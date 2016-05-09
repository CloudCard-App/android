package com.example.marc.materialtabviews;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class Downloader extends AsyncTask<String, Integer, Boolean> {

    public static final String APP_DIRECTORY = "/CloudCard/";
    public static final String DECK_JSON = "deck.json";
    private static final String URL_BASE = "https://spreadsheets.google.com/tq?key=";
    private final String TAG = "Downloader";
    protected OnTaskCompleted completionWaiter;
    private String key = "";
    private String filePath = "";

    public Downloader(String filePath, OnTaskCompleted completionWaiter, String key) {
        this.filePath = filePath;
        this.completionWaiter = completionWaiter;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {

    }

    /**
     * Downloads the sheet and writes the contents into the filePath.
     *
     * @return True if download was successful; False if download failed
     */
    protected boolean downloadTheSheet() {
        try {
            String urlString = URL_BASE + getKey();

            URL spreadsheetURL = new URL(urlString);
            // To get the editable spreadsheet, insert the key:
            // https:// docs.google.com/spreadsheets/d/**** insert key here ****/edit#gid=0

            File jsonOutput = new File(android.os.Environment.getExternalStorageDirectory(),
                    getFilePath());

            FileUtils.write(jsonOutput, "");
            FileUtils.copyURLToFile(spreadsheetURL, jsonOutput);

            String fileString = FileUtils.readFileToString(jsonOutput);
            int indexOfStartJSON = fileString.indexOf('{');
            int indexOfEndJSON = fileString.length() - 2;
            String substringed = fileString.substring(indexOfStartJSON, indexOfEndJSON);

            FileUtils.write(jsonOutput, substringed);
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "IOException when writing to file");
            Log.e(TAG, "FilePath = " + filePath);
            ioe.printStackTrace();
        }
        Log.v(TAG, "Could not download deck due to network.");
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }


    @Override
    protected Boolean doInBackground(String... params) {
        return downloadTheSheet();
    }

    @Override
    // Actually, this runs on the **main** thread
    protected abstract void onPostExecute(Boolean result);

    protected String getFilePath() {
        return filePath;
    }

    private String getKey() {
        return key;
    }
}
