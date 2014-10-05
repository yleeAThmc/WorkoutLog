package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class EntryActivity extends Activity {
    private final String TAG = "ENTRY ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

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
        if (id == R.id.submit_log_button) {
            Spinner workoutType = (Spinner) findViewById(R.id.workout_type);
            EditText dist = (EditText) findViewById(R.id.distance);

            String type = String.valueOf(workoutType.getSelectedItem());
            String distance = String.valueOf(dist.getText());

            Toast.makeText(this,
                    "type: " + type + " dist: " + distance,
                    Toast.LENGTH_SHORT).show();
            Log.e(TAG, "type: " + type + " dist: " + distance);
        }


        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnWorkoutSpinner() {
        Spinner type = (Spinner) findViewById(R.id.workout_type);
        type.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }



}
