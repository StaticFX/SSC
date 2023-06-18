package de.sexa.ssc.server.util;

public enum ServerStatus {
    DISCONNECTED("Disconnected"),
    CONNECTED("Connected");



    ServerStatus(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }
}
