package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Sam Jackson on 10/29/14.
 */
public class DetailActivity extends Activity {
    private WOLog myLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_detail);

        // Enable the "Up" button for more navigation options
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        // Access the textview from XML
        TextView textView = (TextView) findViewById(R.id.detail_page);

        //access the intent from WOLogListActivity
        Intent i = getIntent();
        myLog = (WOLog) i.getParcelableExtra("log");

        //set the text for the TextView
        textView.setText(myLog.toString());
    }
}
