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
public class EnrichPropertiesPanelPresenter extends AbstractPropertiesPanel<Enrich>
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

                ((EnrichPropertiesPanelView)EnrichPropertiesPanelPresenter.this.view).setSourceXpath(expression);

                notifyListeners();
            }
        };

        this.targetCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                element.getTarget().setXpath(expression);
                element.getTarget().setNameSpaces(nameSpaces);

                ((EnrichPropertiesPanelView)EnrichPropertiesPanelPresenter.this.view).setTargetXpath(expression);

                notifyListeners();
            }
        };

        this.inlineCallback = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.getSource().setSourceXML(inline);

                ((EnrichPropertiesPanelView)EnrichPropertiesPanelPresenter.this.view).setInlineXml(inline);

                notifyListeners();
            }
        };

        this.keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.getSource().setInlRegisterKey(key);

                ((EnrichPropertiesPanelView)EnrichPropertiesPanelPresenter.this.view).setInlineRegisterKey(key);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onCloneSourceChanged() {
        element.getSource().setClone(((EnrichPropertiesPanelView)view).getCloneSource());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceTypeChanged() {
        element.getSource().setType(((EnrichPropertiesPanelView)view).getSourceType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcPropertyChanged() {
        element.getSource().setProperty(((EnrichPropertiesPanelView)view).getSrcProperty());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSourceXpathChanged() {
        element.getSource().setXpath(((EnrichPropertiesPanelView)view).getSourceXpath());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetActionChanged() {
        element.getTarget().setAction(((EnrichPropertiesPanelView)view).getTargetAction());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetTypeChanged() {
        element.getTarget().setType(((EnrichPropertiesPanelView)view).getTargetType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTargetXpathChanged() {
        element.getTarget().setXpath(((EnrichPropertiesPanelView)view).getTargetXpath());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((EnrichPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcTypeChanged() {
        String type = ((EnrichPropertiesPanelView)view).getSourceType();
        element.getSource().setType(type);

        setDefaultSourcePanelView();

        ((EnrichPropertiesPanelView)view).setVisibleSrcInlineRegisterPanel(false);

        switch (Enrich.SourceType.valueOf(type)) {
            case custom:
                ((EnrichPropertiesPanelView)view).setVisibleSrcPropertyPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcInlineTypePanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(false);
                break;

            case property:
                ((EnrichPropertiesPanelView)view).setVisibleSrcXPathPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcInlineTypePanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(false);
                break;

            case inline:
                ((EnrichPropertiesPanelView)view).setVisibleSrcXPathPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcPropertyPanel(false);
                break;

            default:
                ((EnrichPropertiesPanelView)view).setVisibleSrcXPathPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcPropertyPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcInlineTypePanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(false);
                break;
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSrcInlineTypeChanged() {
        String inlineType = ((EnrichPropertiesPanelView)view).getInlineType();
        element.getSource().setInlineType(inlineType);

        setDefaultSourcePanelView();

        ((EnrichPropertiesPanelView)view).setVisibleSrcXPathPanel(false);
        ((EnrichPropertiesPanelView)view).setVisibleSrcPropertyPanel(false);

        if (inlineType.equals(Enrich.InlineType.SourceXML.name())) {
            ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(true);
            ((EnrichPropertiesPanelView)view).setVisibleSrcInlineRegisterPanel(false);
        } else {
            ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(false);
            ((EnrichPropertiesPanelView)view).setVisibleSrcInlineRegisterPanel(true);
        }

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onTgtTypeChanged() {
        String targetType = ((EnrichPropertiesPanelView)view).getTargetType();
        element.getTarget().setType(targetType);

        setDefaultTargetPanelView();

        switch (Enrich.TargetType.valueOf(targetType)) {
            case custom:
                ((EnrichPropertiesPanelView)view).setVisibleTargetPropertyPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleTargetXPathPanel(true);
                break;

            case property:
                ((EnrichPropertiesPanelView)view).setVisibleTargetPropertyPanel(true);
                ((EnrichPropertiesPanelView)view).setVisibleTargetXPathPanel(false);
                break;

            default:
                ((EnrichPropertiesPanelView)view).setVisibleTargetPropertyPanel(false);
                ((EnrichPropertiesPanelView)view).setVisibleTargetXPathPanel(false);
                break;
        }

        notifyListeners();
    }

    /** Sets default value of source panel visibility */
    private void setDefaultSourcePanelView() {
        ((EnrichPropertiesPanelView)view).setVisibleSrcInlineRegisterPanel(true);
        ((EnrichPropertiesPanelView)view).setVisibleSrcInlineTypePanel(true);
        ((EnrichPropertiesPanelView)view).setVisibleSrcPropertyPanel(true);
        ((EnrichPropertiesPanelView)view).setVisibleSrcXMLPanel(true);
        ((EnrichPropertiesPanelView)view).setVisibleSrcXPathPanel(true);
    }

    /** Sets default value of target panel visibility */
    private void setDefaultTargetPanelView() {
        ((EnrichPropertiesPanelView)view).setVisibleTargetPropertyPanel(true);
        ((EnrichPropertiesPanelView)view).setVisibleTargetXPathPanel(true);
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
        element.getTarget().setProperty(((EnrichPropertiesPanelView)view).getTargetProperty());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((EnrichPropertiesPanelView)view).setCloneSource(propertyTypeManager.getValuesOfTypeByName(Send.EBoolean.TYPE_NAME));
        ((EnrichPropertiesPanelView)view).selectCloneSource(element.getSource().getClone());

        ((EnrichPropertiesPanelView)view).setSourceType(propertyTypeManager.getValuesOfTypeByName(Enrich.SourceType.TYPE_NAME));
        ((EnrichPropertiesPanelView)view).selectSourceType(element.getSource().getType());

        ((EnrichPropertiesPanelView)view).setInlineRegisterKey(element.getSource().getInlRegisterKey());

        ((EnrichPropertiesPanelView)view).setSourceXpath(element.getSource().getXpath());

        ((EnrichPropertiesPanelView)view).setTargetAction(propertyTypeManager.getValuesOfTypeByName(Enrich.TargetAction.TYPE_NAME));
        ((EnrichPropertiesPanelView)view).selectTargetAction(element.getTarget().getAction());

        ((EnrichPropertiesPanelView)view).setTargetType(propertyTypeManager.getValuesOfTypeByName(Enrich.TargetType.TYPE_NAME));

        ((EnrichPropertiesPanelView)view).setInlineType(propertyTypeManager.getValuesOfTypeByName(Enrich.InlineType.INLINE_TYPE));
        ((EnrichPropertiesPanelView)view).selectInlineType(element.getSource().getInlineType());

        ((EnrichPropertiesPanelView)view).selectTargetType(element.getTarget().getType());

        ((EnrichPropertiesPanelView)view).setTargetXpath(element.getTarget().getXpath());

        ((EnrichPropertiesPanelView)view).setDescription(element.getDescription());
        ((EnrichPropertiesPanelView)view).setProperty(element.getSource().getProperty());
        ((EnrichPropertiesPanelView)view).setSrcXml(element.getSource().getSourceXML());
        ((EnrichPropertiesPanelView)view).setTargetProperty(element.getTarget().getProperty());
    }

}