package com.example.marc.materialtabviews;

import java.util.List;

/**
 * Created by marc on 160220.
 */
public class DeckWithContents extends Deck {

    private List<Card> cards = null;

    public DeckWithContents(String name, String key, String code, List<Card> cards) {
        super(name, key, code);
        this.cards = cards;
    }

    public DeckWithContents() {
        super("", "", "");
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
