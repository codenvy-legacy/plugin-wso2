package com.codenvy.ide.ext.wso2.esb.graphical.wizard;

import com.codenvy.ide.api.ui.wizard.newresource.NewResourceProvider;
import com.codenvy.ide.resources.model.File;
import com.codenvy.ide.resources.model.Folder;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.resources.model.Resource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import javax.validation.constraints.NotNull;

public class NewEsbModelProvider extends NewResourceProvider
{
	
	@Inject
	public NewEsbModelProvider()
	{
		super("ESB file", "ESB file", null, "esb");
	}
	
	/** {@inheritDoc} */
	@Override
	public void create(@NotNull String name, @NotNull Folder parent, @NotNull Project project,
			@NotNull final AsyncCallback<Resource> callback)
	{
		String fileName = name + '.' + getExtension();
		
		project.createFile(parent, fileName, "", "xml/esb",
			new AsyncCallback<File>() {
				@Override
				public void onSuccess(File result)
				{
					callback.onSuccess(result);
				}
				
				@Override
				public void onFailure(Throwable caught)
				{
					callback.onFailure(caught);
				}
			});
	}
}