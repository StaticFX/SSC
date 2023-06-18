package de.sexa.ssc.server;

import de.sexa.ssc.Main;
import de.sexa.ssc.server.util.ServerStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;

public class SimpleWebSocketClient extends WebSocketClient {

    public SimpleWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Connected with server").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x51ff00))));

        Main.serverHandler.setConnected(true);
        Main.serverStatus = ServerStatus.CONNECTED;

        Color color = Main.playerColor;
        JSONObject json = new JSONObject();
        json.put("UUID", MinecraftClient.getInstance().getSession().getUuid());

        JSONObject parameters = new JSONObject();

        parameters.put("name", MinecraftClient.getInstance().getSession().getUsername());
        parameters.put("r", color.getRed());
        parameters.put("g", color.getGreen());
        parameters.put("b", color.getBlue());

        json.put("parameters", parameters);

        send(json.toString());
    }

    @Override
    public void onMessage(String message) {

        JSONObject json;

        try {
            json = new JSONObject(message);
        }catch (Exception e) {
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Error from server occurred: " + e.getMessage()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x820000))));
            return;
        }
        Main.serverHandler.handleServerInput(json);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Closing connection to server... Reason: " + reason).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x820000))));
        Main.serverHandler.setConnected(false);
        Main.lobbyHandler.setCurrentLobby(null);
        Main.self = null;
        Main.isHudOpen = false;
        if (Main.isMenuPressed) {
            Main.canonInventory.close();
        }
        Main.serverStatus = ServerStatus.DISCONNECTED;
    }

    @Override
    public void onError(Exception ex) {
        Main.serverStatus = ServerStatus.DISCONNECTED;
        ex.printStackTrace();
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Error from server occurred: " + ex.getMessage()).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x820000))));
    }
}
