package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import org.json.JSONObject;

public class CanonChangedNotification implements Notification {

    @Override
    public String getName() {
        return "notification:canon:changed";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        int canon = data.getInt("canon");

        Main.canon = canon;

        if (Main.lobbyHandler.getCurrentLobby() == null) return;
        Main.self.setCanon(canon);
    }
}
