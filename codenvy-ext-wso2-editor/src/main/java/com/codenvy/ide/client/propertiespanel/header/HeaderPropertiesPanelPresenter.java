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
package com.codenvy.ide.client.propertiespanel.header;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.Header.HeaderAction.remove;
import static com.codenvy.ide.client.elements.Header.HeaderValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.Header.HeaderValueType.INLINE;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class HeaderPropertiesPanelPresenter extends AbstractPropertiesPanel<Header> implements HeaderPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          headerCallBack;
    private final AddNameSpacesCallBack          expressionCallBack;
    private final WSO2EditorLocalizationConstant locale;

    @Inject
    public HeaderPropertiesPanelPresenter(HeaderPropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          WSO2EditorLocalizationConstant locale) {

        super(view, propertyTypeManager);

        this.nameSpacePresenter = nameSpacePresenter;
        this.locale = locale;

        this.headerCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setHeaderName(nameSpaces.isEmpty() ?
                                      expression :
                                      nameSpaces.get(nameSpaces.size() - 1).getPrefix() + ":" + expression);

                element.setHeaderNamespaces(nameSpaces);
            }
        };

        this.expressionCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setExpression(expression);
                element.setExpressionNamespaces(nameSpaces);
            }
        };

    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderActionChanged() {
        element.setAction(((HeaderPropertiesPanelView)view).getAction());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onScopeChanged() {
        element.setScope(((HeaderPropertiesPanelView)view).getScope());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueTypeChanged() {
        element.setValueType(((HeaderPropertiesPanelView)view).getValueType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        element.setValue(((HeaderPropertiesPanelView)view).getValue());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderNameChanged() {
        element.setHeaderName(((HeaderPropertiesPanelView)view).getHeaderName());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onActionChanged() {
        String action = ((HeaderPropertiesPanelView)view).getAction();
        element.setAction(action);

        setDefaultPanelView();

        ((HeaderPropertiesPanelView)view).setVisibleValueExpressionPanel(false);
        ((HeaderPropertiesPanelView)view).setVisibleValueInlinePanel(false);

        if (remove.name().equals(action)) {
            ((HeaderPropertiesPanelView)view).setVisibleValueExpressionPanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueInlinePanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueTypePanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueLiteralPanel(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onTypeChanged() {
        String type = ((HeaderPropertiesPanelView)view).getValueType();
        element.setValueType(type);

        setDefaultPanelView();

        if (type.equals(EXPRESSION.name())) {
            ((HeaderPropertiesPanelView)view).setVisibleValueLiteralPanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueInlinePanel(false);
        } else if (type.equals(INLINE.name())) {
            ((HeaderPropertiesPanelView)view).setVisibleValueLiteralPanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueExpressionPanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleHeaderNamePanel(false);
        } else {
            ((HeaderPropertiesPanelView)view).setVisibleValueExpressionPanel(false);
            ((HeaderPropertiesPanelView)view).setVisibleValueInlinePanel(false);
        }
    }

    /** Sets default value of panel visibility */
    private void setDefaultPanelView() {
        ((HeaderPropertiesPanelView)view).setVisibleHeaderNamePanel(true);
        ((HeaderPropertiesPanelView)view).setVisibleValueExpressionPanel(true);
        ((HeaderPropertiesPanelView)view).setVisibleValueInlinePanel(true);
        ((HeaderPropertiesPanelView)view).setVisibleValueLiteralPanel(true);
        ((HeaderPropertiesPanelView)view).setVisibleValueTypePanel(true);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddHeaderNameSpaceBtnClicked() {
        String expression = ((HeaderPropertiesPanelView)view).getHeaderName();

        if (expression.contains(":")) {
            expression = StringUtils.split(expression, ":").get(1);
        }
        nameSpacePresenter.showWindowWithParameters(element.getHeaderNamespaces(),
                                                    headerCallBack,
                                                    locale.labelHeader(),
                                                    expression);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddExpressionBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getExpressionNamespaces(),
                                                    expressionCallBack,
                                                    locale.labelExpression(),
                                                    element.getExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onAddInlineBtnClicked() {
        //TODO use inline dialog window
        Window.alert("//TODO inline dialog window");
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((HeaderPropertiesPanelView)view).setAction(propertyTypeManager.getValuesOfTypeByName("HeaderAction"));
        ((HeaderPropertiesPanelView)view).selectHeaderAction(element.getAction());
        ((HeaderPropertiesPanelView)view).setScope(propertyTypeManager.getValuesOfTypeByName("ScopeType"));
        ((HeaderPropertiesPanelView)view).selectScope(element.getScope());
        ((HeaderPropertiesPanelView)view).setValueType(propertyTypeManager.getValuesOfTypeByName("HeaderValueType"));
        ((HeaderPropertiesPanelView)view).selectValueType(element.getValueType());
        ((HeaderPropertiesPanelView)view).setValue(element.getValue());
        ((HeaderPropertiesPanelView)view).setHeaderName(element.getHeaderName());
        ((HeaderPropertiesPanelView)view).setExpression(element.getExpression());
    }

}