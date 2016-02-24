package com.example.marc.materialtabviews;

import java.util.ArrayList;

public class DeckChooserDownloader extends Downloader {

    public DeckChooserDownloader(String key, String fileName,
                                 OnTaskCompleted completionWaiter) {
        super(fileName, completionWaiter, key);
    }

    @Override
    // Actually, this runs on the **main** thread
    // Weee!
    protected void onPostExecute(String result) {

        System.out.println("onPostExecute fileName = " + getFileName());
        DeckChooserReader reader = new DeckChooserReader(getFileName());
        ArrayList<Object> deckList = new ArrayList<>();

        while (reader.hasNext()) {
            Deck thisDeck = (Deck) reader.getNext();
            deckList.add(thisDeck);
        }

        completionWaiter.onTaskCompleted(deckList);
    }

}
