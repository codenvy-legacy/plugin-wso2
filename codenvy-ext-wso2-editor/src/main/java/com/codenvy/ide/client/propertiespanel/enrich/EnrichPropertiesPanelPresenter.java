/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.propertiespanel.enrich;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.enrich.Target;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.editor.WSO2Editor.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType.SourceXML;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType.custom;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Call mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class EnrichPropertiesPanelPresenter extends AbstractPropertiesPanel<Enrich, EnrichPropertiesPanelView>
        implements EnrichPropertiesPanelView.ActionDelegate {

    private final NameSpaceEditorPresenter     nameSpacePresenter;
    private final ResourceKeyEditorPresenter   keyPresenter;
    private final InlineConfigurationPresenter inlinePresenter;

    private final AddNameSpacesCallBack sourceCallBack;
    private final AddNameSpacesCallBack targetCallBack;

    private final ChangeInlineFormatCallBack inlineCallback;
    private final ChangeResourceKeyCallBack  keyCallBack;

    private final WSO2EditorLocalizationConstant locale;

    @Inject
    public EnrichPropertiesPanelPresenter(EnrichPropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          WSO2EditorLocalizationConstant locale,
                                          ResourceKeyEditorPresenter keyPresenter,
                                          InlineConfigurationPresenter inlinePresenter) {
        super(view, propertyTypeManager);

        this.locale = locale;
        this.keyPresenter = keyPresenter;
        this.inlinePresenter = inlinePresenter;
        this.nameSpacePresenter = nameSpacePresenter;

        this.sourceCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.getSource().setXpath(expression);
                element.getSource().setNameSpaces(nameSpaces);

                EnrichPropertiesPanelPresenter.this.view.setSourceXpath(expression);

                notifyListeners();
            }
        };

        this.targetCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.getTarget().setXpath(expression);
                element.getTarget().setNameSpaces(nameSpaces);

                EnrichPropertiesPanelPresenter.this.view.setTargetXpath(expression);

                notifyListeners();
            }
        };

        this.inlineCallback = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.getSource().setSourceXML(inline);

                EnrichPropertiesPanelPresenter.this.view.setInlineXml(inline);

                notifyListeners();
            }
        };

        this.keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.getSource().setInlRegisterKey(key);

                EnrichPropertiesPanelPresenter.this.view.setInlineRegisterKey(key);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onCloneSourceChanged() {
        element.getSource().setClone(Boolean.valueOf(view.getCloneSource()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourcePropertyChanged() {
        element.getSource().setProperty(view.getSrcProperty());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceXpathChanged() {
        element.getSource().setXpath(view.getSourceXpath());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetActionChanged() {
        element.getTarget().setAction(Target.TargetAction.valueOf(view.getTargetAction()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetXpathChanged() {
        element.getTarget().setXpath(view.getTargetXpath());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetPropertyChanged() {
        element.getTarget().setProperty(view.getTargetProperty());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** Sets source type value to element from special place of view and displaying properties panel to a certain value of source type */
    private void applySourceType() {
        SourceType type = SourceType.valueOf(view.getSourceType());
        element.getSource().setType(type);

        setDefaultSourcePanelView();

        view.setVisibleSrcInlineRegisterPanel(false);

        switch (type) {
            case custom:
                view.setVisibleSrcPropertyPanel(false);
                view.setVisibleSrcInlineTypePanel(false);
                view.setVisibleSrcXMLPanel(false);
                break;

            case property:
                view.setVisibleSrcXPathPanel(false);
                view.setVisibleSrcInlineTypePanel(false);
                view.setVisibleSrcXMLPanel(false);
                break;

            case inline:
                view.setVisibleSrcXPathPanel(false);
                view.setVisibleSrcPropertyPanel(false);
                break;

            default:
                view.setVisibleSrcXPathPanel(false);
                view.setVisibleSrcPropertyPanel(false);
                view.setVisibleSrcInlineTypePanel(false);
                view.setVisibleSrcXMLPanel(false);
                break;
        }
    }

    /**
     * Sets source inline type value to element from special place of view and displaying properties panel to a certain value of source
     * inline type
     */
    private void applySourceInlineType() {
        InlineType inlineType = InlineType.valueOf(view.getInlineType());
        element.getSource().setInlineType(inlineType);

        setDefaultSourcePanelView();

        view.setVisibleSrcXPathPanel(false);
        view.setVisibleSrcPropertyPanel(false);

        if (SourceXML.equals(inlineType)) {
            view.setVisibleSrcXMLPanel(true);
            view.setVisibleSrcInlineRegisterPanel(false);
        } else {
            view.setVisibleSrcXMLPanel(false);
            view.setVisibleSrcInlineRegisterPanel(true);
        }
    }

    /** Sets target type value to element from special place of view and displaying properties panel to a certain value of target type */
    private void applyTargetType() {
        TargetType targetType = TargetType.valueOf(view.getTargetType());
        element.getTarget().setType(targetType);

        setDefaultTargetPanelView();

        switch (targetType) {
            case custom:
                view.setVisibleTargetPropertyPanel(false);
                view.setVisibleTargetXPathPanel(true);
                break;

            case property:
                view.setVisibleTargetPropertyPanel(true);
                view.setVisibleTargetXPathPanel(false);
                break;

            default:
                view.setVisibleTargetPropertyPanel(false);
                view.setVisibleTargetXPathPanel(false);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceTypeChanged() {
        applySourceType();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceInlineTypeChanged() {
        applySourceInlineType();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetTypeChanged() {
        applyTargetType();

        notifyListeners();
    }

    /** Sets default value of source panel visibility */
    private void setDefaultSourcePanelView() {
        view.setVisibleSrcInlineRegisterPanel(true);
        view.setVisibleSrcInlineTypePanel(true);
        view.setVisibleSrcPropertyPanel(true);
        view.setVisibleSrcXMLPanel(true);
        view.setVisibleSrcXPathPanel(true);
    }

    /** Sets default value of target panel visibility */
    private void setDefaultTargetPanelView() {
        view.setVisibleTargetPropertyPanel(true);
        view.setVisibleTargetXPathPanel(true);
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcXMLBtnClicked() {
        inlinePresenter.showDialog(element.getSource().getSourceXML(), locale.titleEnrichInline(), inlineCallback);
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcRegistryKeyBtnClicked() {
        keyPresenter.showDialog(element.getSource().getInlRegisterKey(), keyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcXPathBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getSource().getNameSpaces(),
                                                    sourceCallBack,
                                                    locale.enrichSrcLabel(),
                                                    element.getSource().getXpath());
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetXPathBtnClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getTarget().getNameSpaces(),
                                                    targetCallBack,
                                                    locale.namespacedProperty(),
                                                    element.getTarget().getXpath());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setCloneSources(propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME));
        view.selectCloneSource(String.valueOf(element.getSource().getClone()));

        view.setSourceType(propertyTypeManager.getValuesByName(SourceType.TYPE_NAME));
        view.selectSourceType(element.getSource().getType().name());

        view.setTargetActions(propertyTypeManager.getValuesByName(Target.TargetAction.TYPE_NAME));
        view.selectTargetAction(element.getTarget().getAction().name());

        view.setTargetTypes(propertyTypeManager.getValuesByName(TargetType.TYPE_NAME));
        view.selectTargetType(element.getTarget().getType().name());

        view.setInlineTypes(propertyTypeManager.getValuesByName(InlineType.INLINE_TYPE));
        view.selectInlineType(element.getSource().getInlineType().name());

        view.setInlineRegisterKey(element.getSource().getInlRegisterKey());
        view.setSourceXpath(element.getSource().getXpath());
        view.setTargetXpath(element.getTarget().getXpath());
        view.setDescription(element.getDescription());
        view.setProperty(element.getSource().getProperty());
        view.setSrcXml(element.getSource().getSourceXML());
        view.setTargetProperty(element.getTarget().getProperty());

        if (!custom.equals(element.getSource().getType())) {
            applySourceType();
        }

        if (!SourceXML.equals(element.getSource().getInlineType())) {
            applySourceInlineType();
        }

        if (!TargetType.custom.equals(element.getTarget().getType())) {
            applyTargetType();
        }
    }

}