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
import com.codenvy.ide.client.mvp.View;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
@ImplementedBy(NameSpaceEditorViewImpl.class)
public interface NameSpaceEditorView extends View<NameSpaceEditorView.ActionDelegate> {

    public interface ActionDelegate{
        void showNameSpaceEditorWindow(List<Log.Property.NameSpace> nameSpaces);

        void onAddNameSpaceButtonClicked();

        void onOkButtonClicked();

        void onEditButtonClicked();

        void onRemoveButtonClicked();

        void onCancelButtonClicked();

        void onSelectXPathButtonClicked();

        void onSelectedNameSpace(Log.Property.NameSpace nameSpace);
    }

    void showNameSpaceEditorDialogWindow();

    void hideNameSpaceEditorDialogWindow();

    public String getPrefixText();

    public String getUriText();

    public void setPrefixText(String text);

    public void setUriText(String text);

    public void setNameSpacesList(List<Log.Property.NameSpace> nameSpacesList);

}
