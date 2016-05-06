package com.example.marc.materialtabviews;

import android.os.AsyncTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Downloader extends AsyncTask<String, Integer, String> {
    protected OnTaskCompleted completionWaiter;
    private String key = "";
    private String fileName = "";

    public Downloader(String fileName, OnTaskCompleted completionWaiter, String key) {
        this.fileName = fileName;
        this.completionWaiter = completionWaiter;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {

    }

    protected void downloadTheSheet() {
        try {
            String urlString = "https://spreadsheets.google.com/tq?key=" + getKey();

            URL spreadsheetURL = new URL(urlString);
            // To get the editable spreadsheet, insert the key:
            // https:// docs.google.com/spreadsheets/d/**** insert key here ****/edit#gid=0

            String filePath = R.string.appDirectory + getFileName() + ".json";
            File jsonOutput = new File(android.os.Environment.getExternalStorageDirectory(),
                    filePath);

            FileUtils.write(jsonOutput, "");
            FileUtils.copyURLToFile(spreadsheetURL, jsonOutput);

            String fileString = FileUtils.readFileToString(jsonOutput);
            int indexOfStartJSON = fileString.indexOf('{');
            int indexOfEndJSON = fileString.length() - 2;
            String substringed = fileString.substring(indexOfStartJSON, indexOfEndJSON);

            FileUtils.write(jsonOutput, substringed);
        } catch (MalformedURLException murle) {
            murle.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected String doInBackground(String... params) {
        downloadTheSheet();
        return null;
    }

    @Override
    // Actually, this runs on the **main** thread
    protected abstract void onPostExecute(String result);

    protected String getFileName() {
        return fileName;
    }

    private String getKey() {
        return key;
    }
}
