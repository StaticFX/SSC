package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import de.sexa.ssc.models.lobby.LobbyHandler;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class LobbyCreatedNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:created";
    }


    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        String wg = data.getString("wg");
        int id = data.getInt("id");

        ArrayList<LobbyPlayer> players = new ArrayList<>();

        UUID uuid = MinecraftClient.getInstance().player.getUuid();
        String name = MinecraftClient.getInstance().player.getName().getString();
        LobbyPlayer player = new LobbyPlayer(uuid, name, Main.playerColor, false, -1);

        Main.self = player;
        players.add(player);


        Lobby lobby = new Lobby(id, players, wg);
        Main.lobbyHandler.setCurrentLobby(lobby);
    }
}
