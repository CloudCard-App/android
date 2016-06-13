package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.marc.materialtabviews.signin.SignInManager;
import com.example.marc.materialtabviews.your_decks.YourDecksChooserFragment;
import com.instabug.library.Instabug;
import com.instabug.wrapper.support.activity.InstabugAppCompatActivity;

public class MainActivity extends InstabugAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    private static final String TAG = "MainActivity";

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
//                super.onDrawerOpened(drawerView);

                //Do stuff

                Log.v(TAG, "OnDrawerOpened!");

                if (!SignInManager.getManager().isUserSignedIn()) {
                    Log.i(TAG, "Asking for sign in");
                    drawer.closeDrawer(GravityCompat.START);

                    Intent signInIntent = new Intent(getBaseContext(),
                            com.example.marc.materialtabviews.signin.SigninFragment.class);
                    startActivity(signInIntent);
                }
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUserInfo() {
//        if (SignInManager.getManager().isUserSignedIn()) {

//        // Notice how these 4 lines all amazingly line up perfectly at 86 characters!
//        CoordinatorLayout appBarMain = (CoordinatorLayout) findViewById(R.id.appBar);
//        RelativeLayout contentMain = (RelativeLayout) findViewById(R.id.contentMain);
//        LinearLayout navHeaderMain = (LinearLayout) findViewById(R.id.navHeaderMain);

        ImageView profileImage = (ImageView) findViewById(R.id.profilePic);
        Uri userPhotoURI = SignInManager.getManager().getUserPhotoURI();
        Log.i(TAG, "User photo URI = " + userPhotoURI);
        profileImage.setImageURI(userPhotoURI);

        TextView studentName = (TextView) findViewById(R.id.studentName);
        String userName = SignInManager.getManager().getUserName();
        Log.i(TAG, "User name = " + userName);
        studentName.setText(userName);

        TextView studentEmail = (TextView) findViewById(R.id.studentEmail);
        String userEmail = SignInManager.getManager().getUserEmail();
        Log.i(TAG, "User email = " + userEmail);
        studentEmail.setText(userEmail);
//        }
    }

    @Override
    /**
     * When the back button is pressed
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
        if (SignInManager.getManager().isUserSignedIn()) {
            Log.i(TAG, "User signed in already");
            // Handle navigation view item clicks here.
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
        } else {

        }
        setUserInfo();

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
