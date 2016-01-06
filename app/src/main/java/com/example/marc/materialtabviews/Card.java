package com.example.marc.materialtabviews;

/**
 * Created by marc on 152812.
 */
public class Card extends Object {

    private String front = "";
    private String back = "";

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    @Override
    public String toString() {
        return "Card{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                '}';
    }
}
