package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.editor.EditorProvider;

/**
 * Editor Provider for WSO2 ESB graphical files
 *
 * @author Thomas Legrand
 */
public class GraphicalEditorProvider implements EditorProvider {

    /** {@inheritDoc} */
    @Override
    public EditorPartPresenter getEditor() {
        return new GraphicalEditor();
    }
}
