package com.cs121.team2.workoutlog;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.RenamingDelegatingContext;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Sam on 12/4/2014.
 */


//TODO: Sam E is responsible for this class

public class WOLogListActivityTest extends ActivityInstrumentationTestCase2 {

    //private data members
    private WOLogListActivity mActivity;
    private WOLogListAdapter mAdapter;
    private ListView listView;
    private ArrayList<WOLog> logs;
    private Spinner timePicker;
    private Spinner typePicker;
    String[] keywordsTimePicker = {"All Time", "Today", "Last Week", "Last 2 Weeks",
            "Last Month", "Last 6 Months", };
    String[] keywordsTypePicker = {"All Workouts", "Cardio", "Strength", "Custom" };

    public WOLogListActivityTest() {
        super(WOLogListActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();

        mActivity = (WOLogListActivity) getActivity();
        listView = (ListView) mActivity.findViewById(R.id.wologlist_listview);
        WOLog log1 = new WOLog();
        WOLog log2 = new WOLog();
        WOLog log3 = new WOLog();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        RenamingDelegatingContext ContextToUse = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test");
        mAdapter = new WOLogListAdapter(new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test"), logs);
        listView.setAdapter(mAdapter);
        ArrayAdapter<String> tpValues =
                new ArrayAdapter<String>(getInstrumentation().getTargetContext(), android.R.layout.simple_spinner_item, keywordsTimePicker);
        tpValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timePicker.setAdapter(tpValues);
        ArrayAdapter<String> tyValues =
                new ArrayAdapter<String>(getInstrumentation().getTargetContext(), android.R.layout.simple_spinner_item, keywordsTypePicker);
        tyValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typePicker.setAdapter(tyValues);
    }

}
