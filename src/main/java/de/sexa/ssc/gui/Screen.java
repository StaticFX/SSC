package de.sexa.ssc.gui;

import de.sexa.ssc.Main;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Screen extends CottonClientScreen {
    public Screen(GuiDescription description) {
        super(Text.literal("Wähle deine TNT Kanone (ich bin schwer depressiv)"), description);
        description.setTitleColor(0xff0000);
    }

    @Override
    public void close() {
        Main.isMenuPressed = false;
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int ch, int keyCode, int modifiers) {

        if (ch == KeyBindingHelper.getBoundKeyOf(Main.toggleMenuBinding).getCode()) {
            MinecraftClient.getInstance().setScreen(null);
        }

        return super.keyPressed(ch, keyCode, modifiers);
    }
}