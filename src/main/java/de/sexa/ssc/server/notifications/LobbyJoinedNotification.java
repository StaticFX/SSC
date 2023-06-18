package de.sexa.ssc.server.notifications;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import de.sexa.ssc.models.lobby.LobbyPlayerSerialized;
import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LobbyJoinedNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:join";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        int id = data.getInt("id");
        String wg = data.getString("wg");
        Gson gson = new Gson();
        List<LobbyPlayerSerialized> players = gson.fromJson(data.getJSONArray("players").toString(), new TypeToken<List<LobbyPlayerSerialized>>(){}.getType());

        System.out.println(data.getJSONArray("players"));

        ArrayList<LobbyPlayer> playersList = players.stream().map((lP -> new LobbyPlayer(lP.getUuid(), lP.getName(), new Color(lP.getR(), lP.getG(), lP.getB()), lP.isSpectator(), -1))).collect(Collectors.toCollection(ArrayList::new));
        LobbyPlayer self = playersList.stream().filter(player -> player.getUuid().equals(MinecraftClient.getInstance().player.getUuid())).findFirst().get();
        Main.self = self;

        Lobby lobby = new Lobby(id, playersList, wg);
        Main.lobbyHandler.setCurrentLobby(lobby);
    }
}
