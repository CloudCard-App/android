package com.example.marc.materialtabviews;

import java.util.ArrayList;

/**
 * Created by marc on 16031.
 */
public class SchoolChooserDownloader extends Downloader {

    public SchoolChooserDownloader(String fileName, OnTaskCompleted completionWaiter, String key) {
        super(fileName, completionWaiter, key);
    }

    @Override
    protected void onPostExecute(String result) {
        SchoolChooserReader reader = new SchoolChooserReader(getFileName());
        ArrayList<Object> schoolList = new ArrayList<>();

        while (reader.hasNext()) {
            School thisSchool = (School)reader.getNext();
            schoolList.add(thisSchool);
        }

        completionWaiter.onTaskCompleted(schoolList);

    }

}
