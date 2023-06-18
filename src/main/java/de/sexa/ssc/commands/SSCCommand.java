package de.sexa.ssc.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.sexa.ssc.Main;
import de.sexa.ssc.io.Hash;
import de.sexa.ssc.server.util.ActionBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.lang.reflect.Field;

public class SSCCommand {
    public static int executeJoin(CommandContext<FabricClientCommandSource> context) {
        String idRaw = StringArgumentType.getString(context, "lobby");
        String passwordRaw = StringArgumentType.getString(context, "password");
        boolean spectator = BoolArgumentType.getBool(context, "spectator");

        int id;

        try {
            id = Integer.parseInt(idRaw);
        }catch (Exception e) {
            context.getSource().sendFeedback(Text.literal("[SSC] Invalid ID").setStyle(Style.EMPTY.withColor(Formatting.RED)));
            return 0;
        }

        String hash = Hash.sha256(passwordRaw);

        ActionBuilder ab = new ActionBuilder("action:lobby:join");
        ab.addParameter("id", String.valueOf(id));
        ab.addParameter("spectator", String.valueOf(spectator));
        ab.addParameter("hash", hash);

        Main.serverHandler.sendMessage(ab.build().toString());
        return 1;
    }

    public static int executeLeave(CommandContext<FabricClientCommandSource> context) {
        ActionBuilder ab = new ActionBuilder("action:lobby:leave");

        Main.serverHandler.sendMessage(ab.build().toString());
        return 1;
    }

    public static int executeColor(CommandContext<FabricClientCommandSource> context) {
        String color = StringArgumentType.getString(context, "color").trim();
        Color colorObj;

        try {
            Field field = Class.forName("java.awt.Color").getField(color);
            colorObj = (Color) field.get(null);
        } catch (Exception e) {
            context.getSource().sendFeedback(Text.literal("[SSC] Invalid Color").setStyle(Style.EMPTY.withColor(Formatting.RED)));
            return 0;
        }

        ActionBuilder ab = new ActionBuilder("action:player:color:change");
        ab.addParameter("r", String.valueOf(colorObj.getRed()));
        ab.addParameter("g", String.valueOf(colorObj.getGreen()));
        ab.addParameter("b", String.valueOf(colorObj.getBlue()));

        Main.serverHandler.sendMessage(ab.build().toString());

        return 1;
    }

    public static int executeCreate(CommandContext<FabricClientCommandSource> context) {
        String wg = StringArgumentType.getString(context, "wg");
        String passwordRaw = StringArgumentType.getString(context, "password");

        String hash = Hash.sha256(passwordRaw);

        ActionBuilder ab = new ActionBuilder("action:lobby:create");
        ab.addParameter("wg", wg);
        ab.addParameter("hash", hash);

        Main.serverHandler.sendMessage(ab.build().toString());
        return 1;
    }
}
