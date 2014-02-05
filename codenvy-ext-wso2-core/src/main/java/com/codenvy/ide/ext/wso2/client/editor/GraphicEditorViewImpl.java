package com.codenvy.ide.ext.wso2.client.editor;

import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.common.ModelWidgetCSS;
import org.genmymodel.gmmf.common.SelectModelElementEvent;
import org.genmymodel.gmmf.propertypanel.PropertyPanel;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.genmymodel.gmmf.ui.tools.ToolsController;

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

public class GraphicEditorViewImpl extends Composite implements GraphicEditorView
{

	interface GEUIBinder extends UiBinder<Widget, GraphicEditorViewImpl> {
	};
	private static GEUIBinder binder = GWT.create(GEUIBinder.class); 
	
	//private GraphicEditor presenter;
	
	@UiField(provided = true)
	Toolbar toolbar;
	
	@UiField(provided = true)
	ModelWidget modelWidget;
	
	@UiField
	PropertyPanel propertyPanel;
	
    private       ToolsController       toolsController;
    //private       EventBus              diagramEventBus;
	
	public GraphicEditorViewImpl(Diagram diagram, ModelWidgetCSS modelWidgetCss)
	{
		EventBus diagramEventBus = new SimpleEventBus();// Must be local to the widget
		
		this.modelWidget = new ModelWidget(diagram, diagramEventBus);

        // the ESB-specific toolbar
        this.toolbar = new ESBDiagramToolbar(modelWidget, diagramEventBus, modelWidgetCss);

        this.toolsController = new ToolsController(modelWidget, diagramEventBus);

        /* toolsController */
        diagramEventBus.addHandler(MouseDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseMoveEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseUpEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOverEvent.getType(), toolsController);
        diagramEventBus.addHandler(MouseOutEvent.getType(), toolsController);
        diagramEventBus.addHandler(KeyDownEvent.getType(), toolsController);
        diagramEventBus.addHandler(ContextMenuEvent.getType(), toolsController);

        /* event for the property panel */
        diagramEventBus.addHandler(SelectModelElementEvent.TYPE, propertyPanel);
        
        modelWidget.loadDiagram();

        /* A handler listens every EMF command */
        diagramEventBus.addHandler(CommandRequestEvent.TYPE, new SeqEventsHandler());
        
        
        initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(GraphicEditor presenter)
	{
		//this.presenter = presenter;
	}

}
