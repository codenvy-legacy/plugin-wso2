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
package com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow.nameSpaceEditorDialogWindow;

import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.mvp.Presenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public class NameSpaceEditorPresenter implements Presenter, NameSpaceEditorView.ActionDelegate {

    private final NameSpaceEditorView          nameSpaceEditorView;
    private       Log.Property.NameSpace       selectednameSpace;
    private       List<Log.Property.NameSpace> nameSpaceList;
    private final List<Log.Property.NameSpace> temporaryNameSpaces;

    @Inject
    public NameSpaceEditorPresenter(NameSpaceEditorView nameSpaceEditorView) {
        this.nameSpaceEditorView = nameSpaceEditorView;
        this.temporaryNameSpaces = new ArrayList<>();
        nameSpaceEditorView.setDelegate(this);
    }

    @Override
    public void showNameSpaceEditorWindow(List<Log.Property.NameSpace> nameSpaces) {
        this.nameSpaceList = nameSpaces;
        if (!nameSpaces.isEmpty()) {
            for (Log.Property.NameSpace nameSpace : nameSpaceList) {
                temporaryNameSpaces.add(nameSpace.clone());
            }
        }
        nameSpaceEditorView.setNameSpacesList(temporaryNameSpaces);
        nameSpaceEditorView.showNameSpaceEditorDialogWindow();
    }

    @Override
    public void onAddNameSpaceButtonClicked() {
        String prefix = nameSpaceEditorView.getPrefixText();
        String uri = nameSpaceEditorView.getUriText();

        Log.Property.NameSpace nameSpace = new Log.Property.NameSpace(prefix, uri);

        nameSpaceEditorView.setPrefixText("");
        nameSpaceEditorView.setUriText("");

        temporaryNameSpaces.add(0, nameSpace);

        nameSpaceEditorView.setNameSpacesList(temporaryNameSpaces);
    }

    @Override
    public void onEditButtonClicked() {

    }

    @Override
    public void onRemoveButtonClicked() {
        temporaryNameSpaces.remove(selectednameSpace);
        nameSpaceEditorView.setNameSpacesList(temporaryNameSpaces);
    }

    @Override
    public void onOkButtonClicked() {
        if (!temporaryNameSpaces.isEmpty()) {
            nameSpaceList.addAll(temporaryNameSpaces);
            temporaryNameSpaces.clear();
        }
        nameSpaceEditorView.hideNameSpaceEditorDialogWindow();
    }


    @Override
    public void onCancelButtonClicked() {
        if (!temporaryNameSpaces.isEmpty()) {
            temporaryNameSpaces.clear();
        }
        nameSpaceEditorView.hideNameSpaceEditorDialogWindow();
    }

    @Override
    public void onSelectXPathButtonClicked() {
        //TODO
    }

    @Override
    public void onSelectedNameSpace(Log.Property.NameSpace nameSpace) {
        this.selectednameSpace = nameSpace;
    }

    @Override
    public void go(@Nonnull AcceptsOneWidget container) {

    }
}
