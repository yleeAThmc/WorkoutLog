package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

import java.io.IOException;


public class EntryActivity extends Activity {
    private final String TAG = "ENTRY ACTIVITY";
    DataHandler _dhInstance;

    private DatePicker _date;
    private TimePicker _time;
    private SeekBar _mood;
    private AutoCompleteTextView _actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initial choice: Cardio/Strength/Custom
        setContentView(R.layout.entry_initial_choice);

        // hide icon and title on action bar
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        // Getting the data handler instance
        _dhInstance = DataHandler.getDataHandler(this);
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

        return super.onOptionsItemSelected(item);
    }

    private void startWOLogListAct() {
        Intent newEntryIntent = new Intent(this, WOLogListActivity.class);
        startActivity(newEntryIntent);
    }


    public void getCardioView(View view) {setContentView(R.layout.entry_second_choice_cardio);}

    public void getStrengthView(View view) {setContentView(R.layout.entry_second_choice_strength);}

    public void getTimeCardioView(View view) {
        setCardioSubview(view);
        setActvArray(R.array.time_cardio_array);
    }

    public void getDistCardioView(View view) {
        setCardioSubview(view);
        setActvArray(R.array.dist_cardio_array);
    }

    public void getWeightStrengthView(View view) {
        setStrengthSubview(view);
        setActvArray(R.array.weights_strength_array);
    }

    public void getBodyStrengthView(View view) {
        setStrengthSubview(view);
        setActvArray(R.array.body_strength_array);
    }

    public void getCustomWorkoutView(View view) {
        setContentView(R.layout.entry_custom_workout);
    }

    private void setCardioSubview(View view) {
        setContentView(R.layout.entry_cardio);
        _date = (DatePicker) findViewById(R.id.cardio_date);
        _time = (TimePicker) findViewById(R.id.cardio_time);
        _time.setIs24HourView(true);
        _actv = (AutoCompleteTextView) findViewById(R.id.cardio_actv);

    }

    private void setStrengthSubview(View view) {
        setContentView(R.layout.entry_strength);
        _date = (DatePicker) findViewById(R.id.strength_date);
        _time = (TimePicker) findViewById(R.id.strength_time);
        _time.setIs24HourView(true);
        _actv = (AutoCompleteTextView) findViewById(R.id.strength_actv);
    }

    private void setActvArray(int arrayId) {
        String[] exercises = getResources().getStringArray(arrayId);
        ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,exercises);
        _actv.setAdapter(adapter);
        _actv.requestFocus();
        _actv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "integer is "+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        _actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _actv.showDropDown();
            }
        });
    }

    public void getInitialChoiceView(View view) {
        setContentView(R.layout.entry_initial_choice);
    }

    public void onCardioSubmit(View view) {

    }

    public void onStrengthSubmit(View view) {
        EditText sets = (EditText) findViewById(R.id.strength_sets);
        EditText reps = (EditText) findViewById(R.id.strength_reps);
        EditText weight = (EditText) findViewById(R.id.strength_weight);
        Spinner unit = (Spinner) findViewById(R.id.strength_weight_unit);
        _mood = (SeekBar) findViewById(R.id.strength_mood);
        WOLog wl = new WOLog();
        wl.setSets(String.valueOf(sets.getText()));
        wl.setReps(String.valueOf(reps.getText()));
        wl.setWeight(String.valueOf(weight.getText()));
        wl.setMood(WOLog.MOOD_ARRAY[_mood.getProgress()]);
        onSubmit(wl);
    }

    public void onCustomSubmit(View view) {

    }

    private void onSubmit(WOLog woLog) {
        int woMonth = _date.getMonth() + 1; // Jan == 0
        int woDay = _date.getDayOfMonth(); // day 1 == 1
        int woYear = _date.getYear();
        int woHour = _time.getCurrentHour(); // midnight == 0
        int woMinute = _time.getCurrentMinute(); // minute 0 == 0
        woLog.setType(String.valueOf(_actv.getText()));
        woLog.setDate(woMonth, woDay, woYear, woHour, woMinute);

            /*

            EditText dist = (EditText) findViewById(R.id.entry_dist);
            Spinner dist_unit = (Spinner) findViewById(R.id.entry_dist_unit);
            EditText mood = (EditText) findViewById(R.id.entry_mood);

            // find values
            String woType = String.valueOf(type.getSelectedItem());



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

            WOLog log = new WOLog();
            log.setType(woType);
            log.setDate(woMonth, woDay, woYear, woHour, woMinute);
            log.setDistance(woDist + " " + dist_unit.getSelectedItem().toString());
            log.setMood(woMood);
            try {
                _dhInstance.addLog(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
        */
        try {
            Log.e(TAG, "HERE!");
            _dhInstance.addLog(woLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startWOLogListAct();

    }
}
