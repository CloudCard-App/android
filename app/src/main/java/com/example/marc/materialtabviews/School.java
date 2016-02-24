package com.example.marc.materialtabviews;

public class School {

    private String name;
    private String key;
    private String password;

    public School(String name, String key, String password) {
        this.name = name;
        this.key = key;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        return getName().equals(school.getName());

    }
}
