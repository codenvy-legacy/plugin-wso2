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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.delete;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;
import com.codenvy.ide.client.elements.connectors.salesforce.Delete;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType.Inline;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Delete connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class DeletePropertiesPanelPresenter extends AbstractPropertiesPanel<Delete, DeletePropertiesPanelView>
        implements DeletePropertiesPanelView.ActionDelegate, BaseConnectorPanelPresenter.BasePropertyChangedListener {

    private final WSO2EditorLocalizationConstant local;
    private final BaseConnectorPanelPresenter    baseConnectorPresenter;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          subjectNameSpacesCallBack;
    private final AddNameSpacesCallBack          allOrNoneNameSpacesCallBack;

    @Inject
    public DeletePropertiesPanelPresenter(WSO2EditorLocalizationConstant local,
                                          BaseConnectorPanelPresenter baseConnectorPresenter,
                                          DeletePropertiesPanelView view,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          PropertyTypeManager propertyTypeManager) {

        super(view, propertyTypeManager);

        this.local = local;

        this.nameSpacePresenter = nameSpacePresenter;

        this.baseConnectorPresenter = baseConnectorPresenter;
        this.baseConnectorPresenter.addListener(this);

        this.subjectNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setSubjectsNameSpaces(nameSpaces);
                element.setSubjectExpression(expression);

                DeletePropertiesPanelPresenter.this.view.setSubjectValue(expression);

                notifyListeners();
            }
        };

        this.allOrNoneNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setAllOrNoneNameSpaces(nameSpaces);
                element.setAllOrNoneExpr(expression);

                DeletePropertiesPanelPresenter.this.view.setAllOrNone(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged(@Nonnull ParameterEditorType parameterType, @Nonnull String configRef) {
        element.setParameterEditorType(parameterType);
        element.setConfigRef(configRef);

        view.setSubjectValue(Inline.equals(parameterType) ? element.getSubject() : element.getSubjectExpression());
        view.setAllOrNone(Inline.equals(parameterType) ? element.getAllOrNone() : element.getAllOrNoneExpr());

        view.setVisibleButton(!Inline.equals(parameterType));
        view.setEnableTextField(Inline.equals(parameterType));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAllOrNoneChanged() {
        element.setAllOrNone(view.getAllOrNone());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSubjectChanged() {
        element.setSubject(view.getSubjectValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAllOrNoneBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getAllOrNoneNameSpaces(),
                                                    allOrNoneNameSpacesCallBack,
                                                    local.propertiespanelConnectorExpression(),
                                                    element.getAllOrNoneExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSubjectBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSubjectsNameSpaces(),
                                                    subjectNameSpacesCallBack,
                                                    local.propertiespanelConnectorExpression(),
                                                    element.getSubjectExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setGeneralPanel(baseConnectorPresenter);
        baseConnectorPresenter.setConfigRef(element.getConfigRef());
        baseConnectorPresenter.setParameterEditorType(element.getParameterEditorType());

        onPropertyChanged(element.getParameterEditorType(), element.getConfigRef());
    }
}
