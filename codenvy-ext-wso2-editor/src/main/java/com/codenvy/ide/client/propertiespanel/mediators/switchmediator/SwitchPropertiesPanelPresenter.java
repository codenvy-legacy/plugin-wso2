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
package com.codenvy.ide.client.propertiespanel.mediators.switchmediator;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.switchmediator.branch.BranchFiledPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The presenter that provides a business logic of 'Switch' mediator properties panel. It provides an ability to work with all properties
 * of 'Switch' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class SwitchPropertiesPanelPresenter extends AbstractPropertiesPanel<Switch, SwitchPropertiesPanelView>
        implements SwitchPropertiesPanelView.ActionDelegate, BranchFiledPresenter.BranchChangedListener {

    private final PropertiesPanelWidgetFactory   propertiesPanelWidgetFactory;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant localizationConstant;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;
    private final List<BranchFiledPresenter>     regExpFields;

    @Inject
    public SwitchPropertiesPanelPresenter(SwitchPropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                          NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                          WSO2EditorLocalizationConstant localizationConstant) {
        super(view, propertyTypeManager);

        this.propertiesPanelWidgetFactory = propertiesPanelWidgetFactory;
        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.localizationConstant = localizationConstant;
        this.regExpFields = new ArrayList<>();

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setNameSpaces(nameSpaces);
                element.setSourceXpath(expression);

                SwitchPropertiesPanelPresenter.this.view.setSourceXpath(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onEditXpathButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getNameSpaces(),
                                                          addNameSpacesCallBack,
                                                          localizationConstant.switchXpathTitle(),
                                                          element.getSourceXpath());
    }

    /** {@inheritDoc} */
    @Override
    public void onBranchChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onWidgetDetached() {
        view.removeBranchFields();

        for (BranchFiledPresenter field : regExpFields) {
            field.removeBranchChangedListener(this);
        }

        regExpFields.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setSourceXpath(element.getSourceXpath());

        onWidgetDetached();

        List<Branch> branches = element.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            Branch branch = branches.get(i);

            if (Switch.DEFAULT_CASE_TITLE.equals(branch.getTitle())) {
                continue;
            }

            BranchFiledPresenter field = propertiesPanelWidgetFactory.createBranchFieldPresenter(branch, i);
            field.addBranchChangedListener(this);

            regExpFields.add(field);
            view.addBranchField(field);
        }
    }

}