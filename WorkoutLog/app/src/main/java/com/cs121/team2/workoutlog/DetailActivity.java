package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Sam Jackson on 10/29/14.
 */
public class DetailActivity extends Activity {
    private WOLog myLog;
    DataHandler _dhInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _dhInstance = DataHandler.getDataHandler(this);
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_detail);

        // Enable the "Up" button for more navigation options
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        // Access the linearlayout from XML
        LinearLayout detailView = (LinearLayout) findViewById(R.id.scrollLinearLayout);

        //access the textview from XML
        TextView textView = (TextView) findViewById(R.id.detail_page);

        //access the intent from WOLogListActivity
        Intent i = getIntent();
        myLog = (WOLog) i.getParcelableExtra("log");

        //source string with HTML formatting tags for setText()
        String sourceString = myLog.toStringHTML();
        //set the text for the TextView
        textView.setText(Html.fromHtml(sourceString));

        //get the edit log button
        Button editButton = (Button) findViewById(R.id.editButton);
        //get the delete log button
        //push calls for this button go straight through the layout file to onDeleteClick
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        //get the listener for the edit log button
        detailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DetailActivity.this, EntryActivity.class);
                myIntent.putExtra("toEdit", (android.os.Parcelable) myLog);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
        if (id == R.id.view_list_button) {
            Intent newEntryIntent = new Intent(this, WOLogListActivity.class);
            startActivity(newEntryIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO: what to do if myLog is void? Shouldn't be, couldn't be, but still safer
    public void onDeleteClick(View view) throws IOException {
        _dhInstance.editLog(myLog, myLog, true); //call editLog with delete = true
        //make a toast to confirm the deletion
        Toast myToast = Toast.makeText(this, "Log deleted.", Toast.LENGTH_SHORT);
        Intent myIntent = new Intent(DetailActivity.this, WOLogListActivity.class);
        myToast.show();
        startActivity(myIntent);
    }

    // Create a message handling object as an anonymous class.
    //private AdapterView.OnItemClickListener editClickedHandler = new AdapterView.OnItemClickListener() {
    //    public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
    //    }
    //};

}
