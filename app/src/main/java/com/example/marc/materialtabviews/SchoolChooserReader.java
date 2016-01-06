package com.example.marc.materialtabviews;

import android.support.annotation.NonNull;

import org.json.simple.JSONObject;

/**
 * Created by marc on 16031.
 */
public class SchoolChooserReader extends JSONParser {

    public SchoolChooserReader(String fileName) {
        super(fileName);
        initializeParsing(); //Do we really need this if super() does it already?
    }

    @NonNull
    @Override
    protected Object configureReturn(JSONObject[] stuffsInside) {

        if (stuffsInside != null) {
            //Strips away {"v":" and "} from the **good stuff**
            String name= stuffsInside[0].toString().substring(6, stuffsInside[0].toString().length() - 2);
            String key = stuffsInside[1].toString().substring(6, stuffsInside[1].toString().length() - 2);
            String password = stuffsInside[2].toString().substring(6, stuffsInside[2].toString().length() - 2);

            return new School(name, key, password);
        } else {
            return null;
        }

    }

}
