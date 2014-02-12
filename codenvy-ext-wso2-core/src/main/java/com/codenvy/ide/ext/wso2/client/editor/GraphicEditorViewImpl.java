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

import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.common.ModelWidgetCSS;
import org.genmymodel.gmmf.common.SelectModelElementEvent;
import org.genmymodel.gmmf.propertypanel.PropertyPanel;
import org.genmymodel.gmmf.propertypanel.PropertyPresenter;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.genmymodel.gmmf.ui.tools.ToolsController;

import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.WSO2Resources.WSO2Style;
import com.genmymodel.ecoreonline.graphic.Diagram;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import esbdiag.widgets.ESBDiagramToolbar;

/**
 * @author Alexis Muller
 * @author Justin Trentesaux
 */
public class GraphicEditorViewImpl extends Composite implements GraphicEditorView
{

    interface GEUIBinder extends UiBinder<Widget, GraphicEditorViewImpl> {
    };

    private static GEUIBinder binder = GWT.create(GEUIBinder.class);

    @UiField(provided = true)
    protected WSO2Style       style;

    @UiField(provided = true)
    protected Toolbar         toolbar;

    @UiField(provided = true)
    protected ModelWidget     modelWidget;

    @UiField
    protected PropertyPanel   propertyPanel;

    private ToolsController   toolsController;

    public GraphicEditorViewImpl(Diagram diagram, WSO2Resources resources, EventBus globalBus)
    {
        this.style = resources.wso2Style();

        /* Must be local to the widget */
        EventBus diagramEventBus = new SimpleEventBus();

        this.modelWidget = new ModelWidget(diagram, diagramEventBus);

        /* the ESB-specific toolbar */
        this.toolbar = new ESBDiagramToolbar(modelWidget, globalBus, resources.wso2Style(), resources);

        this.toolsController = new ToolsController(modelWidget, globalBus);

        /* toolsController */
        diagramEventBus.addHandler(MouseDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseMoveEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseUpEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOverEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOutEvent.getType(), toolsController);
        diagramEventBus.addHandler(KeyDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(ContextMenuEvent.getType(), toolsController);

        modelWidget.setSize(2048, 2048);
        modelWidget.loadDiagram();

        /* A handler listens every EMF command */
        globalBus.addHandler(CommandRequestEvent.TYPE, new SeqEventsHandler(globalBus));

        /* Bind */
        initWidget(binder.createAndBindUi(this));

        /* event for the property panel */
        globalBus.addHandler(SelectModelElementEvent.TYPE, propertyPanel);
    }

    @Override
    public void addPropertyForm(PropertyPresenter... forms)
    {
        this.propertyPanel.add(forms);
    }


}
