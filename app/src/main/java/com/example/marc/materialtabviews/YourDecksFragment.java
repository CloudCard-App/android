package com.example.marc.materialtabviews;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class YourDecksFragment extends ListFragment {

    private List<Deck> deckList = new ArrayList<>();
    private List<String> deckNameList = new ArrayList<>();
    private DeckParser deckParser;

    public YourDecksFragment() {

        deckParser.setFileName("savedDecks");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Removes the view below it, such that this view does not
        // appear on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the card_quiz_fragment inside container
        // This is very important to call.
        return inflater.inflate(R.layout.deck_chooser_fragment, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        deckParser.initializeParsing();
        while (deckParser.hasNext()) {
            Deck thisDeck = deckParser.getNextDeck();
            deckList.add(thisDeck);
            deckNameList.add(thisDeck.getName());
        }

        // Set the adapter of the view to the schoolNameList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);

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
        System.out.println("YourDecksFragment.onListItemClick");
        Deck selection = deckList.get((int) id); // Gets the clicked place.
        String key = selection.getKey(); // Gets the key of the clicked place.
        String correctCode = selection.getCode();

        System.out.println("selection = " + selection);
        System.out.println("key = " + key);
        System.out.println("code = " + correctCode);

        // TODO Look into using bundles
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


}
