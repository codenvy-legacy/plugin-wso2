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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The view of {@link PayloadFactoryPropertiesPanelPresenter}
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@ImplementedBy(PayloadFactoryPropertiesPanelViewImpl.class)
public abstract class PayloadFactoryPropertiesPanelView extends AbstractView<PayloadFactoryPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed payload format field. */
        void onPayloadFormatChanged();

        /** Performs any actions appropriate in response to the user having changed format field. */
        void onFormatChanged();

        /** Performs any actions appropriate in response to the user having changed media type field. */
        void onMediaTypeChanged();

        /** Performs any actions appropriate in response to the user having changed description field. */
        void onDescriptionChanged();

        /**
         * Performs any actions appropriate in response to the user having clicked format button.
         *
         * @param content
         *         text into Format place on the view
         */
        void showFormatConfigurationWindow(@NotNull String content, @NotNull String tile);

        /** Performs any actions appropriate in response to the user having clicked format button. */
        void showArgsConfigurationWindow();

        /**
         * Performs any actions appropriate in response to the user having clicked format key button.
         *
         * @param key
         *         text into Key Format place on the view
         */
        void showKeyEditorWindow(@NotNull String key);
    }

    /** return payload format value */
    @NotNull
    public abstract String getPayloadFormat();

    /**
     * Select payload format in place on view.
     *
     * @param payloadFormat
     *         payload format value
     */
    public abstract void selectPayloadFormat(@NotNull String payloadFormat);

    /**
     * Set payload format value.
     *
     * @param payloadFormat
     *         values of payload format field.
     */
    public abstract void setPayloadFormat(@NotNull List<String> payloadFormat);

    /** @return format value */
    @NotNull
    public abstract String getFormat();

    /**
     * Set format value.
     *
     * @param format
     *         value of format
     */
    public abstract void setFormat(@NotNull String format);

    /** @return format key */
    @NotNull
    public abstract String getFormatKey();

    /**
     * Set format key.
     *
     * @param formatKey
     *         value of format key
     */
    public abstract void setFormatKey(@NotNull String formatKey);

    /** @return args value */
    @NotNull
    public abstract String getArgs();

    /**
     * Set args value.
     *
     * @param args
     */
    public abstract void setArgs(@NotNull String args);

    /** @return media type of PayLoad mediator. */
    @NotNull
    public abstract String getMediaType();

    /**
     * Select value of media type.
     *
     * @param mediaType
     *         selected value of media type
     */
    public abstract void selectMediaType(@NotNull String mediaType);

    /**
     * Set media type values.
     *
     * @param mediaType
     */
    public abstract void setMediaType(@NotNull List<String> mediaType);

    /** @return description */
    @NotNull
    public abstract String getDescription();

    /**
     * Set description.
     *
     * @param description
     */
    public abstract void setDescription(@NotNull String description);


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