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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.SalesForcePropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmailMessage;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SendEmailMessageConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SendEmailMessage> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack          sendEmailMesCallBack;

    @Inject
    public SendEmailMessageConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                              NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                              GeneralPropertiesPanelView view,
                                              SalesForcePropertyManager salesForcePropertyManager,
                                              ParameterPresenter parameterPresenter,
                                              PropertyTypeManager propertyTypeManager) {
        super(view, salesForcePropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        this.sendEmailMesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSendEmailNameSpaces(nameSpaces);
                element.setSendEmailMessage(expression);

                SendEmailMessageConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        redrawPropertiesPanel();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setSendEmailMessageInline(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getSendEmailMessageNameSpaces(),
                                                          sendEmailMesCallBack,
                                                          locale.connectorExpression(),
                                                          element.getSendEmailMessage());
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType editorType = ParameterEditorType.valueOf(view.getParameterEditorType());
        element.setParameterEditorType(editorType);

        boolean isEquals = NamespacedPropertyEditor.equals(editorType);

        view.setVisibleFirstButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getSendEmailMessage() : element.getSendEmailMessageInline());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);

        view.setFirstLabelTitle(locale.connectorSendEmailMessage());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
