package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.json.JSONObject;

public class LobbyCreatedOtherNotification implements Notification {

    @Override
    public String getName() {
        return "notification:lobby:created:join";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        String wg = data.getString("wg");
        int id = data.getInt("id");

        if (Main.lobbyHandler.getCurrentLobby() == null) {
            Text text = Text.literal("[SSC] Lobby " + id + " has been created for " + wg + "!")
                    .setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

            MinecraftClient.getInstance().player.sendMessage(text);
        }
    }
}
