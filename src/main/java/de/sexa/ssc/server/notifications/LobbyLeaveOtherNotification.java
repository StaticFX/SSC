package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import org.json.JSONObject;

import java.util.UUID;

public class LobbyLeaveOtherNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:left:other";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        UUID leaver = UUID.fromString(data.getString("leaver"));

        Main.lobbyHandler.removePlayerFromLobby(leaver);
    }
}
