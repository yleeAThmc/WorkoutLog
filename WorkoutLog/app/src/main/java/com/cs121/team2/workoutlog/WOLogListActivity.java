package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.cs121.team2.workoutlog.DataHandler;

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListActivity extends Activity {

    private final String TAG = "WOLOGLIST ACTIVITY";
    ListView wologlistListView;
    WOLogListAdapter mWOLogListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setting content and look
        setContentView(R.layout.activity_wologlist);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        //Access the ListView
        wologlistListView = (ListView) findViewById(R.id.wologlist_listview);

        //Create a LogListAdapter for the ListView
        DataHandler dhInstance = DataHandler.getDataHandler();
        mWOLogListAdapter = new WOLogListAdapter(this,R.layout.row_wolog, dhInstance.getLogs());

        // Set the ListView to use the ArrayAdapter
        wologlistListView.setAdapter( mWOLogListAdapter);

        //TODO: get sorting working and then sort, reset adapter here

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
