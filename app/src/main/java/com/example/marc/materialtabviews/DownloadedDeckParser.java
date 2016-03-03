package com.example.marc.materialtabviews;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public abstract class DownloadedDeckParser {
    protected org.json.simple.parser.JSONParser parser = null;
    private Iterator<?> rowIterator = null;
    private String fileName = "";
    private Object obj = null;
    private JSONArray rows = null;

    public DownloadedDeckParser(String fileName) {
        this.fileName = fileName;
        parser = new org.json.simple.parser.JSONParser();
    }

    protected void initializeParsing() {
        String pathToFile = "/flashofacts/" + fileName + ".json";
        File fileHere = new File(pathToFile);
        try {
            // Creates new file if it doesn't exist
            //noinspection ResultOfMethodCallIgnored
            fileHere.createNewFile();
            // Initializes parsing
            obj = parser.parse(new FileReader(android.os.Environment.
                    getExternalStorageDirectory() + pathToFile));
        } catch (ParseException pe) {
            // Thrown by the parser.parse DownloadedDeckParser
            System.out.println("CardReader.CardReader ParseException");
            pe.printStackTrace();
        } catch (IOException ioe) {
            // Thrown possibly by the getExternalStorageDirectory if we don't
            // have proper permissions.
            System.out.println("CardReader.CardReader IOException");
            ioe.printStackTrace();
        } finally {
            if (obj == null) { // We should handle this better.
                System.out.println("Object was null in DownloadedDeckParser. Exiting now!");
                System.exit(-1); // Oh my.
            }
        }

        JSONObject jsonObject = (JSONObject) obj;

        JSONObject table = (JSONObject) jsonObject.get("table");
        System.out.println("table: " + table);

        rows = (JSONArray) table.get("rows");
        System.out.println("rows: " + rows);
        System.out.println();

        rowIterator = rows.iterator();
    }

    public Object getNext() {
        if (rowIterator.hasNext()) {
            JSONObject next = (JSONObject) rowIterator.next();
            JSONArray name = (JSONArray) next.get("c"); // Gets c
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
