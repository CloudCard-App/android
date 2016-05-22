package com.example.marc.materialtabviews.card_quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marc.materialtabviews.Downloader;
import com.example.marc.materialtabviews.OnTaskCompleted;
import com.example.marc.materialtabviews.R;
import com.example.marc.materialtabviews.model.Card;
import com.example.marc.materialtabviews.model.DeckWithContents;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardQuizFragment extends Fragment implements OnTaskCompleted {

    private static final String TAG = "CardQuizFragment";
    private static final String url = "http://104.197.166.40:80/studentPost/";
    private TextView cardDisplay;
    private TextView numDisplay;
    private SeekBar progressSelect;
    private ImageButton backButton;
    private ImageButton nextButton;
    private int currentIndex = -1; // It doesn't have any index yet, before it starts.
    private int totalLength;
    private DeckWithContents deckWithContents = new DeckWithContents();
    private boolean shouldDownload = true;


    public CardQuizFragment() {
    }

    public void setShouldDownload(boolean shouldDownload) {
        this.shouldDownload = shouldDownload;
    }

    public void setName(String name) {
        deckWithContents.setName(name);
    }

    public void setKey(String key) {
        deckWithContents.setKey(key);
    }

    public void setCode(String code) {
        deckWithContents.setCode(code);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Begins the inflation of card_quiz_fragment.xml
     *
     * @return View that is used in onViewCreated
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Removes the view below it, such that this view does not appear
        // on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }

        reportEnterStudy(deckWithContents.getCode());

        // Inflate the card_quiz_fragment inside container
        // This is very important to call.
        View view = inflater.inflate(R.layout.card_quiz_fragment, container, false);

        // Now that it's inflated, start recording the actions.

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                nextCard();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                previousCard();
                            } else {
                                flipCard();
                            }
                        } catch (Exception e) {
                            Log.wtf(TAG, "Wt actual f");
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return view;
    }

    /**
     * Called right after onCreateView and uses the View onCreateView returns.
     *
     * @param view               The view passed in, created in onCreateView
     * @param savedInstanceState Not relevant here.
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getView() gets the default view for the fragment.
        cardDisplay = (TextView) getView().findViewById(R.id.cardDisplay);
        cardDisplay.setTextSize(30);
        numDisplay = (TextView) getView().findViewById(R.id.currentNumDisplay);

        progressSelect = (SeekBar) getView().findViewById(R.id.progressSelect);

        nextButton = (ImageButton) getView().findViewById(R.id.nextButton);
        backButton = (ImageButton) getView().findViewById(R.id.backButton);

        if (shouldDownload) {
            // Create the downloader, passing in this as the OnTaskCompleted listener
            String filePath = Downloader.APP_DIRECTORY + deckWithContents.getName();
            CardQuizDownloader downloader = new CardQuizDownloader(deckWithContents.getName(),
                    deckWithContents.getKey(), deckWithContents.getCode(), filePath, this);

            // Do in background stuffs.
            downloader.execute();
        } else {
            String filePath = Downloader.APP_DIRECTORY + deckWithContents.getName();
            // Deck name is also the file name
            CardQuizReader reader = new CardQuizReader(filePath);
            ArrayList<Card> cardList = new ArrayList<>();

            while (reader.hasNext()) {
                Card thisCard = (Card) reader.getNext();
                cardList.add(thisCard);
            }

            deckWithContents.setCards(cardList);
            List<Object> resultDeckList = new ArrayList<>();
            resultDeckList.add(deckWithContents);
            this.onTaskCompleted(false, resultDeckList);
        }
    }

    public void setData(DeckWithContents data) {
        List<Object> dataList = new ArrayList<>();
        dataList.add(data);
        onTaskCompleted(false, dataList);
    }

    /**
     * Is called by CardQuizDownloader when it finishes everything.
     *
     * @param err True if there was an error generated
     * @param data An ArrayList of Cards, disguised as Object.
     */
    @Override
    public void onTaskCompleted(Boolean err, List<Object> data) {

        cardDisplay.setText(R.string.swipeToStart);

        DeckWithContents deck = (DeckWithContents) data.get(0);
        deckWithContents = deck;
        List<Card> cards = deck.getCards();

        totalLength = cards.size();

        progressSelect.setMax(totalLength - 1);
        progressSelect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentIndex = progress;
                updateCard(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCard();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCard();
            }
        });
    }

    private void previousCard() {
        if (currentIndex > 0) { // If we're not at the beginning
            currentIndex--;
            updateCard(true);
        }
    }

    private void flipCard() {
        List<Card> cardData = deckWithContents.getCards();
        if (cardDisplay.getText().equals(cardData.get(currentIndex).getFront())) {
            updateCard(false); // Switch to back
            cardDisplay.setTextColor(getResources().getColor(R.color.lightBlue));
        } else if (cardDisplay.getText().equals(cardData.get(currentIndex).getBack())) {
            updateCard(true); // Switch to front
            cardDisplay.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        numDisplay.setText(MessageFormat.format("{0} | {1}", String.valueOf(currentIndex + 1), totalLength));
    }

    private void nextCard() {
        List<Card> cardData = deckWithContents.getCards();
        if (currentIndex < cardData.size() - 1) { // If we're not at the end
            currentIndex++;
            updateCard(true);
        }
    }

    private void updateCard(boolean front) {
        List<Card> cardData = deckWithContents.getCards();
        if (front) {
            cardDisplay.setText(cardData.get(currentIndex).getFront());
        } else {
            cardDisplay.setText(cardData.get(currentIndex).getBack());
        }
        numDisplay.setText(MessageFormat.format("{0} | {1}", String.valueOf(currentIndex + 1), totalLength));
        progressSelect.setProgress(currentIndex); // Wee-Wee!
    }

    @Override
    public void onStop() {
        // Record that the user has left.
        Log.i(TAG, "User stopped studying");
        reportExitStudy(deckWithContents.getCode());
        super.onStop();
    }

    public void reportEnterStudy(String code) {
        sendPostRequest(code, "enterstudy");
    }

    public void reportExitStudy(String code) {
        sendPostRequest(code, "exitstudy");
    }

    private void sendPostRequest(final String code, final String action) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, null, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("code", code);
                params.put("action", action);
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);
    }
}
