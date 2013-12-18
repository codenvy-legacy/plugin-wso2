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
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.parts.ConsolePart;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * The presenter for import configuration files.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class ImportFilePresenter implements ImportFileView.ActionDelegate {
    private ImportFileView      view;
    private ConsolePart         console;
    private NotificationManager notificationManager;

    /**
     * Create presenter.
     *
     * @param view
     * @param notificationManager
     * @param console
     */
    @Inject
    public ImportFilePresenter(ImportFileView view,
                               ConsolePart console,
                               NotificationManager notificationManager) {
        this.view = view;
        this.view.setDelegate(this);
        this.console = console;
        this.notificationManager = notificationManager;
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelClicked() {
        view.close();
    }

    /** {@inheritDoc} */
    @Override
    public void onImportClicked() {
        String file;
        file = view.isUseUrl() ? view.getTextUrl() : view.getFileName();
        //TODO import file
    }

    /** {@inheritDoc} */
    @Override
    public void onSubmitComplete(@NotNull String result) {
        if (result.isEmpty()) {
            ImportFilePresenter.this.view.close();
        } else {
            if (result.startsWith("<pre>") && result.endsWith("</pre>")) {
                result.substring(5, (result.length() - 6));
            }
            console.print(result);
            Notification notification = new Notification(result, ERROR);
            notificationManager.showNotification(notification);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onFileNameChanged() {
        String fileName = view.getFileName();
        view.setEnabledImportButton(!fileName.isEmpty() && view.isUseLocalPath());
    }

    /** {@inheritDoc} */
    @Override
    public void onUrlChanged() {
        view.setEnabledImportButton(!view.getUrl().isEmpty() && view.isUseUrl());
    }

    /** {@inheritDoc} */
    @Override
    public void onUseUrlChosen() {
        view.setEnterUrlFieldEnabled(true);
    }

    /** {@inheritDoc} */
    @Override
    public void onUseLocalPathChosen() {
        view.setEnterUrlFieldEnabled(false);
    }

    /** Show dialog. */
    public void showDialog() {
        view.setUseLocalPath(true);
        view.setEnabledImportButton(false);
        view.setEnterUrlFieldEnabled(false);
        view.showDialog();
    }
}
