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
 * The abstract view's representation which describes methods to receive information from the user and to set it to the file. It
 * provides an ability to show all available tools for working with the file.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
@ImplementedBy(OverwriteFileViewImpl.class)
public interface OverwriteFileView extends View<OverwriteFileView.ActionDelegate> {

    /**
     * Interface defines methods of OverwriteFile's presenter which calls from view. These methods defines some actions when a user
     * click the button and when a user change the name of file.
     */
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

    /** @return value of fileName from special view's place */
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
     * Sets value of message to special place on view
     *
     * @param message
     *         the message which need to set
     */
    void setMessage(@Nonnull String message);

    /** Close dialog. */
    void close();

    /** Show dialog. */
    void showDialog();
}
