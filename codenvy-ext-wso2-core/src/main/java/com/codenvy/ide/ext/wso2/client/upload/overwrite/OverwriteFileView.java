/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;

/**
 * The view of {@link com.codenvy.ide.ext.wso2.client.upload.overwrite.OverwriteFilePresenter}.
 *
 * @author Valeriy Svydenko
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
    @NotNull
    String getFileName();

    /**
     * Set file name into place on view.
     *
     * @param fileName
     */
    void setFileName(@NotNull String fileName);

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
    void setMessage(@NotNull String message);

    /** Close dialog. */
    void close();

    /** Show dialog. */
    void showDialog();
}
