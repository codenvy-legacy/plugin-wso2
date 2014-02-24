/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import javax.validation.constraints.NotNull;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.GMMUtil;
import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbFactory;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbPackage;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.genmymodel.ecoreonline.graphic.Diagram;
import com.genmymodel.ecoreonline.graphic.GraphicFactory;
import com.genmymodel.ecoreonline.graphic.Plane;
import com.genmymodel.ecoreonline.graphic.impl.GraphicPackageImpl;
import com.genmymodel.ecoreonline.graphic.util.GraphicUtil;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import esbdiag.EsbdiagFactory;
import esbdiag.EsbdiagPackage;
import esbdiag.properties.addressendpoint.AddressEndPointPropertiesView.AddressEndPointPropertiesPresenter;
import esbdiag.properties.callmediator.CallMediatorPropertiesView.CallMediatorPropertiesPresenter;
import esbdiag.properties.headermediator.HeaderMediatorPropertiesView.HeaderMediatorPropertiesPresenter;
import esbdiag.properties.logmediator.LogMediatorPropertiesView.LogMediatorPropertiesPresenter;
import esbdiag.properties.propertymediator.PropertyMediatorPropertiesView.PropertyMediatorPropertiesPresenter;
import esbdiag.properties.respondmediator.RespondMediatorPropertiesView.RespondMediatorPropertiesPresenter;
import esbdiag.properties.sendmediator.SendMediatorPropertiesView.SendMediatorPropertiesPresenter;
import esbdiag.properties.switchmediator.SwitchMediatorPropertiesView.SwitchMediatorPropertiesPresenter;
import esbdiag.util.EsbdiagUtil;

/**
 * The graphical editor for ESB configuration.
 *
 * @author Andrey Plotnikov
 * @author Alexis Muller
 * @author Justin Trentesaux
 */
public class GraphicEditor extends AbstractEditorPresenter implements GraphicEditorView.ActionDelegate, GraphicalSequenceChangeHandler {

	static {
        // register metamodels - should only be done once
        EPackage.Registry.INSTANCE.put(EsbPackage.eINSTANCE.getNsURI(), EsbPackage.eINSTANCE);
        EPackage.Registry.INSTANCE.put(EsbdiagPackage.eINSTANCE.getNsURI(), EsbdiagPackage.eINSTANCE);
    }
	
    private GraphicEditorView                   view;
    private EventBus                            globalBus;
    private LogMediatorPropertiesPresenter      logProperties;
    private PropertyMediatorPropertiesPresenter propertyProperties;
    private RespondMediatorPropertiesPresenter  respondProperties;
    private SendMediatorPropertiesPresenter     sendProperties;
    private SwitchMediatorPropertiesPresenter   switchProperties;
    private CallMediatorPropertiesPresenter     callProperties;
    private HeaderMediatorPropertiesPresenter   headerProperties;
    private AddressEndPointPropertiesPresenter  addressProperties;
    private EsbSequence							sequence;
    private HandlerRegistration                 registration;

    @Inject
    public GraphicEditor(GraphicEditorView view,
                         WSO2Resources wso2Resources,
                         LogMediatorPropertiesPresenter logProperties,
                         PropertyMediatorPropertiesPresenter propertyProperties,
                         RespondMediatorPropertiesPresenter respondProperties,
                         SendMediatorPropertiesPresenter sendProperties,
                         SwitchMediatorPropertiesPresenter switchProperties,
                         CallMediatorPropertiesPresenter callProperties,
                         HeaderMediatorPropertiesPresenter headerProperties,
                         AddressEndPointPropertiesPresenter addressProperties,
                         EventBus globalBus) {

        this.view = view;
        this.view.setDelegate(this);
        this.logProperties = logProperties;
        this.propertyProperties = propertyProperties;
        this.respondProperties = respondProperties;
        this.sendProperties = sendProperties;
        this.switchProperties = switchProperties;
        this.callProperties = callProperties;
        this.headerProperties = headerProperties;
        this.addressProperties = addressProperties;
        this.globalBus = globalBus;

        /* A handler listens every EMF command */
        this.registration = globalBus.addHandler(CommandRequestEvent.TYPE, new SeqEventsHandler(globalBus));

        // /!\ needed for compliance with condenvy injector /!\
        // must be changed
        GraphicPackageImpl.globalBus = globalBus;
        EsbdiagUtil.ESB_RESOURCES = wso2Resources;
    }

    /** {@inheritDoc} */
    @Override
    protected void initializeEditor() {

        view.addPropertyForm(logProperties,
                             propertyProperties,
                             respondProperties,
                             sendProperties,
                             switchProperties,
                             callProperties,
                             headerProperties,
                             addressProperties);

        // create the sequence and its diagram
        sequence = EsbFactory.eINSTANCE.createEsbSequence();
        GMMUtil.setUUID(sequence);
        sequence.setName("NewESB");
        Diagram diagram = EsbdiagFactory.eINSTANCE.createESBDiagram();
        final Plane plane = GraphicFactory.eINSTANCE.createPlane();
        GMMUtil.setUUID(diagram);
        GMMUtil.setUUID(plane);
        diagram.setPlane(plane);
        diagram.setName("NewESB" + "-diag");
        diagram.getPlane().setModelElement(sequence);
        GraphicUtil.addDiagram(sequence, diagram);
        
        // init the modeling widgets
        view.initModelingWidgets(sequence, diagram);
        
        // add a handler for detecting changes on the sequence
        globalBus.addHandler(GraphicalSequenceChangeEvent.TYPE, this);
    }

    /** @return ESB sequence content. */
    public EsbSequence getSequence() {
        return sequence;
    }
    
    /** {@inheritDoc} */
    @Override
    public void doSave() {
        updateDirtyState(false);
    }

    /** {@inheritDoc} */
    @Override
    public void doSaveAs() {
    }

    /** {@inheritDoc} */
    @Override
    public void activate() {
    }

    /** {@inheritDoc} */
    @Override
    public String getTitle() {
        return "ESB Editor: " + input.getFile().getName();
    }

    /** {@inheritDoc} */
    @Override
    public ImageResource getTitleImage() {
        return input.getImageResource();
    }

    /** {@inheritDoc} */
    @Override
    public String getTitleToolTip() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }

    /** {@inheritDoc} */
    @Override
    public void hasChanged(@NotNull EsbSequence sequence) {
        updateDirtyState(true);
    }
     
    /** {@inheritDoc} */
    @Override
    public boolean onClose()
    {
    	// remove handler
        this.registration.removeHandler();
        
        return super.onClose();
    }
    
}
