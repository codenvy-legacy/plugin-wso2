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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.sendemail;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.elements.connectors.salesforce.GeneralPropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmail;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class SendEmailPropertiesPanelPresenter extends GeneralConnectorPanelPresenter<SendEmail> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack          sendEmailCallBack;

    @Inject
    public SendEmailPropertiesPanelPresenter(WSO2EditorLocalizationConstant locale,
                                             NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                             GeneralConnectorPanelView view,
                                             GeneralPropertyManager generalPropertyManager,
                                             ParameterPresenter parameterPresenter,
                                             PropertyTypeManager propertyTypeManager) {
        super(view, generalPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        this.sendEmailCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSendEmailNameSpaces(nameSpaces);
                element.setSendEmail(expression);

                SendEmailPropertiesPanelPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };
    }

    @Override
    public void onParameterEditorTypeChanged() {
        AbstractConnector.ParameterEditorType editorType =
                AbstractConnector.ParameterEditorType.valueOf(view.getParameterEditorType());
        element.setParameterEditorType(editorType);

        boolean isEquals = NamespacedPropertyEditor.equals(editorType);

        view.setVisibleFirstButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSendEmail() : element.getSendEmailInline());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setSendEmailInline(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getSendEmailNameSpaces(),
                                                          sendEmailCallBack,
                                                          locale.connectorExpression(),
                                                          element.getSendEmail());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);

        view.setFirstLabelTitle(locale.connectorSendEmail());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
        onParameterEditorTypeChanged();
    }
}
