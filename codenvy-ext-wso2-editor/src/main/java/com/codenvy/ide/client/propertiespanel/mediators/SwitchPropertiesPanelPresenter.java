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
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
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
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The presenter that provides a business logic of 'Switch' mediator properties panel. It provides an ability to work with all properties
 * of 'Switch' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SwitchPropertiesPanelPresenter extends AbstractPropertiesPanel<Switch> {

    private final NameSpaceEditorPresenter nameSpaceEditorPresenter;
    private final AddNameSpacesCallBack    addNameSpacesCallBack;

    private final Provider<SimplePropertyPresenter> simplePropertyPresenterProvider;
    private final List<SimplePropertyPresenter>     regExpFields;

    private PropertyGroupPresenter   basicGroup;
    private ComplexPropertyPresenter sourceXPath;

    @Inject
    public SwitchPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          Provider<SimplePropertyPresenter> simplePropertyPresenterProvider,
                                          WSO2EditorLocalizationConstant locale,
                                          PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

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

        prepareView();
    }

    private void prepareView() {
        basicGroup = createGroup(locale.miscGroupTitle());

        EditButtonClickedListener sourceXpathListener = new EditButtonClickedListener() {
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
        };
        sourceXPath = createComplexProperty(basicGroup, locale.switchSourceXPath(), sourceXpathListener);
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

            createSimplePanel(branch, i);
        }
    }

    private void createSimplePanel(@Nonnull final Branch branch, int panelNumber) {
        SimplePropertyPresenter panel = simplePropertyPresenterProvider.get();

        panel.setTitle(branch.getTitle() + ' ' + panelNumber);
        panel.setProperty(branch.getAttributeByName(REGEXP_ATTRIBUTE_NAME));

        panel.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                branch.addAttribute(REGEXP_ATTRIBUTE_NAME, property);

                notifyListeners();
            }
        });

        regExpFields.add(panel);
        basicGroup.addItem(panel);
    }

    private void clearPanel() {
        for (SimplePropertyPresenter field : regExpFields) {
            field.removePropertyValueChangedListeners();
            basicGroup.removeItem(field);
        }

        regExpFields.clear();
    }

}