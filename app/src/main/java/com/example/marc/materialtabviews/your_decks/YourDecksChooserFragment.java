package com.example.marc.materialtabviews.your_decks;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marc.materialtabviews.R;
import com.example.marc.materialtabviews.card_quiz.CardQuizFragment;
import com.example.marc.materialtabviews.deck_operations.DownloadedDeckChooserReader;

import java.util.ArrayList;
import java.util.List;

public class YourDecksChooserFragment extends ListFragment {

    List<String> deckNameList = new ArrayList<>();

    public YourDecksChooserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.deck_chooser_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        YourDeckLister lister = new YourDeckLister("./");

        deckNameList = lister.getListOfSheets();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        /* String fileName = deckNameList.get((int) id);
        DownloadedDeckChooserReader reader = new DownloadedDeckChooserReader(fileName);
//        Deck selection = reader.getNext(); // WRONG

//        Deck selection = deckList.get((int) id); // Gets the clicked place.
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

        // We can get a fragment manager from the Fragment superclass
        FragmentManager fragmentManager = getFragmentManager();

        // We do the transaction, replacing the container with
        // the code fragment and then committing.
        fragmentManager.beginTransaction()
                .replace(R.id.container, cardQuiz)
                .commit();
        System.out.println("Finished committing fragment transaction!");
        */
    }
}
