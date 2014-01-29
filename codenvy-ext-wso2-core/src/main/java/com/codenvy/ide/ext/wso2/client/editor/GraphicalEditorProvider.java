package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.editor.EditorProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;


/**
 * Editor Provider for WSO2 ESB graphical files
 *
 * @author Thomas Legrand
 */
public class GraphicalEditorProvider implements EditorProvider {

	EventBus diagramEventBus;
	
	@Inject 
	public GraphicalEditorProvider(EventBus diagramEventBus)
	{
		this.diagramEventBus = diagramEventBus;
	}
	
    /** {@inheritDoc} */
    @Override
    public EditorPartPresenter getEditor() {
        return new GraphicalEditor(diagramEventBus);
    }
}
