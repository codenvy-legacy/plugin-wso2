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

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Enrich' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(EnrichPropertiesPanelViewImpl.class)
public abstract class EnrichPropertiesPanelView extends AbstractView<EnrichPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link EnrichPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user change properties of Enrich mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed clone source. */
        void onCloneSourceChanged();

        /** Performs any actions appropriate in response to the user having changed property. */
        void onSourcePropertyChanged();

        /** Performs any actions appropriate in response to the user having changed XPath. */
        void onSourceXpathChanged();

        /** Performs any actions appropriate in response to the user having changed target action. */
        void onTargetActionChanged();

        /** Performs any actions appropriate in response to the user having changed target XPath. */
        void onTargetXpathChanged();

        /** Performs any actions appropriate in response to the user having changed description. */
        void onDescriptionChanged();

        /** Performs any actions appropriate in response to the user having changed source type. */
        void onSourceTypeChanged();

        /** Performs any actions appropriate in response to the user having changed source inline type. */
        void onSourceInlineTypeChanged();

        /** Performs any actions appropriate in response to the user having changed target type. */
        void onTargetTypeChanged();

        /** Shows the dialog window which needed to be for editing source xml of source element. */
        void onSrcXMLBtnClicked();

        /** Shows the dialog window which needed to be for editing registry key of source element. */
        void onSrcRegistryKeyBtnClicked();

        /** Shows the dialog window which needed to be for editing name spaces and xpath of source element. */
        void onSrcXPathBtnClicked();

        /** Shows the dialog window which needed to be for editing name spaces and xpath of target element. */
        void onTargetXPathBtnClicked();

        /** Performs any actions appropriate in response to the user having changed target property. */
        void onTargetPropertyChanged();

    }

    /** @return target property value from the special place on the view which uses for showing target property parameter */
    @Nonnull
    public abstract String getTargetProperty();

    /**
     * Sets target property special place on the view which uses for showing property.
     *
     * @param property
     *         property which need to set to special place
     */
    public abstract void setTargetProperty(@Nonnull String property);

    /**
     * Sets xml to the special place on the view which uses for showing xml.
     *
     * @param xml
     *         xml which need to set to special place
     */
    public abstract void setSrcXml(@Nonnull String xml);

    /** @return property value from the special place on the view which uses for showing property parameter */
    @Nonnull
    public abstract String getSrcProperty();

    /**
     * Sets property value to the special place on the view which uses for showing property.
     *
     * @param property
     *         property which need to set to special place
     */
    public abstract void setProperty(@Nonnull String property);

    /**
     * Sets inline register key value to the special place on the view which uses for showing register key.
     *
     * @param key
     *         inline register key which need to set to special place
     */
    public abstract void setInlineRegisterKey(@Nonnull String key);

    /**
     * Sets inline xml value to the special place on the view which uses for showing inline xml.
     *
     * @param inlineXml
     *         inline xml types which need to set to special list box
     */
    public abstract void setInlineXml(@Nonnull String inlineXml);

    /** @return inline type value from the special place on the view which uses for showing inline type parameter */
    @Nonnull
    public abstract String getInlineType();

    /**
     * Sets inline type value to the special place on the view which uses for showing inline type parameter.
     *
     * @param inlineTypes
     *         list of inline types which need to set to special list box
     */
    public abstract void setInlineTypes(@Nullable List<String> inlineTypes);

    /**
     * Select inline type format in place on view.
     *
     * @param inlineType
     *         inline type format value
     */
    public abstract void selectInlineType(@Nonnull String inlineType);

    /** @return clone source value from the special place on the view which uses for showing clone source parameter */
    @Nonnull
    public abstract String getCloneSource();

    /**
     * Select clone source format in place on view.
     *
     * @param cloneSource
     *         clone source format value
     */
    public abstract void selectCloneSource(@Nonnull String cloneSource);

    /**
     * Sets clone source value to the special place on the view which uses for showing clone source parameter.
     *
     * @param cloneSources
     *         list of clone sources which need to set to special list box
     */
    public abstract void setCloneSources(@Nullable List<String> cloneSources);

    /** @return source type value from the special place on the view which uses for showing source type parameter */
    @Nonnull
    public abstract String getSourceType();

    /**
     * Select source type format in place on view.
     *
     * @param sourceType
     *         source type format value
     */
    public abstract void selectSourceType(@Nonnull String sourceType);

    /**
     * Sets source type value to the special place on the view which uses for showing source type parameter.
     *
     * @param sourceType
     *         list of source types which need to set to special list box
     */
    public abstract void setSourceType(@Nullable List<String> sourceType);

    @Nonnull
    public abstract String getSourceXpath();

    /**
     * Sets source XPath value to the special place on the view which uses for showing header name parameter.
     *
     * @param sourceXpath
     *         value of source XPath which need to set to special place of view
     */
    public abstract void setSourceXpath(@Nullable String sourceXpath);

    /** @return target action value from the special place on the view which uses for showing target action parameter */
    @Nonnull
    public abstract String getTargetAction();

    /**
     * Select target action format in place on view.
     *
     * @param targetAction
     *         target action format value
     */
    public abstract void selectTargetAction(@Nonnull String targetAction);

    /**
     * Sets source type value to the special place on the view which uses for showing source type parameter.
     *
     * @param targetActions
     *         list of target actions which need to set to special list box
     */
    public abstract void setTargetActions(@Nullable List<String> targetActions);

    /** @return target type value from the special place on the view which uses for showing target type parameter */
    @Nonnull
    public abstract String getTargetType();

    /**
     * Select target type format in place on view.
     *
     * @param targetType
     *         target type format value
     */
    public abstract void selectTargetType(@Nonnull String targetType);

    /**
     * Sets target type value to the special place on the view which uses for showing target type parameter.
     *
     * @param targetTypes
     *         list of target types which need to set to special list box
     */
    public abstract void setTargetTypes(@Nullable List<String> targetTypes);

    /** @return target xpath value from the special place on the view which uses for showing target xpath parameter */
    @Nonnull
    public abstract String getTargetXpath();

    /**
     * Sets target XPath value to the special place on the view which uses for showing target XPath parameter.
     *
     * @param targetXpath
     *         value of target XPath which need to set to special place of view
     */
    public abstract void setTargetXpath(@Nullable String targetXpath);

    /** @return description value from the special place on the view which uses for showing description parameter */
    @Nonnull
    public abstract String getDescription();

    /**
     * Sets description value to the special place on the view which uses for showing description parameter.
     *
     * @param description
     *         value of description which need to set to special place of view
     */
    public abstract void setDescription(@Nullable String description);

    /**
     * Set visible source XML panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show source XML panel, <code>false</code> not to show
     */
    public abstract void setVisibleSrcXMLPanel(boolean isVisible);

    /**
     * Set visible source XPath panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show source XPath panel, <code>false</code> not to show
     */
    public abstract void setVisibleSrcXPathPanel(boolean isVisible);

    /**
     * Set visible source inline register panel on view.
     *
     * @param isVisible
     *         <code>true</code> to source inline register name panel, <code>false</code> not to show
     */
    public abstract void setVisibleSrcInlineRegisterPanel(boolean isVisible);

    /**
     * Set visible source inline type panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show source inline type panel, <code>false</code> not to show
     */
    public abstract void setVisibleSrcInlineTypePanel(boolean isVisible);

    /**
     * Set visible source property panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show source property panel, <code>false</code> not to show
     */
    public abstract void setVisibleSrcPropertyPanel(boolean isVisible);

    /**
     * Set visible target xpath panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show target xpath panel, <code>false</code> not to show
     */
    public abstract void setVisibleTargetXPathPanel(boolean isVisible);

    /**
     * Set visible target property panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show target property panel, <code>false</code> not to show
     */
    public abstract void setVisibleTargetPropertyPanel(boolean isVisible);

}