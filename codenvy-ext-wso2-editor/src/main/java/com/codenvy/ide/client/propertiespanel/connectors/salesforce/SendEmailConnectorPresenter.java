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
import com.codenvy.ide.client.elements.connectors.salesforce.SalesForcePropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmail;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;
import static com.codenvy.ide.client.elements.connectors.salesforce.SendEmail.SEND_EMAIL_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.SendEmail.SEND_EMAIL_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.SendEmail.SEND_EMAIL_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SendEmailConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<SendEmail> {
    private ComplexPropertyPresenter sendEmailNS;
    private SimplePropertyPresenter  sendEmail;

    @Inject
    public SendEmailConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                       NameSpaceEditorPresenter nameSpacePresenter,
                                       PropertiesPanelView view,
                                       SalesForcePropertyManager salesForcePropertyManager,
                                       ParameterPresenter parameterPresenter,
                                       PropertyTypeManager propertyTypeManager,
                                       PropertyPanelFactory propertyPanelFactory,
                                       SelectionManager selectionManager) {
        super(view,
              salesForcePropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        sendEmailNS = createComplexConnectorProperty(locale.connectorSendEmail(), SEND_EMAIL_NS_KEY, SEND_EMAIL_EXPRESSION_KEY);
        sendEmail = createSimpleConnectorProperty(locale.connectorSendEmail(), SEND_EMAIL_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        sendEmail.setVisible(!isNameSpaced);
        sendEmailNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        sendEmail.setProperty(element.getProperty(SEND_EMAIL_KEY));
        sendEmailNS.setProperty(element.getProperty(SEND_EMAIL_EXPRESSION_KEY));
    }

}