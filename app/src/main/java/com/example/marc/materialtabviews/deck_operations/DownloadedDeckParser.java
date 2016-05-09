package com.example.marc.materialtabviews.deck_operations;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public abstract class DownloadedDeckParser {
    public static final String JSONOBJECT_TABLE_KEY = "table";
    public static final String JSONOBJECT_ROW_KEY = "rows";
    public static final String JSONOBJECT_C_ARRAY = "c";
    private final String TAG = "DownloadedDeckParser";
    protected org.json.simple.parser.JSONParser parser = null;
    private Iterator<?> rowIterator = null;
    private String pathToFile = "";
    private Object obj = null;
    private JSONArray rows = null;

    public DownloadedDeckParser(String pathToFile) {
        this.pathToFile = pathToFile;
        parser = new org.json.simple.parser.JSONParser();
    }

    protected void initializeParsing() {
        String totalFilePath = android.os.Environment.getExternalStorageDirectory()
                + "/" + pathToFile;
        try {
            // Initializes parsing
            obj = parser.parse(new FileReader(totalFilePath));
        } catch (ParseException pe) {
            // Thrown by the parser.parse DownloadedDeckParser
            pe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            // Thrown if file does not exist.
            // TODO: Create file if not exist
            Log.e(TAG, "Could not find file");
            Log.e(TAG, "File path = " + totalFilePath);
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            // Thrown possibly by the getExternalStorageDirectory if we don't
            // have proper permissions.
            Log.e(TAG, "IOException coming right up");
            Log.e(TAG, "File path = " + totalFilePath);
            ioe.printStackTrace();
        } finally {
            if (obj == null) { // We should handle this better.
                Log.e(TAG, "Exception occurred in parsing JSON");
            }
        }

        JSONObject jsonObject = (JSONObject) obj;
        JSONObject table = (JSONObject) jsonObject.get(JSONOBJECT_TABLE_KEY);

        rows = (JSONArray) table.get(JSONOBJECT_ROW_KEY);
        rowIterator = rows.iterator();
    }

    public Object getNext() {
        if (rowIterator.hasNext()) {
            JSONObject next = (JSONObject) rowIterator.next();
            JSONArray name = (JSONArray) next.get(JSONOBJECT_C_ARRAY); // Gets c
            Iterator<?> values = name.iterator();

            int count = 0;
            // We can handle up to 3 columns. If need be, change this
            // to more. Nothing else should break, in here at least.
            JSONObject[] stuffsInside = new JSONObject[3];

            while (values.hasNext()) { // For all "v" labeled things
                JSONObject thisValue = (JSONObject) values.next();
                stuffsInside[count] = thisValue;
                count++;
            }

            return configureReturn(stuffsInside);
        } else {
            // No more data
            return null;
        }
    }

    protected abstract Object configureReturn(JSONObject[] stuffsInside);

    public boolean hasNext() {
        return rowIterator.hasNext();
    }
}
