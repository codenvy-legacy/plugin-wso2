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

import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.genmymodel.ecoreonline.graphic.Diagram;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import esbdiag.widgets.ESBDiagramToolbar;
import org.genmymodel.gmmf.common.SelectModelElementEvent;
import org.genmymodel.gmmf.propertypanel.PropertyPanel;
import org.genmymodel.gmmf.propertypanel.PropertyPresenter;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.genmymodel.gmmf.ui.tools.ToolsController;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

/**
 * The implementation of {@link GraphicEditorView}.
 *
 * @author Alexis Muller
 * @author Justin Trentesaux
 * @author Andrey Plotnikov
 */
public class GraphicEditorViewImpl extends Composite implements GraphicEditorView {

    interface GEUIBinder extends UiBinder<Widget, GraphicEditorViewImpl> {
    }

    private static GEUIBinder binder = GWT.create(GEUIBinder.class);

    @UiField(provided = true)
    Toolbar       toolbar;
    @UiField(provided = true)
    ModelWidget   modelWidget;
    @UiField
    PropertyPanel propertyPanel;
    @UiField(provided = true)
    WSO2Resources res;

    private EventBus globalBus;

    // This event bus is internal to each diagram widget instance
    private EventBus diagramEventBus;

    @Inject
    public GraphicEditorViewImpl(WSO2Resources resources, EventBus globalBus) {
        this.res = resources;
        this.globalBus = globalBus;
        // we use a local bus for the communication between the toolbar and the widgets
        this.diagramEventBus = new SimpleEventBus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initModelingWidgets(EsbSequence sequence, Diagram diagram) {

        // ModelWidget comes from GMMF framework
        this.modelWidget = new ModelWidget(diagram, diagramEventBus);

        // the ESB-specific toolbar
        this.toolbar = new ESBDiagramToolbar(modelWidget, this.globalBus, res.wso2Style(), res);

        ToolsController toolsController = new ToolsController(modelWidget, this.globalBus);

        // toolsController
        diagramEventBus.addHandler(MouseDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseMoveEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseUpEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOverEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOutEvent.getType(), toolsController);
        diagramEventBus.addHandler(KeyDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(ContextMenuEvent.getType(), toolsController);

        // TODO need to change hard code size of panel
        modelWidget.setSize(2048, 2048);
        modelWidget.loadDiagram();

        initWidget(binder.createAndBindUi(this));

        // event for the property panel
        this.globalBus.addHandler(SelectModelElementEvent.TYPE, propertyPanel);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setDelegate(ActionDelegate delegate) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyForm(PropertyPresenter... forms) {
        propertyPanel.add(forms);
    }

    @Override
    public EventBus getDiagramEventBus() {
        return diagramEventBus;
    }
}
