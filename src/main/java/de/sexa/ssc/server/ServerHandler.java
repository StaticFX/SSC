package de.sexa.ssc.server;

import com.google.gson.JsonObject;
import de.sexa.ssc.Main;
import de.sexa.ssc.server.util.MessageType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.java_websocket.client.WebSocketClient;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerHandler {

    private boolean connected = false;
    private SimpleWebSocketClient webSocketClient;

    public void connect(String url) {
        MinecraftClient.getInstance().send(() -> {
            if (connected)
                disconnect();

            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Opening connection to server...").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xff8d00))));

            try {
                webSocketClient = new SimpleWebSocketClient(new URI(url));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            webSocketClient.connect();
        });
    }

    public void handleServerInput(JSONObject json) {
        if (json.has("error")) {
            String error = json.getString("error");
            if (error.equalsIgnoreCase("none")) return;
            Style style = Style.EMPTY.withColor(Formatting.RED);
            if (MinecraftClient.getInstance().player != null)
                MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] An error from the server occurred: " + error).setStyle(style));
            return;
        }

        MessageType type = MessageType.valueOf(json.getString("type"));

        if (type == MessageType.NOTIFICATION) {
            String notification = json.getString("notification");
            Main.notificationHandler.handleNotification(notification, json);
        } else {
            String message = json.getString("message");

            Text text;

            System.out.println(type);

            if (type == MessageType.SUCCESS) {
                text = Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.GREEN));
            } else if (type == MessageType.INFO) {
                text = Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.YELLOW));
            } else {
                text = Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.RED));
            }

            MinecraftClient.getInstance().player.sendMessage(text);
        }
    }

    public void disconnect() {
        webSocketClient.close();
        connected = false;
    }

    public void sendMessage(String message) {
        MinecraftClient.getInstance().send(() -> {
            webSocketClient.send(message);
        });
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
