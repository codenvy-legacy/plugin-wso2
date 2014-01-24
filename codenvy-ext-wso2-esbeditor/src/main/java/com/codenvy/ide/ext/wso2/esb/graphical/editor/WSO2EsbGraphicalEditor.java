package com.codenvy.ide.ext.wso2.esb.graphical.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.GMMUtil;
import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.ModelWidgetEventBus;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.genmymodel.gmmf.ui.tools.ToolsController;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbFactory;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.util.loging.Log;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

import esbdiag.EsbdiagFactory;
import esbdiag.widgets.ESBDiagramToolbar;

public class WSO2EsbGraphicalEditor extends AbstractEditorPresenter
{

	private RichTextArea textArea;
	private ModelWidget modelWidget;
	private Toolbar toolbar;
	private ToolsController toolsController;
	private ClientEventsHandler clientEventsHandler;
	private ModelWidgetEventBus diagramEventBus;
	private final List<HandlerRegistration> handlerRegistrations;

	public WSO2EsbGraphicalEditor()
	{
		this.handlerRegistrations = new ArrayList<HandlerRegistration>();
	}

	/** {@inheritDoc} */
	@Override
	protected void initializeEditor()
	{
		// create editor
		textArea = new RichTextArea();

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

		diagramEventBus = new ModelWidgetEventBus();
		modelWidget = new ModelWidget(diag, diagramEventBus);
		toolbar = new ESBDiagramToolbar(modelWidget, diagramEventBus);
		this.toolsController = new ToolsController(modelWidget, diagramEventBus); 
		
		this.initBusHandlers();
		
		if (input.getFile().getContent() == null)
		{
			input.getFile().getProject()
					.getContent(input.getFile(), new AsyncCallback<File>() {
						@Override
						public void onFailure(Throwable caught)
						{
							Log.error(WSO2EsbGraphicalEditor.class, caught);
						}

						@Override
						public void onSuccess(File result)
						{
							textArea.setHTML(input.getFile().getContent());
						}
					});
		} else
		{
			textArea.setHTML(input.getFile().getContent());
		}
	}

	private void initBusHandlers()
	{
		// toolsController
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				MouseDownEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				MouseMoveEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				MouseUpEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				MouseOverEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				MouseOutEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				KeyDownEvent.getType(), toolsController));
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				ContextMenuEvent.getType(), toolsController));
		
		this.clientEventsHandler = new ClientEventsHandler();
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				CommandRequestEvent.TYPE, clientEventsHandler));
	}

	public SimpleEventBus getDiagramEventBus()
	{
		return diagramEventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void doSave()
	{
	}

	/** {@inheritDoc} */
	@Override
	public void doSaveAs()
	{
	}

	/** {@inheritDoc} */
	@Override
	public void activate()
	{
		textArea.setFocus(true);
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		return "ESB Editor: " + input.getFile().getName();
	}

	/** {@inheritDoc} */
	@Override
	public ImageResource getTitleImage()
	{
		return input.getImageResource();
	}

	/** {@inheritDoc} */
	@Override
	public String getTitleToolTip()
	{
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void go(AcceptsOneWidget container)
	{
		// textArea.setSize("100%", "100%");
		modelWidget.setSize("1500", "1500");
		// textArea.setHTML(plane.toString());
		/*
		 * this.toolsController = new ToolsController(modelWidget, eventBus);
		 */

		// RichTextToolbar toolbar = new RichTextToolbar(textArea);

		DockLayoutPanel panel = new DockLayoutPanel(Style.Unit.PX);
		panel.setWidth("1500");
		panel.setHeight("1500");
		panel.addWest(toolbar, 60);
		panel.add(modelWidget);// textArea);
		// panel.add(textArea);

		modelWidget.loadDiagram();

		// Add the components to a panel
		container.setWidget(panel);
	}
}
