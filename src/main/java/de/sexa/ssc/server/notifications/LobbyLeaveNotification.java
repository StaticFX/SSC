package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import org.json.JSONObject;

public class LobbyLeaveNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:left";
    }

    @Override
    public void execute(JSONObject json) {
        Main.lobbyHandler.setCurrentLobby(null);
        Main.self = null;
        Main.isHudOpen = false;
    }
}
