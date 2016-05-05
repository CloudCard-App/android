package com.example.marc.materialtabviews.card_quiz;

import com.example.marc.materialtabviews.model.Card;
import com.example.marc.materialtabviews.deck_operations.DownloadedDeckParser;

import org.json.simple.JSONObject;

public class CardQuizReader extends DownloadedDeckParser {

    public CardQuizReader(String fileName) {
        super(fileName);
        initializeParsing();
    }

    @Override
    /**
     * This is called every time getNext is called; it prepares the return.
     */
    protected Card configureReturn(JSONObject[] stuffsInside) {
        if (stuffsInside != null) {
            // Strips away {"v":" and "} from the **good stuff**
            String front = stuffsInside[0].toString().
                    substring(6, stuffsInside[0].toString().length() - 2);
            String back = stuffsInside[1].toString().
                    substring(6, stuffsInside[1].toString().length() - 2);

            return new Card(front, back);
        } else {
            return null;
        }
    }

}
