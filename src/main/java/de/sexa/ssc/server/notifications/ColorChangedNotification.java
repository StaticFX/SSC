package de.sexa.ssc.server.notifications;

import de.sexa.ssc.Main;
import org.json.JSONObject;

import java.awt.*;

public class ColorChangedNotification implements Notification {

    @Override
    public String getName() {
        return "notification:color:changed";
    }

    @Override
    public void execute(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        int r = data.getInt("r");
        int g = data.getInt("g");
        int b = data.getInt("b");

        Main.playerColor = new Color(r,g,b);

        if (Main.self != null)
            Main.self.setColor(new Color(r,g,b));
    }
}
