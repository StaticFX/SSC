package de.sexa.ssc.models.lobby;

import java.util.UUID;

public class LobbyPlayerSerialized {

    private final UUID uuid;
    private final String name;
    private final int r;
    private final int g;
    private final int b;
    private final boolean isSpectator;

    private final int canon;

    public LobbyPlayerSerialized(UUID uuid, String name, int r, int g, int b, boolean isSpectator, int canon) {
        this.uuid = uuid;
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
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

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

}
