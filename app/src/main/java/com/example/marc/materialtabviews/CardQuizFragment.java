package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marc on 152812.
 */
public class CardQuizFragment extends Fragment implements OnTaskCompleted {

    private TextView cardDisplay;
    private TextView numDisplay;
    private ImageView cardBackground;
    private SeekBar progressSelect;
    private ImageButton backButton;
    private ImageButton nextButton;
    private String key;
    private int currentIndex = -1; // It doesn't have any index yet, before it starts.
    private ArrayList<Card> cardData;
    private int totalLength;


    public CardQuizFragment() {
        this.key = "";
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Begins the inflation of card_quiz_fragment.xml
     *
     * @return View that is used in onViewCreated
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Removes the view below it, such that this view does not appear on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the card_quiz_fragment inside container
        // This is very important to call.
        View view = inflater.inflate(R.layout.card_quiz_fragment, container, false);

        // Now that it's inflated, start recording the actions.
        Log.i("CardQuizFragment", "Started viewing card!");

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i("cardQuizFragment", "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("cardQuizFragment", "Right to Left");
                                nextCard();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("cardQuizFragment", "Left to Right");
                                previousCard();
                            } else {
                                Log.i("cardQuizFragment", "Tippedy-tapped!");
                                flipCard();
                            }
                        } catch (Exception e) {
                            // nothing
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
        numDisplay = (TextView) getView().findViewById(R.id.currentNumDisplay);
        cardBackground = (ImageView) getView().findViewById(R.id.cardBackground);

        progressSelect = (SeekBar) getView().findViewById(R.id.progressSelect);

        nextButton = (ImageButton) getView().findViewById(R.id.nextButton);
        backButton = (ImageButton) getView().findViewById(R.id.backButton);

        // Create the downloader, passing in this as the OnTaskCompleted listener
        CardQuizDownloader downloader = new CardQuizDownloader(key, "cardtmp", this);

        // Do in background stuffs.
        downloader.execute();
    }

    /**
     * Is called by CardQuizDownloader when it finishes everything.
     *
     * @param data An ArrayList of Cards, disguised as Object.
     */
    @Override
    public void onTaskCompleted(ArrayList<Object> data) {

        cardDisplay.setText("Swipe left to start!");

        cardData = new ArrayList<>();
        for (Object each : data) {
            cardData.add((Card) each);
        }

        totalLength = cardData.size();

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
        System.out.println("CardQuizFragment.previousCard");
        if (currentIndex > 0) { // If we're not at the beginning
            currentIndex--;
            updateCard(true);
        } else {
            // Figure something out here later
        }
    }

    private void flipCard() {
        System.out.println("CardQuizFragment.flipCard");
        if (cardDisplay.getText().equals(cardData.get(currentIndex).getFront())) { // Switch to back
            updateCard(false);
        } else if (cardDisplay.getText().equals(cardData.get(currentIndex).getBack())) { // Switch to front
            updateCard(true);
        } else {
            // Oh no.
            // Compiler, handle this error, please.
        }
        // Sometimes, I feel that the compiler doesn't read my comments.

        numDisplay.setText(String.valueOf(currentIndex + 1) + " / " + totalLength);
    }

    private void nextCard() {
        System.out.println("CardQuizFragment.nextCard");
        if (currentIndex < cardData.size() - 1) { // If we're not at the end
            currentIndex++;
            updateCard(true);
        } else {
            // Figure something out here later.
        }
    }

    private void updateCard(boolean front) {
        if (front) {
            cardDisplay.setText(cardData.get(currentIndex).getFront());
            cardBackground.setImageResource(R.mipmap.blue_card); // R.id.something
        } else {
            cardDisplay.setText(cardData.get(currentIndex).getBack());
            cardBackground.setImageResource(R.mipmap.yellow_card); // R.id.something
        }
        numDisplay.setText(String.valueOf(currentIndex + 1) + " / " + totalLength);
        progressSelect.setProgress(currentIndex); // Wee-Wee!
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void onStop() {
        // Record that the user has left.
        Log.i("CardQuizFragment", "Stopped!");
        super.onStop();
    }
}
