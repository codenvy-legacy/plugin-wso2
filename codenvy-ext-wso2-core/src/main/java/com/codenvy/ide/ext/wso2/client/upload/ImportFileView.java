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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

/**
 * The view of {@link ImportFilePresenter}.
 *
 * @author Valeriy Svydenko
 */

@ImplementedBy(ImportFileViewImpl.class)
public interface ImportFileView extends View<ImportFileView.ActionDelegate> {
    public interface ActionDelegate {
        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelClicked();

        /** Performs any actions appropriate in response to the user having pressed the Import button. */
        void onImportClicked();

        /**
         * Performs any actions appropriate in response to submit operation is completed.
         *
         * @param result
         *         result of submit operation
         */
        void onSubmitComplete(@NotNull String result);

        /** Performs any actions appropriate in response to the user having changed file name field. */
        void onFileNameChanged();

        /** Performs actions appropriate in response to the user having changed file with invalid format. */
        void onFileNameChangedWithInvalidFormat();

        /** Performs any actions appropriate in response to the user having changed url field. */
        void onUrlChanged();

        /** Performs some actions in response to a user uses url. */
        void onUseUrlChosen();

        /** Performs some actions in response to a user uses local path. */
        void onUseLocalPathChosen();
    }

    /** @return url */
    @NotNull
    String getUrl();

    /**
     * Set URL into place on view.
     *
     * @param url
     */
    void setUrl(@NotNull String url);

    /** @return <code>true</code> if use url is chosen, and <code>false</code> otherwise */
    boolean isUseUrl();

    /** @return <code>true</code> if use local path is chosen, and <code>false</code> otherwise */
    boolean isUseLocalPath();

    /**
     * Select local button.
     *
     * @param isUseLocalPath
     *         <code>true</code> to select use url, <code>false</code> not to select
     */
    void setUseLocalPath(boolean isUseLocalPath);

    /**
     * Select url button.
     *
     * @param isUseUrl
     *         <code>true</code> to select use url, <code>false</code> not to select
     */
    void setUseUrl(boolean isUseUrl);

    /** @return file name */
    @NotNull
    String getFileName();

    /**
     * Set error message
     *
     * @param message
     *         the message
     */
    void setMessage(@NotNull String message);

    /**
     * Change the enable state of the import button.
     *
     * @param enabled
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    void setEnabledImportButton(boolean enabled);

    /**
     * Change the enable state of the url field.
     *
     * @param enabled
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    void setEnterUrlFieldEnabled(boolean enabled);

    /**
     * Sets the 'action' associated with this form. This is the URL to which it will be submitted.
     *
     * @param url
     *         the form's action
     */
    void setAction(@NotNull String url);

    /** Submits the form. */
    void submit();

    /** Close dialog. */
    void close();

    /** Show dialog. */
    void showDialog();
}
