package com.example.marc.materialtabviews;

/**
 * Created by marc on 152712.
 */
public class Deck implements Comparable<Deck> {

    private String name = "";
    private String key = "";

    public Deck(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    @Override
    public int compareTo(Deck another) {
        return this.getName().substring(0, 1).compareTo(another.getName().substring(0, 1));
    }
}
