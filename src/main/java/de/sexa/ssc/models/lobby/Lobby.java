package de.sexa.ssc.models.lobby;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {

    private final int id;
    private final ArrayList<LobbyPlayer> players;
    private final String wargear;


    public Lobby(int id, ArrayList<LobbyPlayer> players, String wargear) {
        this.id = id;
        this.players = players;
        this.wargear = wargear;
    }

    public int getId() {
        return id;
    }

    public ArrayList<LobbyPlayer> getPlayers() {
        return players;
    }

    public String getWargear() {
        return wargear;
    }

    public void removePlayer(UUID player) {
        players.removeIf((lobbyPlayer -> lobbyPlayer.getUuid().equals(player) ));
    }

    public void addPlayer(LobbyPlayer player) {
        players.add(player);
    }

}
