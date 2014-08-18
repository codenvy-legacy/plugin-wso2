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
package com.codenvy.ide.client.propertiespanel.call;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The presenter that provides a business logic of 'Call' mediator properties panel. It provides an ability to work with all properties
 * of 'Call' mediator.
 *
 * @author Andrey Plotnikov
 */
public class CallPropertiesPanelPresenter extends AbstractPropertiesPanel<Call, CallPropertiesPanelView>
        implements CallPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant localizationConstant;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;

    @Inject
    public CallPropertiesPanelPresenter(CallPropertiesPanelView view,
                                        PropertyTypeManager propertyTypeManager,
                                        NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                        WSO2EditorLocalizationConstant localizationConstant) {
        super(view, propertyTypeManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.localizationConstant = localizationConstant;

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.setNameSpaces(nameSpaces);
                element.setXpath(expression);

                CallPropertiesPanelPresenter.this.view.setEndpointXpath(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onEndpointTypeChanged() {
        redesignViewToEndpointType();

        notifyListeners();
    }

    private void redesignViewToEndpointType() {
        Call.EndpointType endpointType = Call.EndpointType.valueOf(view.getEndpointType());
        element.setEndpointType(endpointType);

        switch (endpointType) {
            case REGISTRYKEY:
                view.setVisibleEndpointXpathPanel(false);
                view.setVisibleEndpointRegistryKeyPanel(true);

                view.setEndpointRegistryKey(element.getRegistryKey());
                break;

            case XPATH:
                view.setVisibleEndpointXpathPanel(true);
                view.setVisibleEndpointRegistryKeyPanel(false);

                view.setEndpointXpath(element.getXpath());
                break;

            case NONE:
            case INLINE:
            default:
                view.setVisibleEndpointXpathPanel(false);
                view.setVisibleEndpointRegistryKeyPanel(false);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onEndpointRegisterKeyChanged() {
        element.setRegistryKey(view.getEndpointRegistryKey());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditRegistryXpathButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getNameSpaces(),
                                                          addNameSpacesCallBack,
                                                          localizationConstant.callExpressionTitle(),
                                                          element.getXpath());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setEndpointTypes(propertyTypeManager.getValuesByName(Call.EndpointType.TYPE_NAME));
        view.selectEndpointType(element.getEndpointType().name());

        redesignViewToEndpointType();

        view.setDescription(element.getDescription());
    }

}