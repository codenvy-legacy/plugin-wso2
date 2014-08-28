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
package com.codenvy.ide.client.propertiespanel.resourcekeyeditor;

import com.codenvy.ide.client.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The presentation of Resource Key view. It contains a general places for key message.
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(ResourceKeyEditorViewImpl.class)
public interface ResourceKeyEditorView extends View<ResourceKeyEditorView.ActionDelegate> {
    /** Needs for delegate some function into Resource Key Editor view. */
    public interface ActionDelegate {
        /**
         * Performs any actions appropriate in response to the user having pressed the Ok button.
         *
         * @param key
         *         text into the format field on the view.
         */
        void onOkClicked(@Nonnull String key);

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelClicked();
    }

    /**
     * Show dialog.
     *
     * @param content
     *         format content.
     */
    void showDialog(@Nonnull String content);

    /** Close dialog. */
    void closeDialog();
}
