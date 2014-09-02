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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.describesobject;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeSubject;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType;

/**
 * The presenter that provides a business logic of 'DescribeSobject' connector properties panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 */
public class DescribeSubjectPropertiesPanelPresenter extends AbstractPropertiesPanel<DescribeSubject, DescribeSubjectPropertiesPanelView>
        implements DescribeSubjectPropertiesPanelView.ActionDelegate, BaseConnectorPanelPresenter.BasePropertyChangedListener {

    private final WSO2EditorLocalizationConstant local;
    private final BaseConnectorPanelPresenter    baseConnectorPresenter;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack          addSubjectsNameSpacesCallBack;

    @Inject
    public DescribeSubjectPropertiesPanelPresenter(DescribeSubjectPropertiesPanelView view,
                                                   PropertyTypeManager propertyTypeManager,
                                                   WSO2EditorLocalizationConstant local,
                                                   BaseConnectorPanelPresenter baseConnectorPresenter,
                                                   NameSpaceEditorPresenter nameSpaceEditorPresenter) {
        super(view, propertyTypeManager);

        this.local = local;
        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.baseConnectorPresenter = baseConnectorPresenter;
        this.baseConnectorPresenter.addListener(this);

        this.addSubjectsNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setSubjectsNameSpaces(nameSpaces);
                element.setSubjects(expression);

                DescribeSubjectPropertiesPanelPresenter.this.view.setSubjectsNamespace(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onSubjectChanged() {
        element.setSubjectsInline(view.getSubjects());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void subjectsButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getSubjectsNameSpaces(),
                                                          addSubjectsNameSpacesCallBack,
                                                          local.propertiespanelConnectorExpression(),
                                                          element.getSubjects());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.addBaseConnector(baseConnectorPresenter);

        baseConnectorPresenter.setConfigRef(element.getConfigRef());
        baseConnectorPresenter.selectParameterEditorType(element.getParameterEditorType());

        view.setSubjects(element.getSubjectsInline());
        view.setSubjectsNamespace(element.getSubjects());
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged(@Nonnull ParameterEditorType parameterEditorType, @Nonnull String configRef) {
        boolean isNamespaceEditorParam = parameterEditorType.equals(ParameterEditorType.NamespacedPropertyEditor);

        element.setParameterEditorType(parameterEditorType);
        element.setConfigRef(configRef);

        view.setVisibleSubjectsNamespacePanel(isNamespaceEditorParam);
        view.setVisibleSubjects(!isNamespaceEditorParam);

        notifyListeners();
    }
}
