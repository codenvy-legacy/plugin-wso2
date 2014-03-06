/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
 * All Rights Reserved.
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
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import static com.codenvy.ide.api.notification.Notification.Type.ERROR;

import org.eclipse.emf.ecore.EObject;
import org.genmymodel.gmmf.common.RuntimeValidationNotifier;

import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.google.inject.Inject;

import static org.genmymodel.gmmf.common.RuntimeValidationNotifier.Level.INFO;
import static org.genmymodel.gmmf.common.RuntimeValidationNotifier.Level.WARNING;
import static org.genmymodel.gmmf.common.RuntimeValidationNotifier.Level.SEVERE;


/**
 * Wrapper for validity constraint notifications
 *
 * @author Thomas Legrand
 */
@SuppressWarnings("unused")
public class ValidationNotifierImpl implements RuntimeValidationNotifier {

    private NotificationManager ideNotificationManager;

    @Inject
    public ValidationNotifierImpl(NotificationManager ideNotificationManager) {
        this.ideNotificationManager = ideNotificationManager;
    }

    @Override
    public void notify(Level level, EObject eobject, String message) {

        switch (level) {

            case SEVERE:

                // Propagate the notification to the IDE manager
                Notification notification = new Notification(message, ERROR);
                ideNotificationManager.showNotification(notification);

                break;

            default:
                break;
        }
    }

    @Override
    public void notify(EObject eobject, String message) {

        notify(Level.INFO, eobject, message);
    }

}
