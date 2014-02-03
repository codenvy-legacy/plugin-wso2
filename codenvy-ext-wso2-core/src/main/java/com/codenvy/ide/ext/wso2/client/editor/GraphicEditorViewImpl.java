package com.codenvy.ide.ext.wso2.client.editor;

import org.genmymodel.gmmf.propertypanel.PropertyPanel;
import org.genmymodel.gmmf.ui.ModelWidget;
import org.genmymodel.gmmf.ui.tools.Toolbar;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class GraphicEditorViewImpl extends Composite implements GraphicEditorView
{

	interface GraphicEditorViewImplUIBinder extends UiBinder<Widget, GraphicEditorViewImpl> {
	};
	
	@UiField(provided = true)
	Toolbar toolbar;
	
	@UiField
	ModelWidget modelwidget;
	
	@UiField
	PropertyPanel propertyPanel;
	
    private       EventBus              diagramEventBus;
	
	public GraphicEditorViewImpl()
	{
		
	}

	@Override
	public void setSequence(EsbSequence sequence)
	{
		// TODO Auto-generated method stub

	}

}
