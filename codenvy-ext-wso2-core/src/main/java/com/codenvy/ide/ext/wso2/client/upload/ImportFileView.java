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
package com.codenvy.ide.ext.wso2.client.upload;

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
@ImplementedBy(ImportFileViewImpl.class)
public interface ImportFileView extends View<ImportFileView.ActionDelegate> {

    /**
     * Interface defines methods of ImportFile's presenter which calls from view. These methods defines some actions when a user
     * clicks a button, changes file name or url.
     */
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
        void onSubmitComplete(@Nonnull String result);

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

    /** @return url from special view's place */
    @Nonnull
    String getUrl();

    /**
     * Set URL into place on view.
     *
     * @param url
     *         value of url which need to set
     */
    void setUrl(@Nonnull String url);

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

    /** @return file name from special view's place */
    @Nonnull
    String getFileName();

    /**
     * Set error message to special place on view
     *
     * @param message
     *         the message which need to set
     */
    void setMessage(@Nonnull String message);

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
    void setAction(@Nonnull String url);

    /** Submits the form. */
    void submit();

    /** Close dialog. */
    void close();

    /** Show dialog. */
    void showDialog();
}
