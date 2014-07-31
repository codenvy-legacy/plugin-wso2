/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.namespace;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public class NameSpaceEditorPresenter implements NameSpaceEditorView.ActionDelegate {

    private final NameSpaceEditorView   nameSpaceEditorView;
    private       NameSpace             selectedNameSpace;
    private       Array<NameSpace>      nameSpacesTemporary;
    private       AddNameSpacesCallBack addNameSpacesCallBack;
    private       int                   index;

    @Inject
    public NameSpaceEditorPresenter(NameSpaceEditorView nameSpaceEditorView) {
        this.nameSpaceEditorView = nameSpaceEditorView;
        this.index = -1;

        this.nameSpaceEditorView.setDelegate(this);
    }

    /**
     * Shows dialog window for editing namespaces.
     *
     * @param nameSpaces
     *         namespaces which need to be edited
     * @param callBack
     *         callback that needs to be handled when namespace editing is successful
     */
    public void showNameSpaceEditorWindow(@Nonnull Array<NameSpace> nameSpaces, @Nonnull AddNameSpacesCallBack callBack) {
        nameSpacesTemporary = Collections.createArray();
        addNameSpacesCallBack = callBack;

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            nameSpacesTemporary.add(nameSpace.clone());
        }

        nameSpaceEditorView.setNameSpaces(nameSpacesTemporary);
        nameSpaceEditorView.setUri("");
        nameSpaceEditorView.setPrefix("");

        nameSpaceEditorView.showWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddNameSpaceButtonClicked() {
        String prefix = nameSpaceEditorView.getPrefix();
        String uri = nameSpaceEditorView.getUri();

        NameSpace nameSpace = new NameSpace(prefix, uri);

        nameSpaceEditorView.setPrefix("");
        nameSpaceEditorView.setUri("");

        if (index != -1) {
            nameSpacesTemporary.set(index, nameSpace);
            index = -1;
        } else if (!prefix.isEmpty() && !uri.isEmpty()) {
            nameSpacesTemporary.add(nameSpace);
        }

        nameSpaceEditorView.setNameSpaces(nameSpacesTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        nameSpaceEditorView.setPrefix(selectedNameSpace.getPrefix());
        nameSpaceEditorView.setUri(selectedNameSpace.getUri());

        index = nameSpacesTemporary.indexOf(selectedNameSpace);

        nameSpaceEditorView.setNameSpaces(nameSpacesTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoveButtonClicked() {
        nameSpacesTemporary.remove(selectedNameSpace);

        nameSpaceEditorView.setNameSpaces(nameSpacesTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        addNameSpacesCallBack.onNameSpacesChanged(nameSpacesTemporary);

        nameSpaceEditorView.hideWindow();
    }


    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        nameSpaceEditorView.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onSelectXPathButtonClicked() {
        /*
         TODO
         This method must call method from another presenter to display dialog window for editing XPath.
         This method doesn't have any implementation for now because we need to investigate how this dialog works.
         Needs to create special issue for this problem.
         */
    }

    /** {@inheritDoc} */
    @Override
    public void onSelectedNameSpace(@Nonnull NameSpace nameSpace) {
        this.selectedNameSpace = nameSpace;
    }
}
