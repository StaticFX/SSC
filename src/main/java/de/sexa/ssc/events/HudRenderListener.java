package de.sexa.ssc.events;

import de.sexa.ssc.Main;
import de.sexa.ssc.server.util.ServerStatus;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class HudRenderListener implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        InGameHud inGameHud = MinecraftClient.getInstance().inGameHud;
        TextRenderer textRenderer = inGameHud.getTextRenderer();

        Text waterMarkText = Text.literal("SSC Status: ");
        int textWith = textRenderer.getWidth(waterMarkText);

        Text connectionTest = Text.literal(Main.serverStatus.getText());

        Color color = Main.serverStatus == ServerStatus.CONNECTED ? Color.GREEN : Color.RED;

        drawContext.drawText(textRenderer, waterMarkText, 0, 5, Color.WHITE.getRGB(), true);
        drawContext.drawText(textRenderer, connectionTest, textWith, 5, color.getRGB(), true);
    }
}
