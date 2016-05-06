package com.example.marc.materialtabviews.your_decks;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marc.materialtabviews.R;
import com.example.marc.materialtabviews.card_quiz.CardQuizFragment;

import java.util.ArrayList;
import java.util.List;

public class YourDecksChooserFragment extends ListFragment {

    List<String> deckNameList = new ArrayList<>();

    private final String TAG = "YourDecksChooser";

    public YourDecksChooserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        Log.i(TAG, "Created view successfully");
        return inflater.inflate(R.layout.deck_chooser_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "Activity created");
        YourDeckLister lister = new YourDeckLister("/flashofacts/");
        Log.i(TAG, "Lister created");

        deckNameList = lister.getListOfSheets();
        Log.i(TAG, "Got list of sheets from lister");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);
        setListAdapter(adapter);
        Log.i(TAG, "Set list adapter");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CardQuizFragment cardQuiz = new CardQuizFragment();
        cardQuiz.setName(deckNameList.get((int) id));
        cardQuiz.setShouldDownload(false);

        // We can get a fragment manager from the Fragment superclass
        FragmentManager fragmentManager = getFragmentManager();

        // We do the transaction, replacing the container with
        // the code fragment and then committing.
        fragmentManager.beginTransaction()
                .replace(R.id.container, cardQuiz)
                .commit();
    }
}
