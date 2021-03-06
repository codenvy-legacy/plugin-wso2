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
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Action;
import com.codenvy.ide.client.elements.mediators.Header;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX_KEY;
import static com.codenvy.ide.client.elements.mediators.Action.REMOVE;
import static com.codenvy.ide.client.elements.mediators.Header.ACTION;
import static com.codenvy.ide.client.elements.mediators.Header.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Header.EXPRESSION_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Header.HEADER_NAME;
import static com.codenvy.ide.client.elements.mediators.Header.HEADER_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Header.HeaderValueType;
import static com.codenvy.ide.client.elements.mediators.Header.INLINE_XML;
import static com.codenvy.ide.client.elements.mediators.Header.SCOPE;
import static com.codenvy.ide.client.elements.mediators.Header.ScopeType;
import static com.codenvy.ide.client.elements.mediators.Header.VALUE_LITERAL;
import static com.codenvy.ide.client.elements.mediators.Header.VALUE_TYPE;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Header mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class HeaderPropertiesPanelPresenter extends AbstractPropertiesPanel<Header> {

    private final NameSpaceEditorPresenter     nameSpacePresenter;
    private final InlineConfigurationPresenter inlinePresenter;

    private final ChangeInlineFormatCallBack inlineCallBack;
    private final AddNameSpacesCallBack      headerCallBack;
    private final AddNameSpacesCallBack      expressionCallBack;

    private ListPropertyPresenter    action;
    private ListPropertyPresenter    scope;
    private ListPropertyPresenter    valueType;
    private SimplePropertyPresenter  valueLiteral;
    private ComplexPropertyPresenter headerName;
    private ComplexPropertyPresenter headerExpression;
    private ComplexPropertyPresenter headerInline;

    @Inject
    public HeaderPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          InlineConfigurationPresenter inlinePresenter,
                                          WSO2EditorLocalizationConstant locale,
                                          PropertyPanelFactory propertyPanelFactory,
                                          SelectionManager selectionManager) {

        super(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);

        this.inlinePresenter = inlinePresenter;
        this.nameSpacePresenter = nameSpacePresenter;

        this.headerCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(HEADER_NAME, nameSpaces.isEmpty() ?
                                                 expression :
                                                 nameSpaces.get(nameSpaces.size() - 1).getProperty(PREFIX_KEY) + ":" + expression);
                element.putProperty(HEADER_NAMESPACES, nameSpaces);

                headerName.setProperty(expression);

                notifyListeners();
            }
        };

        this.expressionCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(EXPRESSION, expression);
                element.putProperty(EXPRESSION_NAMESPACES, nameSpaces);

                headerExpression.setProperty(expression);

                notifyListeners();
            }
        };

        this.inlineCallBack = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.putProperty(INLINE_XML, inline);

                headerInline.setProperty(inline);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener headerActionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                Action headerAction = Action.getItemByValue(property);
                element.putProperty(ACTION, headerAction);

                redesignPropertyPanelView();

                notifyListeners();
            }
        };
        action = createListProperty(basicGroup, locale.headerAction(), headerActionListener);

        PropertyValueChangedListener scopeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(SCOPE, ScopeType.getItemByValue(property));

                notifyListeners();
            }
        };
        scope = createListProperty(basicGroup, locale.headerScope(), scopeListener);

        PropertyValueChangedListener valueTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_TYPE, HeaderValueType.valueOf(property));

                redesignPropertyPanelView();

                notifyListeners();
            }
        };
        valueType = createListProperty(basicGroup, locale.headerValueType(), valueTypeListener);

        PropertyValueChangedListener valueLiteralListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(VALUE_LITERAL, property);

                notifyListeners();
            }
        };
        valueLiteral = createSimpleProperty(basicGroup, locale.headerValueLiteral(), valueLiteralListener);

        EditButtonClickedListener headerValueExprBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> expressionNameSpaces = element.getProperty(EXPRESSION_NAMESPACES);
                String expression = element.getProperty(EXPRESSION);

                if (expressionNameSpaces != null) {
                    nameSpacePresenter.showWindowWithParameters(expressionNameSpaces,
                                                                expressionCallBack,
                                                                locale.labelExpression(),
                                                                expression);
                }
            }
        };
        headerExpression = createComplexProperty(basicGroup, locale.headerValueExpression(), headerValueExprBtnListener);

        EditButtonClickedListener headerNameBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> headerNameSpaces = element.getProperty(HEADER_NAMESPACES);
                String expression = element.getProperty(HEADER_NAME);

                if (expression != null && expression.contains(":")) {
                    expression = StringUtils.split(expression, ":").get(1);
                }

                if (headerNameSpaces != null) {
                    nameSpacePresenter.showWindowWithParameters(headerNameSpaces, headerCallBack, locale.labelHeader(), expression);
                }
            }
        };
        headerName = createComplexProperty(basicGroup, locale.headerName(), headerNameBtnListener);

        EditButtonClickedListener headerInlineBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String inline = element.getProperty(INLINE_XML);

                if (inline != null) {
                    inlinePresenter.showDialog(inline, locale.inlineTitle(), inlineCallBack);
                }
            }
        };
        headerInline = createComplexProperty(basicGroup, locale.headerValueInline(), headerInlineBtnListener);
    }

    private void setDefaultPropertyPanelVisible() {
        valueType.setVisible(true);
        valueLiteral.setVisible(true);
        headerName.setVisible(true);
        headerExpression.setVisible(true);
        headerInline.setVisible(true);
    }

    private void redesignPropertyPanelView() {
        HeaderValueType type = element.getProperty(VALUE_TYPE);
        Action headerAction = element.getProperty(ACTION);

        if (headerAction == null || type == null) {
            return;
        }

        setDefaultPropertyPanelVisible();

        if (REMOVE.equals(headerAction)) {
            valueType.setVisible(false);
            valueLiteral.setVisible(false);
            headerExpression.setVisible(false);
            headerInline.setVisible(false);
        }

        switch (type) {
            case EXPRESSION:
                valueLiteral.setVisible(false);
                headerInline.setVisible(false);
                break;

            case INLINE:
                applyInlineValueType();
                break;

            default:
                headerExpression.setVisible(false);
                headerInline.setVisible(false);
                break;
        }
    }

    private void applyInlineValueType() {
        valueLiteral.setVisible(false);
        headerExpression.setVisible(false);
        headerName.setVisible(false);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);
        redesignPropertyPanelView();

        displayHeaderActionParameter();

        displayScopeParameter();

        displayValueTypeParameter();

        valueLiteral.setProperty(element.getProperty(VALUE_LITERAL));
        headerName.setProperty(element.getProperty(HEADER_NAME));
        headerExpression.setProperty(element.getProperty(EXPRESSION));
    }

    private void displayHeaderActionParameter() {
        action.setValues(propertyTypeManager.getValuesByName(Action.TYPE_NAME));
        Action headerAction = element.getProperty(ACTION);

        if (headerAction == null) {
            return;
        }

        action.selectValue(headerAction.getValue());
    }

    private void displayScopeParameter() {
        scope.setValues(propertyTypeManager.getValuesByName(ScopeType.TYPE_NAME));
        ScopeType headerScope = element.getProperty(SCOPE);

        if (headerScope == null) {
            return;
        }

        scope.selectValue(headerScope.getValue());
    }

    private void displayValueTypeParameter() {
        valueType.setValues(propertyTypeManager.getValuesByName(HeaderValueType.TYPE_NAME));
        HeaderValueType headerValueType = element.getProperty(VALUE_TYPE);

        if (headerValueType == null) {
            return;
        }

        valueType.selectValue(headerValueType.name());
    }

}