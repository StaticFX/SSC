package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import org.json.JSONObject;

import java.awt.*;
import java.util.UUID;

public class ColorChangedOtherNotification implements Notification {

    @Override
    public String getName() {
        return "notification:color:other:changed";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        int r = data.getInt("r");
        int g = data.getInt("g");
        int b = data.getInt("b");

        UUID uuid = UUID.fromString(data.getString("player"));
        Lobby lobby = Main.lobbyHandler.getCurrentLobby();

        if (lobby == null) return;

        LobbyPlayer lobbyPlayer = lobby.getPlayers().stream().filter(player -> player.getUuid().equals(uuid)).findFirst().get();
        lobbyPlayer.setColor(new Color(r,g,b));
    }
}