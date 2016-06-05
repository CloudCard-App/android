package com.example.marc.materialtabviews.signin;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SignInManager {

    private static SignInManager manager;

    private boolean signedIn;
    private GoogleSignInAccount userAccount;

    {
        setSignedIn(false);
        userAccount = null;
    }

    public static SignInManager getManager() {
        if (manager == null) {
            manager = new SignInManager();
        }
        return manager;
    }

    public FragmentActivity getSignInFragment() {
        return new SigninFragment();
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;

        if (!signedIn) {
            // User account is false now.
            setUserAccount(null);
        }
    }

    public void setUserAccount(GoogleSignInAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userAccount.getDisplayName();
    }

    public String getUserEmail() {
        return userAccount.getEmail();
    }

    public Uri getUserPhotoURI() {
        return userAccount.getPhotoUrl();
    }

    public boolean isUserSignedIn() {
        return signedIn;
    }

}
