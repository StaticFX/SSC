package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import org.json.JSONObject;

import java.util.UUID;

public class CanonChangedOtherNotification implements Notification {

    @Override
    public String getName() {
        return "notification:canon:changed:other";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        int canon = data.getInt("canon");
        UUID uuid = UUID.fromString(data.getString("player"));

        if (Main.lobbyHandler.getCurrentLobby() == null) return;
        Lobby lobby = Main.lobbyHandler.getCurrentLobby();

        LobbyPlayer lobbyPlayer = lobby.getPlayers().stream().filter(player -> player.getUuid().equals(uuid)).findFirst().get();
        lobbyPlayer.setCanon(canon);
    }
}
