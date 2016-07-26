package com.example.marc.materialtabviews.settings;

import android.util.Log;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsManager {
    private static final String TAG = "SettingsManager";
    private Settings settings;
    private ObjectMapper mapper;
    private String fileName;

    public SettingsManager(String fileName) {
        settings = null;
        mapper = new ObjectMapper();
        this.fileName = fileName;
        loadSettings();
    }

    public Settings getSettings() {
        return settings;
    }

    public boolean writeSettings() {
        try {
            mapper.writeValue(new File(fileName), settings);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error in committing changes to settings: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void loadSettings() {
        File file = new File(fileName);
        try {
            settings = mapper.readValue(file, Settings.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            Log.e(TAG, "Error in mapping JSON to Settings object: " + e.getMessage());
        } catch (FileNotFoundException fnfe) {
            Log.i(TAG, "Settings file not found: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error in reading from JSON settings file: " + e.getMessage());
        }
    }
}
