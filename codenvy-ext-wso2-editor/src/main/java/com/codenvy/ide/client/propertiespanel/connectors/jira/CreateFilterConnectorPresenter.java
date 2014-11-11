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
import com.codenvy.ide.client.elements.connectors.jira.CreateFilter;
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
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.DESCRIPTION_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.DESCRIPTION_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.DESCRIPTION_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FAVOURITE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FAVOURITE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FAVOURITE_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FILTER_NAME_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FILTER_NAME_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.FILTER_NAME_NS;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.JQL_TYPE_EXPR;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.JQL_TYPE_INL;
import static com.codenvy.ide.client.elements.connectors.jira.CreateFilter.JQL_TYPE_NS;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class CreateFilterConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<CreateFilter> {

    private SimplePropertyPresenter filterNameInl;
    private SimplePropertyPresenter jqlTypeInl;
    private SimplePropertyPresenter descriptionInl;
    private SimplePropertyPresenter favouriteInl;

    private ComplexPropertyPresenter filterNameExpr;
    private ComplexPropertyPresenter jqlTypeExpr;
    private ComplexPropertyPresenter descriptionExpr;
    private ComplexPropertyPresenter favouriteExpr;

    @Inject
    public CreateFilterConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        filterNameInl = createSimpleConnectorProperty(locale.jiraFilterName(), FILTER_NAME_INL);
        jqlTypeInl = createSimpleConnectorProperty(locale.jiraJqlType(), JQL_TYPE_INL);
        descriptionInl = createSimpleConnectorProperty(locale.jiraDescription(), DESCRIPTION_INL);
        favouriteInl = createSimpleConnectorProperty(locale.jiraFavourite(), FAVOURITE_INL);

        filterNameExpr = createComplexConnectorProperty(locale.jiraFilterName(), FILTER_NAME_NS, FILTER_NAME_EXPR);
        jqlTypeExpr = createComplexConnectorProperty(locale.jiraJqlType(), JQL_TYPE_NS, JQL_TYPE_EXPR);
        descriptionExpr = createComplexConnectorProperty(locale.jiraDescription(), DESCRIPTION_NS, DESCRIPTION_EXPR);
        favouriteExpr = createComplexConnectorProperty(locale.jiraFavourite(), FAVOURITE_NS, FAVOURITE_EXPR);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        boolean isVisible = INLINE.equals(element.getProperty(PARAMETER_EDITOR_TYPE));

        filterNameInl.setVisible(isVisible);
        jqlTypeInl.setVisible(isVisible);
        descriptionInl.setVisible(isVisible);
        favouriteInl.setVisible(isVisible);

        filterNameExpr.setVisible(!isVisible);
        jqlTypeExpr.setVisible(!isVisible);
        descriptionExpr.setVisible(!isVisible);
        favouriteExpr.setVisible(!isVisible);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        filterNameInl.setProperty(element.getProperty(FILTER_NAME_INL));
        jqlTypeInl.setProperty(element.getProperty(JQL_TYPE_INL));
        descriptionInl.setProperty(element.getProperty(DESCRIPTION_INL));
        favouriteInl.setProperty(element.getProperty(FAVOURITE_INL));

        filterNameExpr.setProperty(element.getProperty(FILTER_NAME_EXPR));
        jqlTypeExpr.setProperty(element.getProperty(JQL_TYPE_EXPR));
        descriptionExpr.setProperty(element.getProperty(DESCRIPTION_EXPR));
        favouriteExpr.setProperty(element.getProperty(FAVOURITE_EXPR));
    }
}
