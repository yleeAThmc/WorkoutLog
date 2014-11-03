package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wologlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_WOlog) {

            Intent newEntryIntent = new Intent(this, EntryActivity.class);
            // start the next Activity the prepared Intent
            startActivity(newEntryIntent);

        }

        return super.onOptionsItemSelected(item);
    }
}
