package com.example.marc.materialtabviews;

import android.support.annotation.NonNull;

public class Deck implements Comparable<Deck> {

    private String name = "";
    private String key = "";
    private String code = "";

    public Deck() {
    }

    public Deck(String name, String key, String code) {
        this.name = name;
        this.key = key;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Deck another) {
        return this.getName().substring(0, 1).compareTo(another.getName().substring(0, 1));
    }
}
