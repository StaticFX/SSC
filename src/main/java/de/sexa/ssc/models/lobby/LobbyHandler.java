package de.sexa.ssc.models.lobby;

import java.util.UUID;

public class LobbyHandler {

    private Lobby currentLobby;

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void removePlayerFromLobby(UUID player) {
        currentLobby.removePlayer(player);
    }

}
