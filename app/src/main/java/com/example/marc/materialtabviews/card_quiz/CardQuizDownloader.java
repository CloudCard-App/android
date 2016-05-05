package com.example.marc.materialtabviews.card_quiz;

import com.example.marc.materialtabviews.model.Card;
import com.example.marc.materialtabviews.model.DeckWithContents;
import com.example.marc.materialtabviews.Downloader;
import com.example.marc.materialtabviews.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class CardQuizDownloader extends Downloader {

    private DeckWithContents deckWithContents = null;

    public CardQuizDownloader(String name, String key, String code, String fileName,
                              OnTaskCompleted completionWaiter) {
        super(fileName, completionWaiter, key);
        deckWithContents = new DeckWithContents(name, key, code, null);
    }

    @Override
    // Actually, this runs on the **main** thread
    // Weee!
    protected void onPostExecute(String result) {

        CardQuizReader reader = new CardQuizReader(getFileName());
        ArrayList<Card> cardList = new ArrayList<>();

        while (reader.hasNext()) {
            Card thisCard = (Card) reader.getNext();
            cardList.add(thisCard);
        }

        deckWithContents.setCards(cardList);
        List<Object> resultDeckList = new ArrayList<>();
        resultDeckList.add(deckWithContents);
        completionWaiter.onTaskCompleted(resultDeckList);
    }

}
