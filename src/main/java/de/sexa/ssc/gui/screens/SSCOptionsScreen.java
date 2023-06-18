package de.sexa.ssc.gui.screens;

import de.sexa.ssc.Main;
import de.sexa.ssc.gui.NumberFieldWidget;
import de.sexa.ssc.server.util.ActionBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.awt.*;

public class SSCOptionsScreen extends Screen {

    private final Screen parent;
    private TextFieldWidget urlBox;
    private TextFieldWidget rBox;
    private TextFieldWidget bBox;
    private TextFieldWidget gBox;

    private Color color = Main.playerColor;

    public SSCOptionsScreen(Screen parent) {
        super(Text.literal("SSC Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.urlBox = new TextFieldWidget(this.textRenderer, this.width / 2 - 50, 40, 100, 20, this.urlBox, Text.literal("Server URL"));
        this.addDrawableChild(urlBox);

        int boxWidth = 40;

        this.rBox = new NumberFieldWidget(this.textRenderer, this.width / 2 - boxWidth / 2, 100, boxWidth, 20, this.rBox, Text.literal("R"));
        this.bBox = new NumberFieldWidget(this.textRenderer, this.width / 2 - boxWidth / 2, 125, boxWidth, 20, this.gBox, Text.literal("G"));
        this.gBox = new NumberFieldWidget(this.textRenderer, this.width / 2 - boxWidth / 2, 150, boxWidth, 20, this.bBox, Text.literal("B"));

        rBox.setText("0");
        bBox.setText("0");
        gBox.setText("0");

        this.rBox.setChangedListener(to -> {

            if (!isValidString(to)) return;

            int r = Integer.parseInt(to);
            int g = Main.playerColor.getGreen();
            int b = Main.playerColor.getBlue();

            color = new Color(r,g,b);
        });

        this.gBox.setChangedListener(to -> {
            if (!isValidString(to)) return;

            int r = Main.playerColor.getRed();
            int g = Integer.parseInt(to);
            int b = Main.playerColor.getBlue();

            color = new Color(r,g,b);
        });

        this.bBox.setChangedListener(to -> {
            if (!isValidString(to)) return;

            int r = Main.playerColor.getRed();
            int g = Main.playerColor.getGreen();
            int b = Integer.parseInt(to);

            color = new Color(r,g,b);
        });

        this.addDrawableChild(rBox);
        this.addDrawableChild(gBox);
        this.addDrawableChild(bBox);

        urlBox.setText(Main.config.getString("serverURL"));

        rBox.setText(String.valueOf(Main.config.getInt("r")));
        gBox.setText(String.valueOf(Main.config.getInt("g")));
        bBox.setText(String.valueOf(Main.config.getInt("b")));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Connect"), button -> {
            Main.config.setAndSave("serverURL", urlBox.getText());
            Main.serverHandler.connect(urlBox.getText());
        }).dimensions(this.width / 2 - 36, 75, 72, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.client.setScreen(this.parent)).dimensions(this.width / 2 - 36, this.height - 28, 72, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Save"
        ), button -> {
                Main.playerColor = color;
                int r = Main.playerColor.getRed();
                int g = Main.playerColor.getGreen();
                int b = Main.playerColor.getBlue();

                Main.config.setAndSave("r", r);
                Main.config.setAndSave("g", g);
                Main.config.setAndSave("b", b);

                ActionBuilder ab = new ActionBuilder("action:player:color:change");
                ab.addParameter("r", String.valueOf(r));
                ab.addParameter("g", String.valueOf(g));
                ab.addParameter("b", String.valueOf(b));

                Main.serverHandler.sendMessage(ab.build().toString());
            }).dimensions(this.width / 2 - 36, 185, 72, 20).build());


        this.addDrawableChild(ButtonWidget.builder(Text.literal("Players"), button -> {

        }).dimensions(this.width / 2 - 36, 200, 72, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Lobbies"), button -> {

        }).dimensions(this.width / 2 - 36, 225, 72, 20).build());

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        Text urlText = Text.literal("Server URL: ");
        int urlWidth = textRenderer.getWidth(urlText);
        int urlHeight = textRenderer.getWrappedLinesHeight(urlText, urlWidth);

        context.drawCenteredTextWithShadow(textRenderer, urlText, width / 2 - 55 - urlWidth / 2, 40 + urlHeight, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "Status: " + Main.serverStatus.getText(), width / 2, 54 + urlHeight, 0xFFFFFF);

        Text rText = Text.literal("R: ");
        int rTextWidth = textRenderer.getWidth(rText);
        int rTextHeight = textRenderer.getWrappedLinesHeight(rText, rTextWidth);

        context.drawCenteredTextWithShadow(textRenderer, rText, width / 2 - 25 - rTextWidth / 2, 100 + rTextHeight, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("G: "), width / 2 - 25 - rTextWidth / 2, 125 + rTextHeight, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("B: "), width / 2 - 25 - rTextWidth / 2, 150 + rTextHeight, 0xFFFFFF);

        context.fill(this.width / 2 + 30, 100, this.width / 2 + 70, 170, color.getRGB());

        super.render(context, mouseX, mouseY, delta);
    }

    private boolean isValidString(String str) {
        if (str == null) return false;
        if (str.isEmpty()) return false;
        if (!isDigitString(str)) return false;


        int number;
        try {
            number = Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }

        return number <= 255;
    }
    private boolean isDigitString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}