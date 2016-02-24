package com.example.marc.materialtabviews;

import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

public class DeckChooserReader extends com.example.marc.materialtabviews.JSONParser {

    public DeckChooserReader(String fileName) {
        super(fileName);
        initializeParsing(); // Do we really need this if super() does it already?
    }

    @Override
    @NonNull
    protected Deck configureReturn(JSONObject[] stuffsInside) {
        if (stuffsInside != null) {
            // Strips away {"v":" and "} from the **good stuff**
            String deckName = stuffsInside[0].toString().substring(6,
                    stuffsInside[0].toString().length() - 2);
            String deckKey = stuffsInside[1].toString().substring(6,
                    stuffsInside[1].toString().length() - 2);
            String deckCode = stuffsInside[2].toString().substring(6,
                    stuffsInside[2].toString().length() - 2);
            return new Deck(deckName, deckKey, deckCode);
        } else {
            return null;
        }
    }

}
