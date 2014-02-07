package com.codenvy.ide.ext.wso2.client.editor;

import org.genmymodel.gmmf.propertypanel.PropertyPresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface GraphicEditorView extends IsWidget
{
	public void addPropertyForm(PropertyPresenter... forms);
}
