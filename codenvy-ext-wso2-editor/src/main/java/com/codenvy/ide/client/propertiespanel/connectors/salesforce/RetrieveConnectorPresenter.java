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
import com.codenvy.ide.client.elements.connectors.salesforce.Retrieve;
import com.codenvy.ide.client.elements.connectors.salesforce.SalesForcePropertyManager;
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
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.FIELD_LIST_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.FIELD_LIST_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.FIELD_LIST_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_IDS_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_IDS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_IDS_NS_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_TYPE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_TYPE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.Retrieve.OBJECT_TYPE_NS_KEY;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class RetrieveConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<Retrieve> {
    private ComplexPropertyPresenter fieldListNS;
    private ComplexPropertyPresenter objectTypeNS;
    private ComplexPropertyPresenter objectIDSNS;
    private SimplePropertyPresenter  fieldList;
    private SimplePropertyPresenter  objectType;
    private SimplePropertyPresenter  objectIDS;

    @Inject
    public RetrieveConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        fieldListNS = createComplexConnectorProperty(locale.connectorFieldList(), FIELD_LIST_NS_KEY, FIELD_LIST_EXPRESSION_KEY);
        objectTypeNS = createComplexConnectorProperty(locale.connectorObjectType(), OBJECT_TYPE_NS_KEY, OBJECT_TYPE_EXPRESSION_KEY);
        objectIDSNS = createComplexConnectorProperty(locale.connectorObjectIDS(), OBJECT_IDS_NS_KEY, OBJECT_IDS_EXPRESSION_KEY);

        fieldList = createSimpleConnectorProperty(locale.connectorFieldList(), FIELD_LIST_KEY);
        objectType = createSimpleConnectorProperty(locale.connectorObjectType(), OBJECT_TYPE_KEY);
        objectIDS = createSimpleConnectorProperty(locale.connectorObjectIDS(), OBJECT_IDS_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        fieldList.setVisible(!isNameSpaced);
        objectType.setVisible(!isNameSpaced);
        objectIDS.setVisible(!isNameSpaced);

        fieldListNS.setVisible(isNameSpaced);
        objectTypeNS.setVisible(isNameSpaced);
        objectIDSNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        fieldList.setProperty(element.getProperty(FIELD_LIST_KEY));
        objectType.setProperty(element.getProperty(OBJECT_TYPE_KEY));
        objectIDS.setProperty(element.getProperty(OBJECT_IDS_KEY));
        fieldListNS.setProperty(element.getProperty(FIELD_LIST_EXPRESSION_KEY));
        objectTypeNS.setProperty(element.getProperty(OBJECT_TYPE_EXPRESSION_KEY));
        objectIDSNS.setProperty(element.getProperty(OBJECT_IDS_EXPRESSION_KEY));
    }

}