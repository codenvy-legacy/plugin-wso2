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
import com.codenvy.ide.client.elements.connectors.salesforce.Upset;
import com.codenvy.ide.client.managers.PropertyTypeManager;
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
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALLOW_FIELD_TRUNCATE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALLOW_FIELD_TRUNCATE_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALL_OR_NONE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALL_OR_NONE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.ALL_OR_NONE_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.EXTERNAL_ID_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.EXTERNAL_ID_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.EXTERNAL_ID_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.SUBJECTS_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.SUBJECTS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Upset.SUBJECTS_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Upset connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class UpsetConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<Upset> {
    private ComplexPropertyPresenter allOrNoneNS;
    private ComplexPropertyPresenter truncateNS;
    private ComplexPropertyPresenter externalIdNS;
    private ComplexPropertyPresenter subjectsNS;
    private SimplePropertyPresenter  allOrNone;
    private SimplePropertyPresenter  truncate;
    private SimplePropertyPresenter  externalId;
    private SimplePropertyPresenter  subjects;

    @Inject
    public UpsetConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                   NameSpaceEditorPresenter nameSpacePresenter,
                                   PropertiesPanelView view,
                                   SalesForcePropertyManager salesForcePropertyManager,
                                   ParameterPresenter parameterPresenter,
                                   PropertyTypeManager propertyTypeManager,
                                   PropertyPanelFactory propertyPanelFactory) {
        super(view,
              salesForcePropertyManager,
              parameterPresenter,
              nameSpacePresenter,
              propertyTypeManager,
              locale,
              propertyPanelFactory);

        prepareView();
    }

    private void prepareView() {
        allOrNoneNS = createComplexConnectorProperty(locale.connectorAllOrNone(), ALL_OR_NONE_NS_KEY, ALL_OR_NONE_EXPRESSION_KEY);
        truncateNS = createComplexConnectorProperty(locale.connectorAllowFieldTruncate(),
                                                    ALLOW_FIELD_TRUNCATE_NS_KEY,
                                                    ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY);
        externalIdNS = createComplexConnectorProperty(locale.connectorExternalId(), EXTERNAL_ID_NS_KEY, EXTERNAL_ID_EXPRESSION_KEY);
        subjectsNS = createComplexConnectorProperty(locale.connectorSubjects(), SUBJECTS_NS_KEY, SUBJECTS_EXPRESSION_KEY);

        allOrNone = createSimpleConnectorProperty(locale.connectorAllOrNone(), ALL_OR_NONE_KEY);
        truncate = createSimpleConnectorProperty(locale.connectorAllowFieldTruncate(), ALLOW_FIELD_TRUNCATE_KEY);
        externalId = createSimpleConnectorProperty(locale.connectorExternalId(), EXTERNAL_ID_KEY);
        subjects = createSimpleConnectorProperty(locale.connectorSubjects(), SUBJECTS_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        allOrNone.setVisible(!isNameSpaced);
        truncate.setVisible(!isNameSpaced);
        externalId.setVisible(!isNameSpaced);
        subjects.setVisible(!isNameSpaced);

        allOrNoneNS.setVisible(isNameSpaced);
        truncateNS.setVisible(isNameSpaced);
        externalIdNS.setVisible(isNameSpaced);
        subjectsNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        allOrNone.setProperty(element.getProperty(ALL_OR_NONE_KEY));
        truncate.setProperty(element.getProperty(ALLOW_FIELD_TRUNCATE_KEY));
        externalId.setProperty(element.getProperty(EXTERNAL_ID_KEY));
        subjects.setProperty(element.getProperty(SUBJECTS_KEY));

        allOrNoneNS.setProperty(element.getProperty(ALL_OR_NONE_EXPRESSION_KEY));
        truncateNS.setProperty(element.getProperty(ALLOW_FIELD_TRUNCATE_EXPRESSION_KEY));
        externalIdNS.setProperty(element.getProperty(EXTERNAL_ID_EXPRESSION_KEY));
        subjectsNS.setProperty(element.getProperty(SUBJECTS_EXPRESSION_KEY));
    }

}