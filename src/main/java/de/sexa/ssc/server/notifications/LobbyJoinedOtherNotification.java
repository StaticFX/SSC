package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.json.JSONObject;

import java.awt.*;
import java.util.UUID;

public class LobbyJoinedOtherNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:join:other";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        String name = data.getString("name");
        int r = data.getInt("r");
        int g = data.getInt("g");
        int b = data.getInt("b");
        UUID player = UUID.fromString(data.getString("player"));
        boolean spectator = data.getBoolean("spectator");

        LobbyPlayer lobbyPlayer = new LobbyPlayer(player, name, new Color(r,g,b), spectator, -1);
        Main.lobbyHandler.getCurrentLobby().addPlayer(lobbyPlayer);
        MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] " + name + " has joined your lobby.").setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));
    }
}
