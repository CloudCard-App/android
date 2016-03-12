package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.app.FragmentManager;

public abstract class IntermediateFragment extends Fragment {

    /**
    * The fragment previous to this and the fragment that will go after this.
    * We don't worry about the fragment after, since that may also be an
    * IntermediateFragment and will have its own previous and next.
    * It is important that any following IntermediateFragment has this object
    * as its previous fragment.
    */
    private Fragment previous, next;

    /**
    * Default constructor.
    * @see: setPrevious(Fragment previous)
    * @see: setNext(Fragment next)
    */
    public IntermediateFragment() {
        previous = null;
        next = null;
    }

    /**
    * Creates a new IntermediateFragment with a previous and next
    * already defined.
    */
    public IntermediateFragment(Fragment previous, Fragment next) {
        this.previous = previous;
        this.next = next;
    }

    /**
    * Sets the previous fragment.
    */
    public void setPrevious(Fragment previous) {
        this.previous = previous;
    }

    /**
    * Sets the next fragment.
    */
    public void setNext(Fragment next) {
        this.next = next;
    }

    /**
    * Switches to the previous fragment by using the fragment manager
    * to replace the current container with the previous fragment.
    */
    void gotoPrevious() {
        swapFragment(previous);
    }

    /**
    * Switches to the next fragment.
    * @see: gotoPrevious()
    */
    void gotoNext() {
        swapFragment(next);
    }

    /**
    * Internal method used to swap fragments inside the container.
    */
    private void swapFragment(Fragment newFragment) {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.container, newFragment).commit();
    }
}
