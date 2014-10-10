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
import com.codenvy.ide.client.elements.connectors.salesforce.QueryMore;
import com.codenvy.ide.client.elements.connectors.salesforce.SalesForcePropertyManager;
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
import static com.codenvy.ide.client.elements.connectors.salesforce.QueryMore.BATCH_SIZE_EXPRESSION_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.QueryMore.BATCH_SIZE_KEY;
import static com.codenvy.ide.client.elements.connectors.salesforce.QueryMore.BATCH_SIZE_NS_KEY;


/**
 * The class provides the business logic that allows editor to react on user's action and to change state of QueryMore connector
 * depending on user's changes of properties.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class QueryMoreConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<QueryMore> {
    private ComplexPropertyPresenter batchSizeNS;
    private SimplePropertyPresenter  batchSize;

    @Inject
    public QueryMoreConnectorPresenter(WSO2EditorLocalizationConstant locale,
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
        batchSizeNS = createComplexConnectorProperty(locale.connectorBatchSize(), BATCH_SIZE_NS_KEY, BATCH_SIZE_EXPRESSION_KEY);
        batchSize = createSimpleConnectorProperty(locale.connectorBatchSize(), BATCH_SIZE_KEY);
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType property = element.getProperty(PARAMETER_EDITOR_TYPE);
        boolean isNameSpaced = NAME_SPACED_PROPERTY_EDITOR.equals(property);

        batchSize.setVisible(!isNameSpaced);
        batchSizeNS.setVisible(isNameSpaced);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        batchSize.setProperty(element.getProperty(BATCH_SIZE_KEY));
        batchSizeNS.setProperty(element.getProperty(BATCH_SIZE_EXPRESSION_KEY));
    }

}