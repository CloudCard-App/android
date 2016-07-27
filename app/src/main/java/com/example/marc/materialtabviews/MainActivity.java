package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marc.materialtabviews.deck_chooser.DeckChooserFragment;
import com.example.marc.materialtabviews.misc_fragments.DefaultFragment;
import com.example.marc.materialtabviews.notifications.ComingSoon;
import com.example.marc.materialtabviews.signin.DownloadImageTask;
import com.example.marc.materialtabviews.signin.User;
import com.example.marc.materialtabviews.your_decks.YourDecksChooserFragment;
import com.instabug.library.Instabug;
import com.instabug.wrapper.support.activity.InstabugAppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends InstabugAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private static final String TAG = "MainActivity";
    private NavigationView navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Instabug for bug reporting
        String token = "27c942385b445c742b5dbf1afaacfc5e";
        Instabug.initialize(this.getApplication(), token);

        super.onCreate(savedInstanceState); // Don't forget this.

        // TODO: Make sure this is actually working!
        // Keeps it in portrait. Maybe implement turning later.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // This is the XML layout for the main activity.
        setContentView(R.layout.activity_main);

        // Top toolbar with the three lines.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Navigation drawer setup.
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                // Refresh the user name, email, and picture every time the drawer is opened
                updateNavHeader();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_USER, MODE_PRIVATE);
        if (sharedPrefs.getString(User.USER_GID, null) == null) {
            Log.v(TAG, "User not signed in: launching sign in activity");
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }

        sharedPrefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.
                OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.v(TAG, "Shared Preference changed -> Updating nav header!");
                updateNavHeader();
            }
        });

        navHeader = (NavigationView) findViewById(R.id.nav_view);
        navHeader.setNavigationItemSelectedListener(this);
    }

    /**
     * Updates the user profile picture, name, and email that appears in the drawer.
     */
    private void updateNavHeader() {
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_USER, MODE_PRIVATE);

        ImageView userImage = (ImageView) findViewById(R.id.userPhoto);
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userEmail = (TextView) findViewById(R.id.userEmail);

        String photoString = sharedPrefs.getString(User.USER_PHOTO, null);
        Log.v(TAG, "User photo string = " + photoString);
        String nameString = sharedPrefs.getString(User.USER_NAME, null);
        String emailString = sharedPrefs.getString(User.USER_EMAIL, null);

        if (photoString != null && nameString != null && emailString != null) {
//            Bitmap profileBitmap;
//
//            try {
//                URL url = new URL(photoString);
//                profileBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                userImage.setImageBitmap(profileBitmap);
//            } catch (Exception e) {
//                Log.v(TAG, "Exception: " + e.getMessage());
//                e.printStackTrace();
//                userImage.setImageResource(R.drawable.ic_person_head);
//            }

            new DownloadImageTask(userImage, R.drawable.ic_person_head).execute(photoString);

            userName.setText(nameString);
            userEmail.setText(emailString);
        }
    }

    @Override
    /**
     * When the back button is pressed close the drawer
     */
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i(TAG, "Navigation Item Selected!");
        int id = item.getItemId();

        // For AppCompat use getSupportFragmentManager
        Fragment fragment = new DefaultFragment();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_deckfinder) {
            fragment = new DeckChooserFragment();
        } else if (id == R.id.nav_yourdecks) {
            fragment = new YourDecksChooserFragment();
        } else if (id == R.id.nav_messages) {
            fragment = new ComingSoon();
        } else if (id == R.id.nav_notifications) {
            fragment = new ComingSoon();
        } else if (id == R.id.nav_settings) {
            fragment = new ComingSoon();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.navHeaderMain, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true; // Why?
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
