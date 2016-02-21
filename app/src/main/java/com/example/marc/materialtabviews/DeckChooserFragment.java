package com.example.marc.materialtabviews;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marc on 152012.
 */
public class DeckChooserFragment extends ListFragment implements OnTaskCompleted {

    ArrayList<Deck> deckList = new ArrayList<>();
    ArrayList<String> deckNameList = new ArrayList<>();

    public DeckChooserFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Removes the view below it, such that this view does not appear on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the card_quiz_fragment inside container
        // This is very important to call.
        View view = inflater.inflate(R.layout.deck_chooser_fragment, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set the adapter of the view to the schoolNameList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);

        // This is the key to the lookup spreadsheet.
        // It contains deck names and their corresponding keys.
        String key = "1Zs0ydpL1twVTgUNi_h9b4KHRafrTMnUljOorwdfBm8I";

        // This is the file where we will save the JSON of the lookup spreadsheet.
        String fileName = "decks";

        // this is used for the OnTaskCompleted interface
        // When it completes everything, it calls onTaskCompleted
        // And passes the arrayList of Deck as data.
        DeckChooserDownloader downloader = new DeckChooserDownloader(key, fileName, this);
        downloader.execute();

        setListAdapter(adapter);
    }

    /**
     * Woot! We have a click!
     * I don't think l, v, or position matter much right now.
     *
     * @param l        ListView where the click happened
     * @param v        View that was clicked within the ListView
     * @param position Position of the view in the list
     * @param id       Row id of the clicked item
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("DeckChooserFragment.onListItemClick");
        Deck selection = deckList.get((int) id); // Gets the clicked place.
        String key = selection.getKey(); // Gets the key of the clicked place.
        String correctCode = selection.getCode();

        System.out.println("selection = " + selection);
        System.out.println("key = " + key);
        System.out.println("code = " + correctCode);

        // TODO Look into using bundles (error non-default constructors)
        CardQuizFragment cardQuiz = new CardQuizFragment();
        cardQuiz.setName(selection.getName());
        cardQuiz.setKey(selection.getKey()); // Sets the key.
        cardQuiz.setCode(selection.getCode());

        // Here, ask for the code
        EnterCodeFragment codeAuth = new EnterCodeFragment(this,
                cardQuiz,
                correctCode,
                selection.getName());

        // We can get a fragment manager from the Fragment superclass
        FragmentManager fragmentManager = getFragmentManager();

        // We do the transaction, replacing the container with
        // the code fragment and then committing.
        fragmentManager.beginTransaction()
                .replace(R.id.container, codeAuth)
                .commit();
        System.out.println("Finished committing fragment transaction!");

    }

    @Override
    /**
     * We assume that the ArrayList<Object> that we get
     * is of type Deck, for purposes of reusability of the
     * OnTaskCompleted interface.
     *
     * @param data The result of the method that calls onTaskCompleted
     */
    public void onTaskCompleted(List<Object> data) {
        deckList.clear();
        deckNameList.clear();

        // Populate the schoolNameList array with names of the decks.
        // We still will need the schoolList itself for the lookup
        // of names and keys inside onListItemClick.
        for (Object each : data) {
            deckList.add((Deck) each);
        }

        // Because we like our users.
        Collections.sort(deckList);

        // Now, both the deckList and deckNameList will be sorted.
        for (Deck each : deckList) {
            deckNameList.add(each.getName());
        }

        // Create a new adapter with schoolNameList.
        // TODO Maybe look into casting into a BaseAdapter, and then calling something like
        // TODO notifyDataSetChanged().
        try {
            ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, deckNameList);
            // Set the adapter again.
            setListAdapter(newAdapter);
        } catch (NullPointerException npe) {
            Log.d("DeckChooserFragment", "No data received from deckdownloader.");
            npe.printStackTrace();
        }
    }
}
