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
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The view of {@link com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter}.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@ImplementedBy(OverwriteFileViewImpl.class)
public interface OverwriteFileView extends View<OverwriteFileView.ActionDelegate> {
    public interface ActionDelegate {
        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Rename button. */
        void onRenameButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Overwrite button. */
        void onOverwriteButtonClicked();

        /** Performs any actions appropriate in response to the user having changed file name field. */
        void onNameChanged();

    }

    /** @return fileName */
    @Nonnull
    String getFileName();

    /**
     * Set file name into place on view.
     *
     * @param fileName
     */
    void setFileName(@Nonnull String fileName);

    /**
     * Change the enable state of the rename button.
     *
     * @param enabled
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    void setEnabledRenameButton(boolean enabled);

    /**
     * Set message
     *
     * @param message
     *         the message
     */
    void setMessage(@Nonnull String message);

    /** Close dialog. */
    void close();

    /** Show dialog. */
    void showDialog();
}
