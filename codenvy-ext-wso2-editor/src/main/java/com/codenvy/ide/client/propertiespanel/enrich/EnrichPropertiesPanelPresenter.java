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
package com.codenvy.ide.client.propertiespanel.enrich;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.log.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
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
        element.getSource().setClone(view.getCloneSource());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceTypeChanged() {
        element.getSource().setType(view.getSourceType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcPropertyChanged() {
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
        element.getTarget().setAction(view.getTargetAction());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetTypeChanged() {
        element.getTarget().setType(view.getTargetType());
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
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcTypeChanged() {
        String type = view.getSourceType();
        element.getSource().setType(type);

        setDefaultSourcePanelView();

        view.setVisibleSrcInlineRegisterPanel(false);

        switch (Enrich.SourceType.valueOf(type)) {
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

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcInlineTypeChanged() {
        String inlineType = view.getInlineType();
        element.getSource().setInlineType(inlineType);

        setDefaultSourcePanelView();

        view.setVisibleSrcXPathPanel(false);
        view.setVisibleSrcPropertyPanel(false);

        if (inlineType.equals(Enrich.InlineType.SourceXML.name())) {
            view.setVisibleSrcXMLPanel(true);
            view.setVisibleSrcInlineRegisterPanel(false);
        } else {
            view.setVisibleSrcXMLPanel(false);
            view.setVisibleSrcInlineRegisterPanel(true);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTgtTypeChanged() {
        String targetType = view.getTargetType();
        element.getTarget().setType(targetType);

        setDefaultTargetPanelView();

        switch (Enrich.TargetType.valueOf(targetType)) {
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
    public void onTargetPropertyChanged() {
        element.getTarget().setProperty(view.getTargetProperty());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setCloneSource(propertyTypeManager.getValuesOfTypeByName(Send.EBoolean.TYPE_NAME));
        view.selectCloneSource(element.getSource().getClone());

        view.setSourceType(propertyTypeManager.getValuesOfTypeByName(Enrich.SourceType.TYPE_NAME));
        view.selectSourceType(element.getSource().getType());

        view.setInlineRegisterKey(element.getSource().getInlRegisterKey());

        view.setSourceXpath(element.getSource().getXpath());

        view.setTargetAction(propertyTypeManager.getValuesOfTypeByName(Enrich.TargetAction.TYPE_NAME));
        view.selectTargetAction(element.getTarget().getAction());

        view.setTargetType(propertyTypeManager.getValuesOfTypeByName(Enrich.TargetType.TYPE_NAME));

        view.setInlineType(propertyTypeManager.getValuesOfTypeByName(Enrich.InlineType.INLINE_TYPE));
        view.selectInlineType(element.getSource().getInlineType());

        view.selectTargetType(element.getTarget().getType());

        view.setTargetXpath(element.getTarget().getXpath());

        view.setDescription(element.getDescription());
        view.setProperty(element.getSource().getProperty());
        view.setSrcXml(element.getSource().getSourceXML());
        view.setTargetProperty(element.getTarget().getProperty());
    }

}