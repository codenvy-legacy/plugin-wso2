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
package com.codenvy.ide.ext.wso2.client.editor;

/**
 * @author Andrey Plotnikov
 */

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.GMMUtil;
import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.genmymodel.gmmf.ui.tools.ToolsController;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbFactory;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.genmymodel.ecoreonline.graphic.Diagram;
import com.genmymodel.ecoreonline.graphic.GraphicFactory;
import com.genmymodel.ecoreonline.graphic.Plane;
import com.genmymodel.ecoreonline.graphic.util.GraphicUtil;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import esbdiag.EsbdiagFactory;
import esbdiag.widgets.ESBDiagramToolbar;

public class GraphicEditor extends AbstractEditorPresenter {

    private       ModelWidget               modelWidget;
    private       Toolbar                   toolbar;
    private       ToolsController           toolsController;
    private       EventBus       diagramEventBus;

    @Inject
    public GraphicEditor(EventBus diagramEventBus) {
        this.diagramEventBus = diagramEventBus;
    }

    /** {@inheritDoc} */
    @Override
    protected void initializeEditor() {
        final EsbSequence newModel = EsbFactory.eINSTANCE.createEsbSequence();
        GMMUtil.setUUID(newModel);
        newModel.setName("NewESB");

        // Create default diagram
        final Diagram diag = EsbdiagFactory.eINSTANCE.createESBDiagram();
        final Plane plane = GraphicFactory.eINSTANCE.createPlane();
        GMMUtil.setUUID(diag);
        GMMUtil.setUUID(plane);
        diag.setPlane(plane);
        diag.setName("NewESB" + "-diag");
        diag.getPlane().setModelElement(newModel);
        GraphicUtil.addDiagram(newModel, diag);

        
        modelWidget = new ModelWidget(diag, diagramEventBus);

        // the ESB-specific toolbar
        toolbar = new ESBDiagramToolbar(modelWidget, diagramEventBus);
        this.toolsController = new ToolsController(modelWidget, diagramEventBus);

        // Take a look at the ClientEventsHandler which execute the EMF commands, i.e each change made of the diagram & model
        this.initBusHandlers();
    }

    private void initBusHandlers() {
        
    	/* toolsController */
    	ArrayList<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
        handlerRegistrations.add(getDiagramEventBus().addHandler(MouseDownEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(MouseMoveEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(MouseUpEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(MouseOverEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(MouseOutEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(KeyDownEvent.getType(), toolsController));
        handlerRegistrations.add(getDiagramEventBus().addHandler(ContextMenuEvent.getType(), toolsController));

        /* A handler listens every EMF command */
        handlerRegistrations.add(getDiagramEventBus().addHandler(CommandRequestEvent.TYPE, new ChangeConfHandler()));
    }

    public EventBus getDiagramEventBus() {
        return diagramEventBus;
    }

    /** {@inheritDoc} */
    @Override
    public void doSave() {
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
        // TODO ugly. need to use something like DockLayoutPanel where we can use not absolute values
        // TODO use MVP pattern
        modelWidget.setSize(800, 800);

        DockLayoutPanel panel = new DockLayoutPanel(Style.Unit.PX);
        panel.setWidth("100%");
        panel.setHeight("100%");
        panel.addWest(toolbar, 90);
        panel.add(modelWidget);

        modelWidget.loadDiagram();

        // Add the components to a panel
        container.setWidget(panel);
    }
}