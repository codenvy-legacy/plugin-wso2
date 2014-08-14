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
package com.codenvy.ide.client.propertiespanel.filter;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The presenter that provides a business logic of 'Filter' mediator properties panel. It provides an ability to work with all properties
 * of 'Filter' mediator.
 *
 * @author Andrey Plotnikov
 */
public class FilterPropertiesPanelPresenter extends AbstractPropertiesPanel<Filter, FilterPropertiesPanelView>
        implements FilterPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant localizationConstant;
    private final AddNameSpacesCallBack          addSourceNameSpacesCallBack;
    private final AddNameSpacesCallBack          addXPathNameSpacesCallBack;

    @Inject
    public FilterPropertiesPanelPresenter(FilterPropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          WSO2EditorLocalizationConstant localizationConstant) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.localizationConstant = localizationConstant;

        this.addSourceNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setSourceNameSpaces(nameSpaces);
                element.setSource(expression);

                FilterPropertiesPanelPresenter.this.view.setSource(expression);

                notifyListeners();
            }
        };

        this.addXPathNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setXPathNameSpaces(nameSpaces);
                element.setXPath(expression);

                FilterPropertiesPanelPresenter.this.view.setXPath(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onConditionTypeChanged() {
        Filter.ConditionType conditionType = Filter.ConditionType.valueOf(view.getConditionType());

        element.setConditionType(conditionType);

        switch (conditionType) {
            case XPATH:
                view.setXPath(element.getXPath());

                view.setVisibleSourcePanel(false);
                view.setVisibleRegularExpressionPanel(false);
                view.setVisibleXpathPanel(true);
                break;

            case SOURCE_AND_REGEX:
            default:
                view.setSource(element.getSource());
                view.setRegularExpression(element.getRegularExpression());

                view.setVisibleSourcePanel(true);
                view.setVisibleRegularExpressionPanel(true);
                view.setVisibleXpathPanel(false);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onRegularExpressionChanged() {
        element.setRegularExpression(view.getRegularExpression());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditSourceButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getSourceNameSpaces(),
                                                          addSourceNameSpacesCallBack,
                                                          localizationConstant.filterSourceTitle(),
                                                          element.getSource());
    }

    /** {@inheritDoc} */
    @Override
    public void onEditXPathButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getXPathNameSpaces(),
                                                          addXPathNameSpacesCallBack,
                                                          localizationConstant.filterXpathTitle(),
                                                          element.getXPath());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setConditionTypes(propertyTypeManager.getValuesOfTypeByName(Filter.ConditionType.TYPE_NAME));
        view.selectConditionType(element.getConditionType().name());

        onConditionTypeChanged();
    }

}