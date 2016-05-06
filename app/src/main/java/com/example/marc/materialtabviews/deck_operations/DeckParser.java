package com.example.marc.materialtabviews.deck_operations;

import com.example.marc.materialtabviews.model.Deck;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
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

    public void initializeParsing() {
        String pathToFile = "/flashofacts/" + fileName + ".json";
        File fileHere = new File(pathToFile);

        try {
            // Creates new file if it doesn't exist
            //noinspection ResultOfMethodCallIgnored
            if (fileHere.createNewFile()) {
                // Initializes parsing
                obj = parser.parse(new FileReader(android.os.Environment.
                        getExternalStorageDirectory() + pathToFile));
            }
        } catch (ParseException pe) {
            // Thrown by the parser.parse DownloadedDeckParser
            pe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            // If file does not exist
            // TODO: Create file if it does not exist
        } catch (IOException ioe) {
            // Thrown possibly by the getExternalStorageDirectory if we don't
            // have proper permissions.
            ioe.printStackTrace();
        } finally {
            if (obj == null) {
                deckIterator = null;
            } else {
                JSONArray deckArray = (JSONArray) obj;
                deckIterator = deckArray.iterator();
            }
        }
    }

    public Deck getNextDeck() {
        if (deckIterator.hasNext()) {
            JSONObject next = (JSONObject) deckIterator.next();

            String name = (String) next.get("name");
            String key = (String) next.get("key");
            String code = (String) next.get("code");

            return new Deck(name, key, code);
        } else {
            // No more data
            return null;
        }
    }

    public boolean hasNext() {
        return deckIterator.hasNext();
    }

}
