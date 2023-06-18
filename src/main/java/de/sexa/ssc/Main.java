package de.sexa.ssc;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.sexa.ssc.commands.SSCCommand;
import de.sexa.ssc.events.HudRenderListener;
import de.sexa.ssc.gui.CanonMenu;
import de.sexa.ssc.gui.screens.SSCMenu;
import de.sexa.ssc.gui.Screen;
import de.sexa.ssc.io.Hash;
import de.sexa.ssc.io.SSCConfig;
import de.sexa.ssc.models.lobby.LobbyPlayer;
import de.sexa.ssc.server.NotificationHandler;
import de.sexa.ssc.server.ServerHandler;
import de.sexa.ssc.models.lobby.LobbyHandler;
import de.sexa.ssc.server.notifications.*;
import de.sexa.ssc.server.util.ServerStatus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;

public class Main implements ClientModInitializer {

    public static KeyBinding toggleMenuBinding;
    private KeyBinding toggleHUDBinding;
    public static boolean isHudOpen = true;
    public static boolean isMenuPressed;
    public static Color playerColor = new Color(0, 0,0);
    private static LiteralArgumentBuilder<FabricClientCommandSource> sscCommand;
    public static ServerHandler serverHandler = new ServerHandler();

    public static LobbyHandler lobbyHandler = new LobbyHandler();
    public static NotificationHandler notificationHandler = new NotificationHandler();
    public static Screen canonInventory;
    public static LobbyPlayer self;
    public static int canon = -1;
    public static ServerStatus serverStatus = ServerStatus.DISCONNECTED;
    public static SSCConfig config;
    public static String lobbyHash;

    @Override
    public void onInitializeClient() {
        File location = new File(FabricLoader.getInstance().getModContainer("ssc").orElseThrow().findPath("assets").get().toFile(), "config.json");

        System.out.println(location.getAbsolutePath());

        config = new SSCConfig(location);
        config.init("config.json");

        HudRenderCallback.EVENT.register(new HudRenderListener());

        notificationHandler.registerNotification(new LobbyCreatedNotification());
        notificationHandler.registerNotification(new LobbyCreatedOtherNotification());
        notificationHandler.registerNotification(new LobbyLeaveNotification());
        notificationHandler.registerNotification(new LobbyLeaveOtherNotification());
        notificationHandler.registerNotification(new LobbyJoinedNotification());
        notificationHandler.registerNotification(new LobbyJoinedOtherNotification());
        notificationHandler.registerNotification(new CanonChangedNotification());
        notificationHandler.registerNotification(new CanonChangedOtherNotification());
        notificationHandler.registerNotification(new ColorChangedNotification());
        notificationHandler.registerNotification(new ColorChangedOtherNotification());

        HudRenderCallback.EVENT.register(new SSCMenu());

        int r = config.getInt("r");
        int g = config.getInt("g");
        int b = config.getInt("b");

        playerColor = new Color(r,g,b);

        serverHandler.connect(config.getString("serverURL"));

        toggleMenuBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Toggle TNT Menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "SSC"
        ));

        toggleHUDBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Toggle HUD",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "SSC"
        ));

        sscCommand = ClientCommandManager.literal("ssc")
                .then(ClientCommandManager.literal("join").then(ClientCommandManager.argument("lobby", StringArgumentType.word()).then(ClientCommandManager.argument("password", StringArgumentType.string()).then(ClientCommandManager.argument("spectator", BoolArgumentType.bool()).executes(SSCCommand::executeJoin)))))
                .then(ClientCommandManager.literal("create").then(ClientCommandManager.argument("wg", StringArgumentType.string()).then(ClientCommandManager.argument("password", StringArgumentType.string()).executes(SSCCommand::executeCreate))))
                .then(ClientCommandManager.literal("color").then(ClientCommandManager.argument("color", StringArgumentType.word()).executes(SSCCommand::executeColor)))
                .then(ClientCommandManager.literal("leave").executes(SSCCommand::executeLeave));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(sscCommand);
        });


        Screen screen = new Screen(new CanonMenu());
        canonInventory = screen;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleMenuBinding.wasPressed()) {
                if (!serverHandler.isConnected()) {
                    if (MinecraftClient.getInstance().currentScreen == null) {
                        MinecraftClient.getInstance().player.sendMessage(Text.literal("[SSC] Must be connected to a server").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x820000))));
                    }
                    return;
                }

                isMenuPressed = !isMenuPressed;

                if (isMenuPressed)
                    MinecraftClient.getInstance().setScreen(screen);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleHUDBinding.wasPressed()) {
                isHudOpen = !isHudOpen;
            }
        });
    }
}