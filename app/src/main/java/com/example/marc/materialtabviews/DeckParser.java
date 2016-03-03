package com.example.marc.materialtabviews;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class DeckParser {

    protected org.json.simple.parser.JSONParser parser = null;
    private Iterator<?> deckIterator = null;
    private String fileName = "";
    private Object obj = null;

    public DeckParser() {
    }

    public DeckParser(String fileName) {
        this.fileName = fileName;
        parser = new org.json.simple.parser.JSONParser();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected void initializeParsing() {
        String pathToFile = "/flashofacts/" + fileName + ".json";
        File fileHere = new File(fileName);
        try {
            // Creates new file if it doesn't exist
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

        JSONArray deckArray = (JSONArray) obj;

        deckIterator = deckArray.iterator();
    }

    public Deck getNextDeck() {
        if (deckIterator.hasNext()) {
            JSONObject next = (JSONObject) deckIterator.next();

            String name = (String) next.get("name");
            String key = (String) next.get("key");
            String code = (String) next.get("code");

            Deck returnDeck = new Deck(name, key, code);
            return returnDeck;
        } else {
            // No more data
            return null;
        }
    }

    public boolean hasNext() {
        return deckIterator.hasNext();
    }

}
