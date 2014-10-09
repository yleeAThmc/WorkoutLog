package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


public class EntryActivity extends Activity {
    private final String TAG = "ENTRY ACTIVITY";
    DatePicker _date;
    TimePicker _time;
    DataHandler _dhInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // hide icon and title on action bar
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        // Getting the data handler instance
        _dhInstance = DataHandler.getDataHandler();

        // setting up date/time picker
        _date = (DatePicker) findViewById(R.id.entry_date);
        _time = (TimePicker) findViewById(R.id.entry_time);
        _time.setIs24HourView(true);



        // For future use
        addListenerOnWorkoutSpinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry, menu);
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
        if (id == R.id.view_list_button) {
            Intent newEntryIntent = new Intent(this, WOLogListActivity.class);
            startActivity(newEntryIntent);
        }
        if (id == R.id.submit_log_button) {
            Spinner type = (Spinner) findViewById(R.id.entry_type);

            EditText dist = (EditText) findViewById(R.id.entry_dist);
            Spinner dist_unit = (Spinner) findViewById(R.id.entry_dist_unit);
            EditText mood = (EditText) findViewById(R.id.entry_mood);

            // find values
            String woType = String.valueOf(type.getSelectedItem());

            String woDate = String.valueOf(_date.getMonth()); // Jan == 0
            woDate.concat(String.valueOf("/"+_date.getDayOfMonth())); // day 1 == 0
            String woTime = String.valueOf(_time.getCurrentHour()); // midnight == 0
            woTime.concat(String.valueOf(":"+_time.getCurrentMinute())); // minute 0 == 0

            // no distance entered --> give prompt
            String woDist = String.valueOf(dist.getText());
            if (woDist.isEmpty()) {
                Toast.makeText(this, "Enter distance", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            woDist.concat(String.valueOf(dist_unit.getSelectedItem()));

            // no mood entered --> give prompt
            String woMood = String.valueOf(mood.getText());
            if (woMood.length() == 0) {
                Toast.makeText(this, "Enter mood", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            WOLog log = new WOLog(woType, woDate, woTime, woDist, woMood);
            _dhInstance.addLog(log);

            startWOLogListAct();

            Toast.makeText(this,
                    log.toString(),
                    Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void startWOLogListAct() {
        Intent newEntryIntent = new Intent(this, WOLogListActivity.class);
        startActivity(newEntryIntent);
    }

    public void addListenerOnWorkoutSpinner() {
        Spinner type = (Spinner) findViewById(R.id.entry_type);
        type.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

}
