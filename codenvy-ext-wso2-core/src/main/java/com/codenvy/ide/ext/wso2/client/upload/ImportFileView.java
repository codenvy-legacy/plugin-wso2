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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;

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
