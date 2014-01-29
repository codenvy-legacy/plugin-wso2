package com.codenvy.ide.ext.wso2.client.editor;

import java.util.ArrayList;
import java.util.List;

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

/**
 * ESB Graphical editor
 *
 * @author Alexis Muller
 */
public class GraphicalEditor extends AbstractEditorPresenter
{
	private ModelWidget modelWidget;
	private Toolbar toolbar;
	private ToolsController toolsController;
	private GraphicalSequenceEventsHandler clientEventsHandler;
	private EventBus diagramEventBus;
	private final List<HandlerRegistration> handlerRegistrations;

	@Inject
	public GraphicalEditor(EventBus diagramEventBus)
	{
		this.handlerRegistrations = new ArrayList<HandlerRegistration>();
		this.diagramEventBus = diagramEventBus;
	}

	/** 
	 * Initialize the esb diagram, the model widget that contains the diagram,
	 *  the esb model and the toolbar.
	 */
	@Override
	protected void initializeEditor()
	{
		// Create the model, that conforms the esb metamodel 
		final EsbSequence newModel = EsbFactory.eINSTANCE.createEsbSequence();
		GMMUtil.setUUID(newModel);
		newModel.setName("NewESB");
		
		// Create the diagram
		final Diagram diag = EsbdiagFactory.eINSTANCE.createESBDiagram();
		final Plane plane = GraphicFactory.eINSTANCE.createPlane();
		GMMUtil.setUUID(diag);
		GMMUtil.setUUID(plane);
		diag.setPlane(plane);
		diag.setName("NewESB" + "-diag");
		diag.getPlane().setModelElement(newModel);
		GraphicUtil.addDiagram(newModel, diag);

		// Get them connected
		modelWidget = new ModelWidget(diag, diagramEventBus);
		toolbar = new ESBDiagramToolbar(modelWidget, diagramEventBus);
		this.toolsController = new ToolsController(modelWidget, diagramEventBus); 
		
		this.initBusHandlers();
		
		/*
		if (input.getFile().getContent() == null)
		{
			input.getFile().getProject()
					.getContent(input.getFile(), new AsyncCallback<File>() {
						@Override
						public void onFailure(Throwable caught)
						{
							Log.error(GraphicalEditor.class, caught);
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
		*/
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
		
		this.clientEventsHandler = new GraphicalSequenceEventsHandler();
		this.handlerRegistrations.add(this.getDiagramEventBus().addHandler(
				CommandRequestEvent.TYPE, clientEventsHandler));
	}

	public EventBus getDiagramEventBus()
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


	
	// TODO
	/*
	public void activate()
	{
		textArea.setFocus(true);
	}
	*/
	
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

	/** 
	 * Handles the layout of the model widget
	 */
	@Override
	public void go(AcceptsOneWidget container)
	{	
		// should soon be dynamic
		modelWidget.setSize("1500", "1500");
		
		DockLayoutPanel panel = new DockLayoutPanel(Style.Unit.PX);
		panel.setWidth("1500");
		panel.setHeight("1500");
		panel.addWest(toolbar, 60);
		panel.add(modelWidget);

		modelWidget.loadDiagram();

		// Add the components to a panel
		container.setWidget(panel);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}
}
