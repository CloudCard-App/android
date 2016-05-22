package com.example.marc.materialtabviews.your_decks;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.marc.materialtabviews.Downloader;
import com.example.marc.materialtabviews.MainActivity;
import com.example.marc.materialtabviews.R;
import com.example.marc.materialtabviews.card_quiz.CardQuizFragment;

import java.util.ArrayList;
import java.util.List;

public class YourDecksChooserFragment extends ListFragment {

    private final String TAG = "YourDecksChooserFr";
    List<String> deckNameList = new ArrayList<>();

    public YourDecksChooserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.yourDecks));

        return inflater.inflate(R.layout.deck_chooser_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setEmptyView(getActivity().findViewById(android.R.id.empty));

        ImageView emptyImage = (ImageView) getView().findViewById(android.R.id.empty);
        emptyImage.setImageResource(R.drawable.no_saved_decks);

        YourDeckLister lister = new YourDeckLister(Downloader.APP_DIRECTORY);
        deckNameList = lister.getListOfSheets();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deckNameList);
        setListAdapter(adapter);
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
