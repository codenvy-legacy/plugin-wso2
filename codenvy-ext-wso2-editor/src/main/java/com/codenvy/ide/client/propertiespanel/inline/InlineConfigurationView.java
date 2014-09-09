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
package com.codenvy.ide.client.propertiespanel.inline;

import com.codenvy.ide.client.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The presentation of Inline Configuration view. It contains a general places for format message.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(InlineConfigurationViewImpl.class)
public interface InlineConfigurationView extends View<InlineConfigurationView.ActionDelegate> {

    /**
     * Interface defines methods of {@link InlineConfigurationPresenter} which calls from view. These methods defines
     * some actions when user changes xml content which is a property of mediator.
     */
    public interface ActionDelegate {
        /**
         * Performs any actions appropriate in response to the user having pressed the Ok button.
         *
         * @param format
         *         text into the format field on the view.
         */
        void onOkClicked(@Nonnull String format);

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelClicked();

        /** Performs any actions appropriate in response to the user having changed content. */
        void onValueChanged();
    }

    /** Close dialog window. */
    void closeDialog();

    /** Sets enable of button. */
    void setEnableBtnOk();

    /**
     * Show dialog window for editing inline property of element.
     *
     * @param content
     *         format content.
     */
    void showDialog(@Nonnull String content);

    /**
     * Sets title of window.
     *
     * @param title
     *         value of title which need to set to window
     */
    void setWindowTitle(@Nonnull String title);
}
