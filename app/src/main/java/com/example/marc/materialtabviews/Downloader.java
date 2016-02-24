package com.example.marc.materialtabviews;

import android.os.AsyncTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Downloader extends AsyncTask<String, Integer, String> {
    protected String key = "";
    protected String fileName = "";
    protected OnTaskCompleted completionWaiter;

    public Downloader(String fileName, OnTaskCompleted completionWaiter, String key) {
        this.fileName = fileName;
        this.completionWaiter = completionWaiter;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {

    }

    public void downloadTheSheet() {
        // TODO: Replace all System.out.printlns with Log
        // System.out.println() is not synchronous, so they are out of sync with actual happenings
        System.out.println("DeckChooserDownloader.downloadTheSheet");
        System.out.println("--------------------------------------");

        try {
            String urlString = "https://spreadsheets.google.com/tq?key=" + getKey();
            System.out.println("The spreadsheet is here: https:// docs.google.com/spreadsheets/d/"
                    + getKey() + "/edit#gid=0");

            URL spreadsheetURL = new URL(urlString);
            // To get the editable spreadsheet, insert the key:
            // https:// docs.google.com/spreadsheets/d/**** insert key here ****/edit#gid=0

            // flashofacts is the directory under which we store our files.
            String filePath = "/flashofacts/" + getFileName() + ".json";
            System.out.println("downloadTheSheet filePath = " + filePath);
            File jsonOutput = new File(android.os.Environment.getExternalStorageDirectory(),
                    filePath);

            FileUtils.write(jsonOutput, "");
            FileUtils.copyURLToFile(spreadsheetURL, jsonOutput);

            String fileString = FileUtils.readFileToString(jsonOutput);
            int indexOfStartJSON = fileString.indexOf('{');
            int indexOfEndJSON = fileString.length() - 2;
            String substringed = fileString.substring(indexOfStartJSON, indexOfEndJSON);

            System.out.println("The Directory: " + jsonOutput.getPath());

            FileUtils.write(jsonOutput, substringed);
        } catch (MalformedURLException murle) {
            System.out.println("DeckChooserDownloader.downloadTheSheet MALFORMEDURLEXCEPTION");
            murle.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("DeckChooserDownloader.downloadTheSheet IOEXCEPTION");
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
