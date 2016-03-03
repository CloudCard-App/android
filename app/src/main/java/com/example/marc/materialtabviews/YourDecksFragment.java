package com.example.marc.materialtabviews;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class YourDecksFragment extends ListFragment {

    private List<Deck> deckList = new ArrayList<>();
    private List<String> deckNameList = new ArrayList<>();
    private DeckParser deckParser = new DeckParser();

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
        View view = inflater.inflate(R.layout.deck_chooser_fragment, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set the adapter of the view to the schoolNameList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);

        setListAdapter(adapter);
    }


}
