package com.example.marc.materialtabviews.your_decks;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YourDeckLister {

    private final String TAG = "YourDeckLister";
    private String directory = "";

    public YourDeckLister(String directory) {
        this.directory = directory;
    }

    public List<String> getListOfSheets() {
        File folder = new File(Environment.getExternalStorageDirectory().toString() + directory);
        File[] listOfFiles = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File each : listOfFiles) {
            if (each.isFile() && !each.getName().equals("decks.json")) {
                fileNames.add(each.getName());
            }
        }
        return fileNames;
    }
}
