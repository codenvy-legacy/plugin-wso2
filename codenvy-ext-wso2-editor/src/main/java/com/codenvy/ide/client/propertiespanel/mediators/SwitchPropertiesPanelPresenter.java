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
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Switch.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Switch.REGEXP_ATTRIBUTE_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.SOURCE_XPATH;

/**
 * The presenter that provides a business logic of 'Switch' mediator properties panel. It provides an ability to work with all properties
 * of 'Switch' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SwitchPropertiesPanelPresenter extends AbstractPropertiesPanel<Switch> {

    private final AddNameSpacesCallBack             addNameSpacesCallBack;
    private final NameSpaceEditorPresenter          nameSpaceEditorPresenter;
    private final Provider<SimplePropertyPresenter> simplePropertyPresenterProvider;
    private final List<SimplePropertyPresenter>     regExpFields;

    private PropertyGroupPresenter   basicGroup;
    private ComplexPropertyPresenter sourceXPath;

    @Inject
    public SwitchPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          WSO2EditorLocalizationConstant locale,
                                          Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                                          Provider<SimplePropertyPresenter> simplePropertyPresenterProvider) {
        super(view, propertyTypeManager);
        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;

        this.simplePropertyPresenterProvider = simplePropertyPresenterProvider;
        this.regExpFields = new ArrayList<>();

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(SOURCE_XPATH, expression);

                sourceXPath.setProperty(expression);

                notifyListeners();
            }
        };

        prepareView(propertiesPanelWidgetFactory, complexPropertyPresenterProvider, locale);
    }

    private void prepareView(@Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             @Nonnull Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider,
                             @Nonnull final WSO2EditorLocalizationConstant locale) {
        basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        view.addGroup(basicGroup);

        sourceXPath = complexPropertyPresenterProvider.get();
        sourceXPath.setTitle(locale.switchSourceXPath());
        sourceXPath.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(NAMESPACES);
                String sourceXPathValue = element.getProperty(SOURCE_XPATH);

                if (nameSpaces == null || sourceXPathValue == null) {
                    return;
                }
                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addNameSpacesCallBack,
                                                                  locale.switchXpathTitle(),
                                                                  sourceXPathValue);

            }
        });
        basicGroup.addItem(sourceXPath);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        sourceXPath.setProperty(element.getProperty(SOURCE_XPATH));

        clearPanel();

        List<Branch> branches = element.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            final Branch branch = branches.get(i);

            if (Switch.DEFAULT_CASE_TITLE.equals(branch.getTitle())) {
                continue;
            }

            SimplePropertyPresenter field = simplePropertyPresenterProvider.get();
            field.setTitle(branch.getTitle() + ' ' + i);
            field.setProperty(branch.getAttributeByName(REGEXP_ATTRIBUTE_NAME));
            field.addPropertyValueChangedListener(new PropertyValueChangedListener() {
                @Override
                public void onPropertyChanged(@Nonnull String property) {
                    branch.addAttribute(REGEXP_ATTRIBUTE_NAME, property);

                    notifyListeners();
                }
            });

            regExpFields.add(field);
            basicGroup.addItem(field);
        }
    }

    private void clearPanel() {
        for (SimplePropertyPresenter field : regExpFields) {
            field.removePropertyValueChangedListeners();
            basicGroup.removeItem(field);
        }

        regExpFields.clear();
    }

}