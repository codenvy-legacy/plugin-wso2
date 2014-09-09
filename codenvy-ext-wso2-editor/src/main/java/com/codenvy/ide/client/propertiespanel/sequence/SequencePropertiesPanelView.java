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
package com.codenvy.ide.client.propertiespanel.sequence;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Sequence' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(SequencePropertiesPanelViewImpl.class)
public abstract class SequencePropertiesPanelView extends AbstractView<SequencePropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link SequencePropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of Sequence mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's changing referring type field. */
        void onReferringTypeChanged();

        /** Performs some actions in response to user's changing static reference key field. */
        void onStaticReferenceKeyChanged();

        /** Performs some actions in response to user's editing expression. */
        void onEditExpressionButtonClicked();

    }

    /** @return content of the referring type field */
    @Nonnull
    public abstract String getReferringType();

    /**
     * Changes content of the referring type field.
     *
     * @param referringTypes
     *         new content of the field
     */
    public abstract void setReferringTypes(@Nullable List<String> referringTypes);

    /**
     * Selects an item from list of referring types in the referring type field.
     *
     * @param referringType
     *         new selected type
     */
    public abstract void selectReferringType(@Nonnull String referringType);

    /** @return content of the static reference key field */
    @Nonnull
    public abstract String getStaticReferenceKey();

    /**
     * Changes content of the static reference key field.
     *
     * @param staticReferenceKey
     *         new content of the static reference key field
     */
    public abstract void setStaticReferenceKey(@Nullable String staticReferenceKey);

    /**
     * Changes content of the dynamic reference key field.
     *
     * @param dynamicReferenceKey
     *         new content of the dynamic reference key field
     */
    public abstract void setDynamicReferenceKey(@Nullable String dynamicReferenceKey);

    /**
     * Changes visible state of the dynamic reference key panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleDynamicReferenceKeyPanel(boolean visible);

    /**
     * Changes visible state of the static reference key panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleStaticReferenceKeyPanel(boolean visible);

}