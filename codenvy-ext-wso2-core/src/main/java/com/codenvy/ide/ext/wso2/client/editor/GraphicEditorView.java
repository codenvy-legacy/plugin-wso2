package com.codenvy.ide.ext.wso2.client.editor;

import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.google.gwt.user.client.ui.IsWidget;

public interface GraphicEditorView extends IsWidget
{
	void setSequence (EsbSequence sequence);
}
