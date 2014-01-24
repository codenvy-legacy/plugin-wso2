package com.codenvy.ide.ext.wso2.esb.graphical;

import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.editor.EditorProvider;
import com.codenvy.ide.ext.wso2.esb.graphical.editor.WSO2EsbGraphicalEditor;

public class WSO2EditorProvider implements EditorProvider {

    /** {@inheritDoc} */
    @Override
    public EditorPartPresenter getEditor() {
        return new WSO2EsbGraphicalEditor();
    }
}
