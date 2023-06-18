package de.sexa.ssc.server.notifications;

import org.json.JSONObject;

public interface Notification {

    public String getName();
    public void execute(JSONObject json);

}
