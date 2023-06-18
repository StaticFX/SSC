package de.sexa.ssc.server;

import de.sexa.ssc.server.notifications.Notification;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationHandler {

    private final ArrayList<Notification> notifications = new ArrayList<>();

    public void registerNotification(Notification notification) {
        notifications.add(notification);
    }

    public void handleNotification(String notification, JSONObject json) {
        for (Notification not : notifications) {
            if (not.getName().equals(notification)) {
                not.execute(json);
                System.out.println("Received: " + json.toString());
            }
        }
    }
}
