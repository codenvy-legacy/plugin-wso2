package com.codenvy.ide.ext.wso2.client.editor;

import org.genmymodel.gmmf.propertypanel.PropertyForm;

import com.google.gwt.user.client.ui.IsWidget;

public interface GraphicEditorView extends IsWidget
{
	public void addPropertyForm(PropertyForm... forms);
}
