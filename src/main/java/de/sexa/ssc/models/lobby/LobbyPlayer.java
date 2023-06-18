package de.sexa.ssc.models.lobby;

import java.awt.*;
import java.util.UUID;

public class LobbyPlayer {

    private final UUID uuid;
    private final String name;
    private Color color;
    private final boolean isSpectator;

    private int canon;

    public LobbyPlayer(UUID uuid, String name, Color color, boolean isSpectator, int canon) {
        this.uuid = uuid;
        this.name = name;
        this.color = color;
        this.isSpectator = isSpectator;
        this.canon = canon;
    }

    public int getCanon() {
        return canon;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCanon(int canon) {
        this.canon = canon;
    }
}
