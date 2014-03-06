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
 * @author Thomas Legrand
 *
 */
@SuppressWarnings("unused")
public class ValiditionNotifierImpl implements RuntimeValidationNotifier {

	private NotificationManager ideNotificationManager;
	
	@Inject
	public ValiditionNotifierImpl (NotificationManager ideNotificationManager)
	{
		this.ideNotificationManager = ideNotificationManager;
	}
	
	@Override
	public void notify(Level level, EObject eobject,String message) {
		
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
