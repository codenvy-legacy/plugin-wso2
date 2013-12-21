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
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * The action for importing configuration files.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class ImportSynapseAction extends Action {

    private AsyncProvider<ImportFilePresenter> importFilePresenter;
    private NotificationManager                notificationManager;

    @Inject
    public ImportSynapseAction(LocalizationConstant local,
                               AsyncProvider<ImportFilePresenter> importFilePresenter,
                               NotificationManager notificationManager) {

        super(local.wso2ImportSynapseConfig(), local.wso2ImportActionDescription(), null);

        this.importFilePresenter = importFilePresenter;
        this.notificationManager = notificationManager;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        importFilePresenter.get(new AsyncCallback<ImportFilePresenter>() {
            @Override
            public void onSuccess(ImportFilePresenter presenter) {
                presenter.showDialog();
            }

            @Override
            public void onFailure(Throwable caught) {
                Notification notification = new Notification(caught.getMessage(), ERROR);
                notificationManager.showNotification(notification);
            }
        });
    }
}