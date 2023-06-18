package de.sexa.ssc.server.util;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

public class ActionBuilder {

    private final String action;
    private final JSONObject json;
    private final JSONObject parameters;

    public ActionBuilder(String action) {
        this.action = action;
        json = new JSONObject();
        json.put("action", action);
        json.put("UUID", MinecraftClient.getInstance().getSession().getUuid());

        parameters = new JSONObject();
    }


    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public JSONObject build() {
        json.put("parameters", parameters);
        return json;
    }



}
