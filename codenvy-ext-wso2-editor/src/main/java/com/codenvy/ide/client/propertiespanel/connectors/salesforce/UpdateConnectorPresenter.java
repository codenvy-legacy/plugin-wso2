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
import com.codenvy.ide.client.elements.connectors.salesforce.Update;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.PARAMETER_EDITOR_TYPE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALLOW_FIELD_TRUNCATE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALLOW_FIELD_TRUNCATE_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALL_OR_NONE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALL_OR_NONE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.ALL_OR_NONE_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.SUBJECTS_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.SUBJECTS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Update.SUBJECTS_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Update connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class UpdateConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<Update> {
    private ComplexPropertyPresenter allOrNoneNS;
    private ComplexPropertyPresenter truncateNS;
    private ComplexPropertyPresenter subjectsNS;
    private SimplePropertyPresenter  allOrNone;
    private SimplePropertyPresenter  truncate;
    private SimplePropertyPresenter  subjects;

    @Inject
    public UpdateConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                    NameSpaceEditorPresenter nameSpacePresenter,
                                    PropertiesPanelView view,
                                    SalesForcePropertyManager salesForcePropertyManager,
                                    ParameterPresenter parameterPresenter,
                                    PropertyTypeManager propertyTypeManager,
                                    PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                    Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                    Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                    Provider<SimplePropertyPresenter> simplePropertyPresenterProvider) {
        super(view,
              salesForcePropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertiesPanelWidgetFactory,
              listPropertyPresenterProvider,
              complexPropertyPresenterProvider,
              simplePropertyPresenterProvider);

        prepareView();
    }

    private void prepareView() {
        allOrNoneNS = createComplexPanel(locale.connectorAllOrNone(), ALL_OR_NONE_NS_KEY, ALL_OR_NONE_EXPRESSION_KEY);
        truncateNS = createComplexPanel(locale.connectorAllowFieldTruncate(),
                                        ALLOW_FIELD_TRUNCATE_NS_KEY,
                                        ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY);
        subjectsNS = createComplexPanel(locale.connectorSubjects(), SUBJECTS_NS_KEY, SUBJECTS_EXPRESSION_KEY);

        allOrNone = createSimplePanel(locale.connectorAllOrNone(), ALL_OR_NONE_KEY);
        truncate = createSimplePanel(locale.connectorAllowFieldTruncate(), ALLOW_FIELD_TRUNCATE_KEY);
        subjects = createSimplePanel(locale.connectorSubjects(), SUBJECTS_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        allOrNone.setVisible(!isNameSpaced);
        truncate.setVisible(!isNameSpaced);
        subjects.setVisible(!isNameSpaced);

        allOrNoneNS.setVisible(isNameSpaced);
        truncateNS.setVisible(isNameSpaced);
        subjectsNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        allOrNone.setProperty(element.getProperty(ALL_OR_NONE_KEY));
        truncate.setProperty(element.getProperty(ALLOW_FIELD_TRUNCATE_KEY));
        subjects.setProperty(element.getProperty(SUBJECTS_KEY));
        allOrNoneNS.setProperty(element.getProperty(ALL_OR_NONE_EXPRESSION_KEY));
        truncateNS.setProperty(element.getProperty(ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY));
        subjectsNS.setProperty(element.getProperty(SUBJECTS_EXPRESSION_KEY));
    }

}