package com.cs121.team2.workoutlog;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sam on 12/4/2014.
 */

//TODO: Sam J is responsible for this class;
    //DO REALLLLLY MINOR TESTING HERE. Honestly, just make sure things aren't show blank/
    //has same info as the log getting passed in, that sort of stuff.
    //ALSO: in setup there is a brand new wolog with random stuff I initialized; feel free to
    //change this if you need to


public class DetailActivityTest extends
        ActivityInstrumentationTestCase2 {

    //private data members
    private WOLog myLog;
    private TextView myText;
    private Button deleteButton;
    private Button editButton;
    DetailActivity mActivity;


    //constructor
    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    //setup
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = (DetailActivity) getActivity();
        myLog = new WOLog();
        myLog.setDate(11, 12, 04, 12, 12);
        myLog.setType("Cardio");
        myLog.setMood("k");
        myText = (TextView) mActivity.findViewById(R.id.detail_page);
        String sourceString = myLog.toStringDetail();
        //set the text for the TextView
        myText.setText(Html.fromHtml(sourceString));
        deleteButton = (Button) mActivity.findViewById(R.id.deleteButton);
        editButton = (Button) mActivity.findViewById(R.id.editButton);
    }


}
