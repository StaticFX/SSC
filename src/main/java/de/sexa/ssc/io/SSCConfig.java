package de.sexa.ssc.io;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SSCConfig {

    private final File location;

    private JSONObject jsonObject;

    public SSCConfig(File location) {
        this.location = location;
    }


    public void init(String resourceLocation) {
        if (!location.exists()) {
            try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourceLocation)) {
                Files.copy(in, location.toPath());
            }catch (Exception e) {
                e.printStackTrace();
                System.err.println("Unable to load config file for SSC: " + e.getMessage());
                return;
            }
        }

        try {
            String lines = Files.readString(location.toPath());
            jsonObject = new JSONObject(lines);
        } catch (IOException e) {
            System.err.println("Unable to load config file for SSC: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return jsonObject.getString(key);
    }

    public int getInt(String key) {
        return jsonObject.getInt(key);
    }

    public void setAndSave(String key, Object value) {
        jsonObject.put(key, value);
        try(FileOutputStream fs = new FileOutputStream(location)) {
            fs.write(jsonObject.toString(1).getBytes());
            fs.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}