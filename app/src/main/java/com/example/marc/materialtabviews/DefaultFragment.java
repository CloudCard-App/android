package com.example.marc.materialtabviews;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marc on 152312.
 *
 * 1qEHY18VWKtrQPzBDdIYwq2DHuPtgleQ0BK-5-dcvcsI
 */
public class DefaultFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View returnedView = inflater.inflate(R.layout.default_fragment, container, false);
        return returnedView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
