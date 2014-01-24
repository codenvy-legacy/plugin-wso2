package com.codenvy.ide.ext.wso2.esb.graphical;

import com.codenvy.ide.api.editor.EditorRegistry;
import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.resources.FileType;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.ui.wizard.newresource.NewResourceAgent;
import com.codenvy.ide.api.ui.workspace.WorkspaceAgent;
import com.codenvy.ide.ext.wso2.esb.graphical.wizard.NewEsbModelProvider;
import com.google.inject.Inject;

@Extension(title = "ESB Editor Extension", version = "1.0")
public class WSO2GraphicalEditorExtension {

    @Inject
    public WSO2GraphicalEditorExtension(ResourceProvider resourceProvider, WSO2EditorProvider editorProvider, EditorRegistry editorRegistry,
                            NewEsbModelProvider newHTMLFileProvider, NewResourceAgent newResourceAgent, WorkspaceAgent workspaceAgent) {
        FileType htmlFileType = new FileType(null, "xml/esb", "esb");
        resourceProvider.registerFileType(htmlFileType);
        editorRegistry.register(htmlFileType, editorProvider);
        newResourceAgent.register(newHTMLFileProvider);
    }
}
