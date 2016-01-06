package com.example.marc.materialtabviews;

import java.util.ArrayList;

/**
 * Created by marc on 152812.
 */
public class CardQuizDownloader extends Downloader {

    public CardQuizDownloader(String key, String fileName, OnTaskCompleted completionWaiter) {
        super(fileName, completionWaiter, key);
    }

    @Override
    //Actually, this runs on the **main** thread
    //Weee!
    protected void onPostExecute(String result) {

        CardQuizReader reader = new CardQuizReader(getFileName());
        ArrayList<Object> cardList = new ArrayList<>();

        while (reader.hasNext()) {
            Card thisCard = (Card) reader.getNext();
            cardList.add(thisCard);
        }

        completionWaiter.onTaskCompleted(cardList);
    }

}
