package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.app.FragmentManager;

public abstract class IntermediateFragment extends Fragment {

    private Fragment previous, next;

    public IntermediateFragment() {
        previous = null;
        next = null;
    }

    public IntermediateFragment(Fragment previous, Fragment next) {
        this.previous = previous;
        this.next = next;
    }

    public void setPrevious(Fragment previous) {
        this.previous = previous;
    }

    public void setNext(Fragment next) {
        this.next = next;
    }

    void gotoPrevious() {
        swapFragment(previous);
    }

    void gotoNext() {
        swapFragment(next);
    }

    private void swapFragment(Fragment newFragment) {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container, newFragment).commit();
    }
}
