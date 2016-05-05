package com.example.marc.materialtabviews.your_decks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YourDeckLister {

    private String directory = "";

    public YourDeckLister(String directory) {
        this.directory = directory;
    }

    public List<String> getListOfSheets() {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        assert listOfFiles != null;
        for (File each : listOfFiles) {
            if (each.isFile()) {
                fileNames.add(each.getName());
            }
        }
        return fileNames;
    }
}
