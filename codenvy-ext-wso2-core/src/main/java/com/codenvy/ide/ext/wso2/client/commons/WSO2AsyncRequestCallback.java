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
 * @author Andrey Plotnikov
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
        notificationManager.showNotification(new Notification(exception.getMessage(), ERROR));
    }
}
