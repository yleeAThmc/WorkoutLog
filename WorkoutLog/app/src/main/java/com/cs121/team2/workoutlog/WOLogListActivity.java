package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.IOException;

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListActivity extends Activity{

    private final String TAG = "WOLOGLIST ACTIVITY";
    ListView wologlistListView;
    Button statsButton;
    WOLogListAdapter mWOLogListAdapter;
    private WOLog toSendAlong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setting content and look
        setContentView(R.layout.activity_wologlist);
        if(getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(false);
        }


        //Access the ListView
        wologlistListView = (ListView) findViewById(R.id.wologlist_listview);

        //Create a LogListAdapter for the ListView
        DataHandler dhInstance = DataHandler.getDataHandler(this);
        try {
            mWOLogListAdapter = new WOLogListAdapter( this,dhInstance.getLogs());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the ListView to use the ArrayAdapter
        wologlistListView.setAdapter(mWOLogListAdapter);
        // Set the ListView to use a single choice mode for list item selection
        wologlistListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // Set the ListView to have an OnItemClickListener so it can take in selections
        wologlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                toSendAlong = (WOLog) parent.getItemAtPosition(position);
                // create an Intent to take you over to a new DetailActivity
                Intent detailIntent;
                detailIntent = new Intent(view.getContext(), DetailActivity.class);

                // pack away the data about the WOLog into your Intent before you head out
                detailIntent.putExtra("log", toSendAlong);

                // start the next Activity using your prepared Intent
                startActivity(detailIntent);
            }
        });

        //access the Stats Button
        statsButton = (Button) findViewById(R.id.stats_button);

        //handle click of Stats Button
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent statsIntent = new Intent(v.getContext(),StatsActivity.class);
                startActivity(statsIntent);
            }
        });
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
