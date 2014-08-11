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
package com.codenvy.ide.client.propertiespanel.send;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(SendPropertiesPanelViewImpl.class)
public abstract class SendPropertiesPanelView extends AbstractView<SendPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed skip serialization parameter. */
        void onSkipSerializationChanged();

        /** Performs any actions appropriate in response to the user having changed receiving sequence type. */
        void onReceivingSequencerTypeChanged();

        /** Performs any actions appropriate in response to the user having changed build message before sending parameter. */
        void onBuildMessageBeforeSendingChanged();

        /** Performs any actions appropriate in response to the user having changed description. */
        void onDescriptionChanged();

    }

    /** @return skip serialization value from the special place on the view which uses for showing skip serialization parameter */
    @Nonnull
    public abstract String getSkipSerialization();

    /**
     * Select skip serialization value in place on view.
     *
     * @param skipSerialization
     *         skip serialization value
     */
    public abstract void selectSkipSerialization(@Nullable String skipSerialization);

    /**
     * Sets skip serialization value to the special place on the view which uses for showing skip serialization parameter.
     *
     * @param skipSerialization
     *         list types of skip serialization which need to set to special list box
     */
    public abstract void setSkipSerialization(@Nullable List<String> skipSerialization);

    /** @return receiving sequence type value from the special place on the view which uses for showing receiving sequence type parameter */
    @Nonnull
    public abstract String getReceivingSequencerType();

    /**
     * Select receiving sequence type in place on view.
     *
     * @param receivingSequencerType
     *         receiving sequence type
     */
    public abstract void selectReceivingSequencerType(@Nullable String receivingSequencerType);

    /**
     * Sets receiving sequence type to the special place on the view which uses for showing receiving sequence type parameter.
     *
     * @param receivingSequencerType
     *         list receiving sequence types which need to set to special list box
     */
    public abstract void setReceivingSequencerType(@Nullable List<String> receivingSequencerType);

    /**
     * @return build message before sending parameter from the special place on the view which uses for showing build message before
     * sending parameter
     */
    @Nonnull
    public abstract String getBuildMessageBeforeSending();

    /**
     * Select build message before sending parameter in place on view.
     *
     * @param buildMessageBeforeSending
     *         build message before sending value
     */
    public abstract void selectBuildMessageBeforeSending(@Nullable String buildMessageBeforeSending);

    /**
     * Sets build message before sending type to the special place on the view which uses for showing build message before sending type
     * parameter.
     *
     * @param buildMessageBeforeSending
     *         list build message before sending types which need to set to special list box
     */
    public abstract void setBuildMessageBeforeSending(@Nullable List<String> buildMessageBeforeSending);

    /** @return description value from the special place on the view which uses for showing description parameter */
    @Nonnull
    public abstract String getDescription();

    /**
     * Sets description to the special place on the view which uses for showing description parameter.
     *
     * @param description
     *         description value
     */
    public abstract void setDescription(@Nullable String description);

}