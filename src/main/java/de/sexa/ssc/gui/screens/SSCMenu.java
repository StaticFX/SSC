package de.sexa.ssc.gui.screens;

import de.sexa.ssc.Main;
import de.sexa.ssc.models.lobby.Lobby;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.Color;
import java.util.List;

public class SSCMenu implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (!Main.isHudOpen) {
            return;
        }

        if (Main.lobbyHandler.getCurrentLobby() == null) {
            return;
        }

        Lobby lobby = Main.lobbyHandler.getCurrentLobby();
        List<LobbyPlayer> players = lobby.getPlayers();

        InGameHud inGameHud = MinecraftClient.getInstance().inGameHud;
        TextRenderer textRenderer = inGameHud.getTextRenderer();

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        Text title = Text.literal("Lobby: " + lobby.getId());

        int textWidth = textRenderer.getWidth(title);
        int textHeight = textRenderer.getWrappedLinesHeight(title, textWidth);

        int maxNameLength = textRenderer.getWidth("WWWWWWWWWWWWWWWW");
        int singleTextHeight = textRenderer.getWrappedLinesHeight("WWWWWWWWWWWWWWWW", maxNameLength);

        int guiHeight = 10 + (players.size() * singleTextHeight) + players.size() * 2;
        int guiWidth = 20 + maxNameLength;
        int titleHeight = 20;

        int xPos = screenWidth - guiWidth + (guiWidth / 2) - (textWidth / 2);
        int yPos = screenHeight - guiHeight - titleHeight + (titleHeight / 2) - (textHeight / 2);

        for (int i = 0; i < players.size(); i++) {
            LobbyPlayer player = players.get(i);
            String name = player.getName();

            String playerName = player.isSpectator() ? "(" + name + ")" : name;
            Color color = player.isSpectator() ? Color.gray : player.getColor();
            String text = player.getCanon() < 0 ? playerName : playerName + " >> " + player.getCanon();

            drawContext.drawText(textRenderer, Text.literal(text), screenWidth - guiWidth + 5, screenHeight - guiHeight + 2 + (i * singleTextHeight), color.getRGB(), true);
        }

        drawContext.fill(screenWidth - guiWidth, screenHeight - guiHeight, screenWidth, screenHeight, 0x802e2e2e);
        drawContext.fill(screenWidth - guiWidth, screenHeight - guiHeight, screenWidth, screenHeight - guiHeight - titleHeight, 0x801c1c1c);

        drawContext.drawBorder(screenWidth - guiWidth, screenHeight - guiHeight - titleHeight, guiWidth, guiHeight + titleHeight, Main.playerColor.getRGB());

        drawContext.drawText(textRenderer, title, xPos, yPos, Color.WHITE.getRGB(), true);
    }
}
