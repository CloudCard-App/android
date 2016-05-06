package com.example.marc.materialtabviews.your_decks;

import android.os.Environment;
import android.util.Log;

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
        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/flashofacts/");
        Log.i(TAG, "Found Directory");
        File[] listOfFiles = folder.listFiles();
        Log.i(TAG, "Got list of files");
        List<String> fileNames = new ArrayList<>();
        Log.i(TAG, "Created list of fileNames");
        for (File each : listOfFiles) {
            if (each.isFile()) {
                fileNames.add(each.getName());
            }
        }
        return fileNames;
    }
}
