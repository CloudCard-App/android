package com.example.marc.materialtabviews.deck_chooser;

import com.example.marc.materialtabviews.Downloader;
import com.example.marc.materialtabviews.OnTaskCompleted;
import com.example.marc.materialtabviews.deck_operations.DownloadedDeckChooserReader;
import com.example.marc.materialtabviews.model.Deck;

import java.util.ArrayList;

/**
 * Downloads the list of decks, keys, and codes.
 */
public class DeckChooserDownloader extends Downloader {

    public DeckChooserDownloader(String key, String fileName,
                                 OnTaskCompleted completionWaiter) {
        super(fileName, completionWaiter, key);
    }

    @Override
    // Actually, this runs on the **main** thread
    // Weee!
    protected void onPostExecute(Boolean result) {
        if (result) {
            DownloadedDeckChooserReader reader = new DownloadedDeckChooserReader(getFilePath());
            ArrayList<Object> deckList = new ArrayList<>();

            while (reader.hasNext()) {
                Deck thisDeck = (Deck) reader.getNext();
                deckList.add(thisDeck);
            }
            completionWaiter.onTaskCompleted(deckList);
        } else {
            //TODO: Tell the user to use his cached decks
        }
    }

}
