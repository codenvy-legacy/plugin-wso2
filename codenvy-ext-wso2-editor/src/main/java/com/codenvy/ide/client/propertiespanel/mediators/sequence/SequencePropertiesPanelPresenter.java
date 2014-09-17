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
package com.codenvy.ide.client.propertiespanel.mediators.sequence;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Sequence;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.Static;

/**
 * The presenter that provides a business logic of 'Sequence' mediator properties panel. It provides an ability to work with all properties
 * of 'Sequence' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class SequencePropertiesPanelPresenter extends AbstractPropertiesPanel<Sequence, SequencePropertiesPanelView>
        implements SequencePropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant localizationConstant;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;

    @Inject
    public SequencePropertiesPanelPresenter(SequencePropertiesPanelView view,
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
                element.setDynamicReferenceKey(expression);

                SequencePropertiesPanelPresenter.this.view.setDynamicReferenceKey(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onReferringTypeChanged() {
        redrawPropertiesPanel();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onStaticReferenceKeyChanged() {
        element.setStaticReferenceKey(view.getStaticReferenceKey());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditExpressionButtonClicked() {
        nameSpaceEditorPresenter.showWindowWithParameters(element.getNameSpaces(),
                                                          addNameSpacesCallBack,
                                                          localizationConstant.sequenceExpressionTitle(),
                                                          element.getDynamicReferenceKey());
    }

    private void redrawPropertiesPanel() {
        ReferringType referringType = ReferringType.valueOf(view.getReferringType());
        element.setReferringType(referringType);

        boolean isStatic = Static.equals(referringType);

        view.setVisibleStaticReferenceKeyPanel(isStatic);
        view.setVisibleDynamicReferenceKeyPanel(!isStatic);

        view.setStaticReferenceKey(element.getStaticReferenceKey());
        view.setDynamicReferenceKey(element.getDynamicReferenceKey());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setReferringTypes(propertyTypeManager.getValuesByName(ReferringType.TYPE_NAME));
        view.selectReferringType(element.getReferringType().name());

        redrawPropertiesPanel();
    }

}