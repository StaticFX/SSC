package de.sexa.ssc.gui;

import de.sexa.ssc.Main;
import de.sexa.ssc.server.util.ActionBuilder;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.json.JSONObject;

import java.util.ArrayList;

public class CanonMenu extends LightweightGuiDescription {
    public CanonMenu() {
        WGridPanel root = new WGridPanel(5);
        root.setGaps(1, 1);

        int buttonsAmount = 16;

        setRootPanel(root);
        root.setSize(buttonsAmount * 10 + buttonsAmount, 50);
        root.setInsets(Insets.ROOT_PANEL);
        root.layout();

        ArrayList<WButton> buttons = new ArrayList<>();

        for (int i = 0; i <= buttonsAmount - 1; i++) {
            WButton button = new WButton(Text.literal(String.valueOf(i + 1)));

            button.setOnClick(() -> {
                ActionBuilder ab = new ActionBuilder("action:player:canon:changed");
                ab.addParameter("canon", button.getLabel().getString());

                button.setLabel(Text.literal(button.getLabel().getString()).setStyle(Style.EMPTY.withColor(Main.playerColor.getRGB())));
                buttons.forEach(wButton -> wButton.setLabel(Text.literal(wButton.getLabel().getString())));
                Main.serverHandler.sendMessage(ab.build().toString());
            });

            int buttonSize = 5;
            buttons.add(button);
            root.add(button, i * buttonSize + 1, 3, buttonSize, buttonSize);
        }

        root.layout();
        root.validate(this);
    }
}