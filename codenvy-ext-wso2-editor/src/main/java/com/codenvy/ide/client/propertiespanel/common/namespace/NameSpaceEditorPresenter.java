/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.common.namespace;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.ui.dialogs.DialogFactory;
import com.codenvy.ide.ui.dialogs.message.MessageDialog;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX_KEY;
import static com.codenvy.ide.client.elements.NameSpace.URI;
import static com.codenvy.ide.client.elements.NameSpace.copyNameSpaceList;

/**
 * The presenter that provides a business logic of dialog window for editing name spaces of property.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class NameSpaceEditorPresenter implements NameSpaceEditorView.ActionDelegate {

    private final NameSpaceEditorView view;
    private final Provider<NameSpace> nameSpaceProvider;
    private final MessageDialog       errorMessageDialog;

    private NameSpace             selectedNameSpace;
    private List<NameSpace>       nameSpacesTemporary;
    private AddNameSpacesCallBack callBack;
    private int                   index;

    @Inject
    public NameSpaceEditorPresenter(NameSpaceEditorView view,
                                    Provider<NameSpace> nameSpaceProvider,
                                    WSO2EditorLocalizationConstant locale,
                                    DialogFactory dialogFactory) {
        this.view = view;
        this.view.setDelegate(this);
        this.nameSpaceProvider = nameSpaceProvider;
        this.index = -1;

        errorMessageDialog = dialogFactory.createMessageDialog(locale.errorMessage(), locale.nameAlreadyExistsError(), null);
    }

    /**
     * Shows dialog window with default parameters for editing namespaces.
     *
     * @param nameSpaces
     *         namespaces which need to be edited
     * @param innerCallBack
     *         callback that needs to be handled when namespace editing is successful
     */
    public void showDefaultWindow(@Nonnull List<NameSpace> nameSpaces, @Nonnull AddNameSpacesCallBack innerCallBack) {
        nameSpacesTemporary = copyNameSpaceList(nameSpaces);
        callBack = innerCallBack;

        view.setNameSpaces(nameSpacesTemporary);
        view.setUri("");
        view.setPrefix("");

        view.showWindow();
    }

    /**
     * Shows dialog window with label and expression parameters for editing namespaces.
     *
     * @param nameSpaces
     *         namespaces which need to be edited
     * @param callBack
     *         callback that needs to be handled when namespace editing is successful
     * @param labelName
     *         name of expression for current element
     * @param expression
     *         expression value for current element
     */
    public void showWindowWithParameters(@Nonnull List<NameSpace> nameSpaces,
                                         @Nonnull AddNameSpacesCallBack callBack,
                                         @Nonnull String labelName,
                                         @Nullable String expression) {

        showDefaultWindow(nameSpaces, callBack);

        view.setNameSpaceLabelName(labelName);
        view.setExpression(expression);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddNameSpaceButtonClicked() {
        String prefix = view.getPrefix();
        String uri = view.getUri();

        NameSpace nameSpace = nameSpaceProvider.get();

        nameSpace.putProperty(PREFIX_KEY, prefix);
        nameSpace.putProperty(URI, uri);

        if (isContainsSamePrefix(prefix)) {
            errorMessageDialog.show();

            return;
        }

        view.setPrefix("");
        view.setUri("");

        if (index != -1) {
            nameSpacesTemporary.set(index, nameSpace);
            index = -1;
        } else if (!prefix.isEmpty() && !uri.isEmpty()) {
            nameSpacesTemporary.add(nameSpace);
        }

        view.setNameSpaces(nameSpacesTemporary);
    }

    private boolean isContainsSamePrefix(@Nonnull String prefix) {
        for (NameSpace nameSpace : nameSpacesTemporary) {
            if (prefix.equals(nameSpace.getProperty(PREFIX_KEY))) {
                return true;
            }
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        String prefix = selectedNameSpace.getProperty(PREFIX_KEY);
        String uri = selectedNameSpace.getProperty(URI);

        if (prefix == null || uri == null) {
            return;
        }

        view.setPrefix(prefix);
        view.setUri(uri);

        index = nameSpacesTemporary.indexOf(selectedNameSpace);

        view.setNameSpaces(nameSpacesTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoveButtonClicked() {
        nameSpacesTemporary.remove(selectedNameSpace);

        view.setNameSpaces(nameSpacesTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        callBack.onNameSpacesChanged(nameSpacesTemporary, view.getExpression());

        view.hideWindow();
    }


    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        view.hideWindow();
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
