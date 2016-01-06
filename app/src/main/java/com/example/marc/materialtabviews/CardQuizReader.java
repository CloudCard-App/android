package com.example.marc.materialtabviews;

import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

/**
 * Created by marc on 152812.
 */
public class CardQuizReader extends com.example.marc.materialtabviews.JSONParser {

    public CardQuizReader(String fileName) {
        super(fileName);
        initializeParsing();
    }

    @Override
    @NonNull
    /**
     * This is called every time getNext is called; it prepares the return.
     */
    protected Card configureReturn(JSONObject[] stuffsInside) {
        if (stuffsInside != null) {
            //Strips away {"v":" and "} from the **good stuff**
            String front = stuffsInside[0].toString().substring(6, stuffsInside[0].toString().length() - 2);
            String back = stuffsInside[1].toString().substring(6, stuffsInside[1].toString().length() - 2);

            return new Card(front, back);
        } else {
            return null;
        }
    }

}
