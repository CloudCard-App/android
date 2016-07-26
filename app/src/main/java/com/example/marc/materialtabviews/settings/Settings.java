package com.example.marc.materialtabviews.settings;

import com.example.marc.materialtabviews.signin.User;

public class Settings {

    private User user;

    public Settings() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
