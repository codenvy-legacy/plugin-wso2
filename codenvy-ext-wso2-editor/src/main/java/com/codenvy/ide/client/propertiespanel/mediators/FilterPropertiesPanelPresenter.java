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
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Filter.CONDITION_TYPE;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType;
import static com.codenvy.ide.client.elements.mediators.Filter.REGULAR_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.XPATH_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.X_PATH;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Filter mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class FilterPropertiesPanelPresenter extends AbstractPropertiesPanel<Filter, PropertiesPanelView> {
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant local;

    private ListPropertyPresenter    conditionType;
    private ComplexPropertyPresenter source;
    private ComplexPropertyPresenter xPath;
    private SimplePropertyPresenter  regularExpression;


    @Inject
    public FilterPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                          WSO2EditorLocalizationConstant local,
                                          ListPropertyPresenter conditionType,
                                          Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                          SimplePropertyPresenter regularExpression) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.local = local;

        prepareView(propertiesPanelWidgetFactory, conditionType, complexPropertyPresenterProvider, regularExpression);
    }

    private void prepareView(@Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             @Nonnull ListPropertyPresenter conditionType,
                             @Nonnull Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                             @Nonnull SimplePropertyPresenter regularExpression) {
        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(local.miscGroupTitle());
        this.view.addGroup(basicGroup);

        this.conditionType = conditionType;
        this.conditionType.setTitle(local.conditionType());
        this.conditionType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                redesignViewToConditionType(ConditionType.valueOf(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(this.conditionType);

        final AddNameSpacesCallBack addSourceNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(SOURCE_NAMESPACE, nameSpaces);
                element.putProperty(SOURCE, expression != null ? expression : "");

                source.setProperty(expression);

                notifyListeners();
            }
        };

        source = complexPropertyPresenterProvider.get();
        source.setTitle(local.filterSource());
        source.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(SOURCE_NAMESPACE);
                String source = element.getProperty(SOURCE);

                if (source == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addSourceNameSpacesCallBack,
                                                                  local.filterSourceTitle(),
                                                                  source);
            }
        });

        basicGroup.addItem(this.source);

        this.regularExpression = regularExpression;
        this.regularExpression.setTitle(local.regularExpression());
        this.regularExpression.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(REGULAR_EXPRESSION, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(this.regularExpression);

        final AddNameSpacesCallBack addXPathNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nullable String expression) {
                element.putProperty(XPATH_NAMESPACE, nameSpaces);
                element.putProperty(X_PATH, expression != null ? expression : "");

                xPath.setProperty(expression);

                notifyListeners();
            }
        };

        xPath = complexPropertyPresenterProvider.get();
        xPath.setTitle(local.filterXpath());
        xPath.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(XPATH_NAMESPACE);
                String xPath = element.getProperty(X_PATH);

                if (xPath == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addXPathNameSpacesCallBack,
                                                                  local.filterXpathTitle(),
                                                                  xPath);
            }
        });

        basicGroup.addItem(xPath);
    }

    /** Modifies the view of the property panel depending on the condition type of filter element. */
    private void redesignViewToConditionType(@Nonnull ConditionType conditionType) {
        element.putProperty(CONDITION_TYPE, conditionType);

        switch (conditionType) {
            case XPATH:
                xPath.setProperty(element.getProperty(X_PATH));

                source.setVisible(false);
                regularExpression.setVisible(false);
                xPath.setVisible(true);

                break;

            case SOURCE_AND_REGEX:
            default:
                source.setProperty(element.getProperty(SOURCE));
                regularExpression.setProperty(element.getProperty(REGULAR_EXPRESSION));

                source.setVisible(true);
                regularExpression.setVisible(true);
                xPath.setVisible(false);
        }
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