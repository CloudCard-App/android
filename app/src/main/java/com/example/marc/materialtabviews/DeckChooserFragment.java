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

/**
 * Created by marc on 152012.
 */
public class DeckChooserFragment extends ListFragment implements OnTaskCompleted {

    ArrayList<Deck> deckList = new ArrayList<>();
    ArrayList<String> deckNameList = new ArrayList<>();

    public DeckChooserFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Removes the view below it, such that this view does not appear on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }

        //Inflate the card_quiz_fragment inside container
        //This is very important to call.
        View view = inflater.inflate(R.layout.deck_chooser_fragment, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set the adapter of the view to the schoolNameList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);

        //This is the key to the lookup spreadsheet.
        //It contains deck names and their corresponding keys.
//        String key = "1ZSFSttwa975pKyCEyEehshjSkcbfb2EomaVg1PF5eds";
        String key = "1Zs0ydpL1twVTgUNi_h9b4KHRafrTMnUljOorwdfBm8I";

        //This is the file where we will save the JSON of the lookup spreadsheet.
        String fileName = "decks";

        //this is used for the OnTaskCompleted interface
        //When it completes everything, it calls onTaskCompleted
        //And passes the arrayList of Deck as data.
        DeckChooserDownloader downloader = new DeckChooserDownloader(key, fileName, this);
        downloader.execute();

        setListAdapter(adapter);

        /*
        old lookup: https://drive.google.com/open?id=1ZSFSttwa975pKyCEyEehshjSkcbfb2EomaVg1PF5eds
        new lookup: https://drive.google.com/file/d/0B9CWcw1blzM2blpGY01sMVIzaHc/view?usp=sharing

         */

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
        Deck selection = deckList.get((int) id); //Gets the clicked place.
        String key = selection.getKey(); //Gets the key of the clicked place.

        System.out.println("selection = " + selection);
        System.out.println("key = " + key);

        CardQuizFragment fragment = new CardQuizFragment();

        //TODO Look into using bundles (error non-default constructors)
        fragment.setKey(key); //Sets the key.

        //We can get a fragment manager from where?
        FragmentManager fragmentManager = getFragmentManager();

        //We do the transaction, replacing the container with this fragment and then committing.
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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
    public void onTaskCompleted(ArrayList<Object> data) {

        //Populate the schoolNameList array with names of the decks.
        //We still will need the schoolList itself for the lookup
        //of names and keys inside onListItemClick.
        for (Object each : data) {
            deckList.add((Deck) each);
            deckNameList.add(((Deck) each).getName());
        }

        //Because we like our users.
        Collections.sort(deckNameList);

        //Create a new adapter with schoolNameList.
        //TODO Maybe look into casting into a BaseAdapter, and then calling something like
        //TODO notifyDataSetChanged().
        try {
            ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, deckNameList);
            //Set the adapter again.
            setListAdapter(newAdapter);
        } catch (NullPointerException npe) {
            Log.d("DeckChooserFragment", "No data received from deckdownloader.");
            npe.printStackTrace();
        }
    }
}
