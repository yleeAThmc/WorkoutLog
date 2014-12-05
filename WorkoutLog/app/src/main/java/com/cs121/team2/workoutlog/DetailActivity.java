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
        super.onCreate(savedInstanceState);

        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_detail);

        // Enable the "Up" button for more navigation options
        if(getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(false);
        }

        //get the datahandler
        _dhInstance = DataHandler.getDataHandler(this);

        // Access the linearlayout from XML
        LinearLayout detailView = (LinearLayout) findViewById(R.id.scrollLinearLayout);

        //access the textview from XML
        TextView textView = (TextView) findViewById(R.id.detail_page);

        //access the intent from WOLogListActivity
        Intent i = getIntent();
        myLog = i.getParcelableExtra("log");

        //source string with HTML formatting tags for setText()
        String sourceString = myLog.toStringDetail();
        //set the text for the TextView
        textView.setText(Html.fromHtml(sourceString));
        setBackgroundColor(textView, myLog);

        //get the edit log button
        Button editButton = (Button) findViewById(R.id.editButton);
        //get the delete log button
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
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

    private void setBackgroundColor(View view, WOLog wl) {
        if (wl.getMood().equals(WOLog.MOOD_ARRAY[0])) {
            view.setBackgroundResource(R.drawable.list_awful);
        } else if (wl.getMood().equals(WOLog.MOOD_ARRAY[1])) {
            view.setBackgroundResource(R.drawable.list_bad);
        } else if (wl.getMood().equals(WOLog.MOOD_ARRAY[2])) {
            view.setBackgroundResource(R.drawable.list_ok);
        } else if (wl.getMood().equals(WOLog.MOOD_ARRAY[3])) {
            view.setBackgroundResource(R.drawable.list_good);
        } else {
            view.setBackgroundResource(R.drawable.list_perfect);
        }
    }

    public void onDeleteClick(View view) throws IOException { //called from the view, handles deletion clicks
        _dhInstance.editLog(myLog, myLog, true); //call editLog with delete = true
        //make a toast to confirm the deletion
        Toast myToast = Toast.makeText(this, "Log deleted.", Toast.LENGTH_SHORT);
        Intent myIntent = new Intent(DetailActivity.this, WOLogListActivity.class);
        myToast.show();
        startActivity(myIntent);
    }

    public void onEditClick(View view) { //called from the view, handles editing clicks
        Intent myIntent = new Intent(DetailActivity.this, EntryActivity.class);
        myIntent.putExtra("toEdit", (android.os.Parcelable) myLog);
        startActivity(myIntent);
    }

    // Create a message handling object as an anonymous class.
    //private AdapterView.OnItemClickListener editClickedHandler = new AdapterView.OnItemClickListener() {
    //    public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
    //    }
    //};

}
