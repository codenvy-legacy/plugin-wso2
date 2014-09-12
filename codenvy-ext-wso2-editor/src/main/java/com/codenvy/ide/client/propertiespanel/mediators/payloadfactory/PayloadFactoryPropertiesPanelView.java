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
package com.codenvy.ide.client.propertiespanel.mediators.payloadfactory;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Payload factory' mediator properties panel. It provides an ability to show all available
 * properties of the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(PayloadFactoryPropertiesPanelViewImpl.class)
public abstract class PayloadFactoryPropertiesPanelView extends AbstractView<PayloadFactoryPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link PayloadFactoryPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of PayloadFactory mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed payload format field. */
        void onPayloadFormatChanged();

        /** Performs any actions appropriate in response to the user having changed media type field. */
        void onMediaTypeChanged();

        /** Performs any actions appropriate in response to the user having changed description field. */
        void onDescriptionChanged();

        /** Performs any actions appropriate in response to the user having clicked format button. */
        void showFormatConfigurationWindow();

        /** Performs any actions appropriate in response to the user having clicked format button. */
        void showArgsConfigurationWindow();

        /**
         * Performs any actions appropriate in response to the user having clicked format key button.
         *
         * @param key
         *         text into Key Format place on the view
         */
        void showKeyEditorWindow(@Nonnull String key);
    }

    /** return payload format value */
    @Nonnull
    public abstract String getPayloadFormat();

    /**
     * Select payload format in place on view.
     *
     * @param payloadFormat
     *         payload format value
     */
    public abstract void selectPayloadFormat(@Nullable String payloadFormat);

    /**
     * Set payload format value.
     *
     * @param payloadFormats
     *         values of payload format field.
     */
    public abstract void setPayloadFormats(@Nonnull List<String> payloadFormats);

    /** @return format value from special view's place */
    @Nonnull
    public abstract String getFormat();

    /**
     * Set format value to special place on view.
     *
     * @param format
     *         value of format
     */
    public abstract void setFormat(@Nonnull String format);

    /**
     * Set format key to special place on view.
     *
     * @param formatKey
     *         value of format key
     */
    public abstract void setFormatKey(@Nullable String formatKey);

    /** @return args value from special view's place */
    @Nonnull
    public abstract String getArgs();

    /**
     * Set args value to special place on view.
     *
     * @param args
     *         value of args
     */
    public abstract void setArgs(@Nonnull String args);

    /** @return media type of PayLoad mediator from special view's place */
    @Nonnull
    public abstract String getMediaType();

    /**
     * Select value of media type.
     *
     * @param mediaType
     *         selected value of media type
     */
    public abstract void selectMediaType(@Nullable String mediaType);

    /**
     * Set media type values.
     *
     * @param mediaTypes
     *         list which need to set
     */
    public abstract void setMediaTypes(@Nonnull List<String> mediaTypes);

    /** @return description from special view's place */
    @Nonnull
    public abstract String getDescription();

    /**
     * Set description to special place on view.
     *
     * @param description
     *         value of description
     */
    public abstract void setDescription(@Nullable String description);


    /**
     * Set visible format panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show format panel, <code>false</code> not to show
     */
    public abstract void setVisibleFormatPanel(boolean isVisible);

    /**
     * Set visible format key panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show format key panel, <code>false</code> not to show
     */
    public abstract void setVisibleFormatKeyPanel(boolean isVisible);

}