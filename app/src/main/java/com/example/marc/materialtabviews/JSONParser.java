package com.example.marc.materialtabviews;

import android.support.annotation.NonNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by marc on 152812.
 */
public abstract class JSONParser {
    protected org.json.simple.parser.JSONParser parser = null;
    Iterator<?> rowIterator = null;
    String fileName = "";
    private Object obj = null;
    private JSONObject table = null;
    private JSONArray row = null;
    private Iterator<?> values = null;

    public JSONParser(String fileName) {
        this.fileName = fileName;
        parser = new org.json.simple.parser.JSONParser();
    }

    protected void initializeParsing() {
        String pathToFile = "/flashofacts/" + fileName + ".json";
        System.out.println("DeckChooserReader.initializeParsing fileName = " + fileName);
        System.out.println("pathToFile = " + pathToFile);
        try {
            obj = parser.parse(new FileReader(android.os.Environment.getExternalStorageDirectory() + pathToFile));
        } catch (ParseException pe) { //Thrown by the parser.parse JSONParser
            System.out.println("CardReader.CardReader ParseException");
            pe.printStackTrace();
        } catch (FileNotFoundException fnfe) { //Thrown by the FileReader
            System.out.println("CardReader.CardReader FileNotFoundException");
            fnfe.printStackTrace();
        } catch (IOException ioe) { //Thrown possibly by the getExternalStorageDirectory, if we don't have proper permissions.
            System.out.println("CardReader.CardReader IOException");
            ioe.printStackTrace();
        } finally {
            if (obj == null) { //We should handle this better.
                System.exit(-1); //Oh my.
            }
        }

        JSONObject jsonObject = (JSONObject) obj;

        table = (JSONObject) jsonObject.get("table");
        System.out.println("table: " + table);

        row = (JSONArray) table.get("rows");
        System.out.println("row: " + row);
        System.out.println();

        rowIterator = row.iterator();
    }

    public Object getNext() {
        if (rowIterator.hasNext()) {
            JSONObject next = (JSONObject) rowIterator.next();
            JSONArray name = (JSONArray) next.get("c"); //Gets c
            values = name.iterator(); //Iterator over all "v"

            int count = 0;
            //If we want to utilize spreadsheets more than n * 2 cells,
            //then we would need to change the size of stuffsInside.
            JSONObject[] stuffsInside = new JSONObject[2];

            while (values.hasNext()) { //For all "v" labeled things
                JSONObject thisValue = (JSONObject) values.next();
                stuffsInside[count] = thisValue;
                count++;
            }

            Object theReturn = configureReturn(stuffsInside);

            return theReturn;
        } else {
            //Nope! No more!
            return null;
        }
    }

    @NonNull
    protected abstract Object configureReturn(JSONObject[] stuffsInside);

    public boolean hasNext() {
        return rowIterator.hasNext();
    }
}