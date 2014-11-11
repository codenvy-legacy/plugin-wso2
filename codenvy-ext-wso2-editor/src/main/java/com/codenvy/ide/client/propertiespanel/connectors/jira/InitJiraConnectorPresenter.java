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
package com.codenvy.ide.client.propertiespanel.connectors.jira;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.connectors.jira.InitJira;
import com.codenvy.ide.client.elements.connectors.jira.JiraPropertyManager;
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
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.PASSWORD_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.PASSWORD_INL;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.PASSWORD_NS;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.URI_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.URI_INL;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.URI_NS;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.USER_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.USER_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.jira.InitJira.USER_NAME_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class InitJiraConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<InitJira> {

    private SimplePropertyPresenter userNameInl;
    private SimplePropertyPresenter passwordInl;
    private SimplePropertyPresenter uriInl;

    private ComplexPropertyPresenter userNameExpr;
    private ComplexPropertyPresenter passwordExpr;
    private ComplexPropertyPresenter uriExpr;

    @Inject
    public InitJiraConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                      NameSpaceEditorPresenter nameSpacePresenter,
                                      PropertiesPanelView view,
                                      JiraPropertyManager jiraPropertyManager,
                                      ParameterPresenter parameterPresenter,
                                      PropertyTypeManager propertyTypeManager,
                                      PropertyPanelFactory propertyPanelFactory,
                                      SelectionManager selectionManager) {
        super(view,
              jiraPropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory,
              selectionManager);

        prepareView();
    }

    private void prepareView() {
        userNameInl = createSimpleConnectorProperty(locale.connectorUsername(), USER_NAME_INL);
        passwordInl = createSimpleConnectorProperty(locale.connectorPassword(), PASSWORD_INL);
        uriInl = createSimpleConnectorProperty(locale.jiraUri(), URI_INL);

        userNameExpr = createComplexConnectorProperty(locale.connectorUsername(), USER_NAME_NS, USER_NAME_EXPR);
        passwordExpr = createComplexConnectorProperty(locale.connectorPassword(), PASSWORD_NS, PASSWORD_EXPR);
        uriExpr = createComplexConnectorProperty(locale.jiraUri(), URI_NS, URI_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        userNameInl.setVisible(isVisible);
        passwordInl.setVisible(isVisible);
        uriInl.setVisible(isVisible);

        userNameExpr.setVisible(!isVisible);
        passwordExpr.setVisible(!isVisible);
        uriExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        userNameInl.setProperty(element.getProperty(USER_NAME_INL));
        passwordInl.setProperty(element.getProperty(PASSWORD_INL));
        uriInl.setProperty(element.getProperty(URI_INL));

        userNameExpr.setProperty(element.getProperty(USER_NAME_EXPR));
        passwordExpr.setProperty(element.getProperty(PASSWORD_EXPR));
        uriExpr.setProperty(element.getProperty(URI_EXPR));
    }
}
