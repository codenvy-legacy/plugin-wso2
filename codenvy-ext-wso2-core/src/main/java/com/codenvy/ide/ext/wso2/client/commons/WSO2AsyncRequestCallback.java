/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2014] Codenvy, S.A. 
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
package com.codenvy.ide.ext.wso2.client.commons;

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.Unmarshallable;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

/**
 * Class to receive a response from a remote procedure call.
 *
 * @author Valeriy Svydenko
 */
public abstract class WSO2AsyncRequestCallback<T> extends AsyncRequestCallback<T> {
    private NotificationManager notificationManager;

    public WSO2AsyncRequestCallback(Unmarshallable<T> unmarshaller, NotificationManager notificationManager) {
        super(unmarshaller);
        this.notificationManager = notificationManager;
    }

    public WSO2AsyncRequestCallback(NotificationManager notificationManager) {
        this(null, notificationManager);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFailure(Throwable exception) {
        Notification notification = new Notification(exception.getMessage(), ERROR);
        notificationManager.showNotification(notification);
    }
}
