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
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.Header.HeaderAction;
import static com.codenvy.ide.client.elements.Header.HeaderAction.remove;
import static com.codenvy.ide.client.elements.Header.HeaderAction.set;
import static com.codenvy.ide.client.elements.Header.HeaderValueType;
import static com.codenvy.ide.client.elements.Header.HeaderValueType.LITERAL;
import static com.codenvy.ide.client.elements.Header.ScopeType;

/**
 * The presenter that provides a business logic of 'Header' mediator properties panel. It provides an ability to work with all properties
 * of 'Header' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class HeaderPropertiesPanelPresenter extends AbstractPropertiesPanel<Header, HeaderPropertiesPanelView>
        implements HeaderPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter     nameSpacePresenter;
    private final InlineConfigurationPresenter inlinePresenter;

    private final AddNameSpacesCallBack      headerCallBack;
    private final AddNameSpacesCallBack      expressionCallBack;
    private final ChangeInlineFormatCallBack inlineCallBack;

    private final WSO2EditorLocalizationConstant locale;

    @Inject
    public HeaderPropertiesPanelPresenter(HeaderPropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          InlineConfigurationPresenter inlinePresenter,
                                          WSO2EditorLocalizationConstant locale) {

        super(view, propertyTypeManager);

        this.inlinePresenter = inlinePresenter;
        this.nameSpacePresenter = nameSpacePresenter;
        this.locale = locale;

        this.headerCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setHeaderName(nameSpaces.isEmpty() ?
                                      expression :
                                      nameSpaces.get(nameSpaces.size() - 1).getPrefix() + ":" + expression);

                element.setHeaderNamespaces(nameSpaces);

                HeaderPropertiesPanelPresenter.this.view.setHeaderName(expression);

                notifyListeners();
            }
        };

        this.expressionCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setExpression(expression);
                element.setExpressionNamespaces(nameSpaces);

                HeaderPropertiesPanelPresenter.this.view.setExpression(expression);

                notifyListeners();
            }
        };

        this.inlineCallBack = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.setInlineXml(inline);

                HeaderPropertiesPanelPresenter.this.view.setInlineXML(inline);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onScopeChanged() {
        element.setScope(ScopeType.valueOf(view.getScope()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        element.setValue(view.getValue());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderNameChanged() {
        element.setHeaderName(view.getHeaderName());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onActionChanged() {
        HeaderAction action = HeaderAction.valueOf(view.getAction());
        element.setAction(action);

        setDefaultPanelView();

        view.setVisibleValueExpressionPanel(false);
        view.setVisibleValueInlinePanel(false);

        if (remove.equals(action)) {
            view.setVisibleValueExpressionPanel(false);
            view.setVisibleValueInlinePanel(false);
            view.setVisibleValueTypePanel(false);
            view.setVisibleValueLiteralPanel(false);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTypeChanged() {
        HeaderValueType type = HeaderValueType.valueOf(view.getValueType());
        element.setValueType(type);

        setDefaultPanelView();

        switch (type) {
            case EXPRESSION:
                view.setVisibleValueLiteralPanel(false);
                view.setVisibleValueInlinePanel(false);
                break;

            case INLINE:
                view.setVisibleValueLiteralPanel(false);
                view.setVisibleValueExpressionPanel(false);
                view.setVisibleHeaderNamePanel(false);
                break;

            default:
                view.setVisibleValueExpressionPanel(false);
                view.setVisibleValueInlinePanel(false);
                break;
        }

        notifyListeners();
    }

    /** Sets default value of panel visibility */
    private void setDefaultPanelView() {
        view.setVisibleHeaderNamePanel(true);
        view.setVisibleValueExpressionPanel(true);
        view.setVisibleValueInlinePanel(true);
        view.setVisibleValueLiteralPanel(true);
        view.setVisibleValueTypePanel(true);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddHeaderNameSpaceBtnClicked() {
        String expression = view.getHeaderName();

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
        inlinePresenter.showDialog(element.getInlineXml(), locale.inlineTitle(), inlineCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setAction(propertyTypeManager.getValuesByName(HeaderAction.TYPE_NAME));
        view.selectHeaderAction(element.getAction().name());

        view.setScope(propertyTypeManager.getValuesByName(ScopeType.TYPE_NAME));
        view.selectScope(element.getScope().name());

        view.setValueTypes(propertyTypeManager.getValuesByName(HeaderValueType.TYPE_NAME));
        view.selectValueType(element.getValueType().name());

        view.setValue(element.getValue());
        view.setHeaderName(element.getHeaderName());
        view.setExpression(element.getExpression());
        view.setInlineXML(element.getInlineXml());

        if (!LITERAL.equals(element.getValueType())) {
            onTypeChanged();
        }

        if (!set.equals(element.getAction())) {
            onActionChanged();
        }
    }

}