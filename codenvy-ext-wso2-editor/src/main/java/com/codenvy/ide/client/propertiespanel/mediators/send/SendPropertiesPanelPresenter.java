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
package com.codenvy.ide.client.propertiespanel.mediators.send;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Send;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.editor.WSO2Editor.BOOLEAN_TYPE_NAME;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Send mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class SendPropertiesPanelPresenter extends AbstractPropertiesPanel<Send, SendPropertiesPanelView>
        implements SendPropertiesPanelView.ActionDelegate {


    private final ResourceKeyEditorPresenter keyPresenter;
    private final NameSpaceEditorPresenter   nameSpacePresenter;

    private final AddNameSpacesCallBack     nameSpacesCallBack;
    private final ChangeResourceKeyCallBack keyCallBack;

    private final WSO2EditorLocalizationConstant locale;

    @Inject
    public SendPropertiesPanelPresenter(SendPropertiesPanelView view,
                                        PropertyTypeManager propertyTypeManager,
                                        ResourceKeyEditorPresenter keyPresenter,
                                        NameSpaceEditorPresenter nameSpacePresenter,
                                        WSO2EditorLocalizationConstant locale) {

        super(view, propertyTypeManager);

        this.locale = locale;

        this.keyPresenter = keyPresenter;
        this.nameSpacePresenter = nameSpacePresenter;

        this.nameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setDynamicExpression(expression);
                element.setNameSpaces(nameSpaces);

                SendPropertiesPanelPresenter.this.view.setDynamicSequence(expression);

                notifyListeners();
            }
        };

        this.keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.setStaticExpression(key);

                SendPropertiesPanelPresenter.this.view.setStaticSequence(key);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onSkipSerializationChanged() {
        boolean skipSerialization = Boolean.valueOf(view.getSkipSerialization());
        element.setSkipSerialization(skipSerialization);

        setDefaultPanelView();

        if (!skipSerialization) {
            view.setVisibleDynamicPanel(false);
            view.setVisibleStaticPanel(false);
        } else {
            view.setVisibleRecSeqTypePanel(false);
            view.setVisibleBuildMessagePanel(false);
            view.setVisibleDynamicPanel(false);
            view.setVisibleStaticPanel(false);
            view.setVisibleDescriptionPanel(false);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onReceivingSequencerTypeChanged() {
        applySequenceType();

        notifyListeners();
    }

    /** Sets sequence type value to element from special place of view and displaying properties panel to a certain value of sequence type */
    private void applySequenceType() {
        Send.SequenceType sequenceType = Send.SequenceType.valueOf(view.getReceivingSequencerType());
        element.setSequencerType(sequenceType);

        setDefaultPanelView();

        switch (sequenceType) {
            case Static:
                view.setVisibleDynamicPanel(false);
                break;

            case Dynamic:
                view.setVisibleStaticPanel(false);
                break;

            default:
                view.setVisibleDynamicPanel(false);
                view.setVisibleStaticPanel(false);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onBuildMessageBeforeSendingChanged() {
        element.setBuildMessage(Boolean.valueOf(view.getBuildMessage()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** Sets default value of panel visibility */
    private void setDefaultPanelView() {
        view.setVisibleRecSeqTypePanel(true);
        view.setVisibleBuildMessagePanel(true);
        view.setVisibleDynamicPanel(true);
        view.setVisibleStaticPanel(true);
        view.setVisibleDescriptionPanel(true);
    }

    /** {@inheritDoc} */
    @Override
    public void onStaticReceivingBtnClicked() {
        keyPresenter.showDialog(element.getStaticExpression(), keyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onDynamicReceivingBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getNameSpaces(),
                                                    nameSpacesCallBack,
                                                    locale.sendNamespaceLabel(),
                                                    element.getDynamicExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setSkipSerializationStates(propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME));

        view.setReceivingSequencerTypes(propertyTypeManager.getValuesByName(Send.SequenceType.TYPE_NAME));
        view.selectReceivingSequencerType(element.getSequencerType().name());
        applySequenceType();

        view.setBuildMessageBeforeSending(propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME));
        view.selectBuildMessageBeforeSending(String.valueOf(element.getBuildMessage()));

        view.setDescription(element.getDescription());
        view.setStaticSequence(element.getStaticExpression());
        view.setDynamicSequence(element.getDynamicExpression());
    }

}