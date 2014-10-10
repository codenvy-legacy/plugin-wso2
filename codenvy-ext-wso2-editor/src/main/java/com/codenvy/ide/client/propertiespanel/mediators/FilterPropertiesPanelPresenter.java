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
import com.codenvy.ide.client.elements.mediators.Filter;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Filter.CONDITION_TYPE;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.XPATH;
import static com.codenvy.ide.client.elements.mediators.Filter.REGULAR_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.XPATH_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.X_PATH;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Filter mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class FilterPropertiesPanelPresenter extends AbstractPropertiesPanel<Filter> {

    private final NameSpaceEditorPresenter nameSpaceEditorPresenter;

    private final AddNameSpacesCallBack addSourceNameSpacesCallBack;
    private final AddNameSpacesCallBack addXPathNameSpacesCallBack;

    private ListPropertyPresenter    conditionType;
    private ComplexPropertyPresenter source;
    private ComplexPropertyPresenter xPath;
    private SimplePropertyPresenter  regularExpression;

    @Inject
    public FilterPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          WSO2EditorLocalizationConstant locale,
                                          PropertyPanelFactory propertyPanelFactory,
                                          SelectionManager selectionManager) {

        super(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        addSourceNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(SOURCE_NAMESPACE, nameSpaces);
                element.putProperty(SOURCE, expression != null ? expression : "");

                source.setProperty(expression);

                notifyListeners();
            }
        };

        addXPathNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(XPATH_NAMESPACE, nameSpaces);
                element.putProperty(X_PATH, expression);

                xPath.setProperty(expression);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener conditionTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                redesignViewToConditionType(ConditionType.valueOf(property));

                notifyListeners();
            }
        };
        conditionType = createListProperty(basicGroup, locale.conditionType(), conditionTypeListener);

        EditButtonClickedListener sourceNameSpaceBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(SOURCE_NAMESPACE);
                String source = element.getProperty(SOURCE);

                if (source == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addSourceNameSpacesCallBack,
                                                                  locale.filterSourceTitle(),
                                                                  source);
            }
        };

        source = createComplexProperty(basicGroup, locale.filterSource(), sourceNameSpaceBtnListener);

        PropertyValueChangedListener regularExprListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(REGULAR_EXPRESSION, property);

                notifyListeners();
            }
        };
        regularExpression = createSimpleProperty(basicGroup, locale.regularExpression(), regularExprListener);

        EditButtonClickedListener xpathBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(XPATH_NAMESPACE);
                String xPath = element.getProperty(X_PATH);

                if (xPath == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addXPathNameSpacesCallBack,
                                                                  locale.filterXpathTitle(),
                                                                  xPath);
            }
        };

        xPath = createComplexProperty(basicGroup, locale.filterXpath(), xpathBtnListener);
    }

    /** Modifies the view of the property panel depending on the condition type of filter element. */
    private void redesignViewToConditionType(@Nonnull ConditionType conditionType) {
        element.putProperty(CONDITION_TYPE, conditionType);

        if (XPATH.equals(conditionType)) {
            redesignViewToXPathConditionType();
        } else {
            redesignViewToDefaultConditionType();
        }
    }

    private void redesignViewToXPathConditionType() {
        xPath.setProperty(element.getProperty(X_PATH));

        source.setVisible(false);
        regularExpression.setVisible(false);
        xPath.setVisible(true);
    }

    private void redesignViewToDefaultConditionType() {
        source.setProperty(element.getProperty(SOURCE));
        regularExpression.setProperty(element.getProperty(REGULAR_EXPRESSION));

        source.setVisible(true);
        regularExpression.setVisible(true);
        xPath.setVisible(false);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        conditionType.setValues(propertyTypeManager.getValuesByName(ConditionType.TYPE_NAME));

        ConditionType cType = element.getProperty(CONDITION_TYPE);
        if (cType != null) {
            conditionType.selectValue(cType.name());
            redesignViewToConditionType(cType);
        }
    }

}