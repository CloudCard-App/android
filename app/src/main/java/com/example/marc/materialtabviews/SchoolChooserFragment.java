package com.example.marc.materialtabviews;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by marc on 152012.
 */
public class SchoolChooserFragment extends ListFragment implements OnTaskCompleted {

    ArrayList<School> schoolList = new ArrayList<>();
    ArrayList<String> schoolNameList = new ArrayList<>();

    public SchoolChooserFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Removes the view below it, such that this view does not appear on top of the previous one.
        if (container != null) {
            container.removeAllViews();
        }

        //Inflate the card_quiz_fragment inside container
        //This is very important to call.
        View view = inflater.inflate(R.layout.school_chooser_fragment, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set the adapter of the view to the schoolNameList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, schoolNameList);

        //This is the key to the lookup spreadsheet.
        //It contains school names and their corresponding keys.
//        String key = "1ZSFSttwa975pKyCEyEehshjSkcbfb2EomaVg1PF5eds";
        String key = "1Zs0ydpL1twVTgUNi_h9b4KHRafrTMnUljOorwdfBm8I";

        //This is the file where we will save the JSON of the lookup spreadsheet.
        String fileName = "schools";

        //this is used for the OnTaskCompleted interface
        //When it completes everything, it calls onTaskCompleted
        //And passes the arrayList of school as data.
        SchoolChooserDownloader downloader = new SchoolChooserDownloader(fileName, this, key);
        downloader.execute();

        setListAdapter(adapter);
    }

    /**
     * Woot! We have a click!
     * I don't think l, v, or position matter much right now.
     *
     * @param l        ListView where the click happened
     * @param v        View that was clicked within the ListView
     * @param position Position of the view in the list
     * @param id       Row id of the clicked item
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("SchoolChooserFragment.onListItemClick");
        School selection = schoolList.get((int) id); //Gets the clicked place.
        String key = selection.getKey(); //Gets the key of the clicked place.
        String password = selection.getPassword();


    }

    @Override
    /**
     * We assume that the ArrayList<Object> that we get
     * is of type school, for purposes of reusability of the
     * OnTaskCompleted interface.
     *
     * @param data The result of the method that calls onTaskCompleted
     */
    public void onTaskCompleted(ArrayList<Object> data) {

        //Populate the schoolNameList array with names of the schools.
        //We still will need the schoolList itself for the lookup
        //of names and keys inside onListItemClick.
        for (Object each : data) {
            schoolList.add((School) each);
            schoolNameList.add(((School) each).getName());
        }

        //Because we like our users.
        Collections.sort(schoolNameList);

        //Create a new adapter with schoolNameList.
        //TODO Maybe look into casting into a BaseAdapter, and then calling something like
        //TODO notifyDataSetChanged().
        try {
            ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, schoolNameList);
            //Set the adapter again.
            setListAdapter(newAdapter);
        } catch (NullPointerException npe) {
            Log.d("schoolChooserFragment", "No data received from Schooldownloader.");
            npe.printStackTrace();
        }
    }
}
