package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.IOException;


public class EntryActivity extends Activity {
    private final String TAG = "ENTRY ACTIVITY";
    DataHandler _dhInstance;
    WOLog _wl;
    private int _type;
    private int _subType;

    // common data
    private DatePicker _date;
    private TimePicker _time;
    private SeekBar _mood;
    private EditText _memo;

    private AutoCompleteTextView _actv;

    private WOLog toEdit; //the WOLog that we might be editing from Detail activity
    private boolean editing = false; //are we editing? Assume we are not
    private int[] toEditDate = new int[5]; //the array that the split date string will be stored in from toEdit
    private String[] toEditTime = new String[3];

    // specific to custom wo
    private Boolean _distEnabled = true;
    private Boolean _durEnabled = true;
    private Boolean _wgtEnabled = true;
    private Boolean _setrepEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get the WOLog to be edited IF the user is trying to edit from Detail view
        Intent i = getIntent();
        if (i.getExtras() != null) { //are the extras in this intent null? If not, we're editing
            toEdit = (WOLog) i.getParcelableExtra("toEdit");
            editing = true;
            if (toEdit.getTime() != null && !toEdit.getTime().isEmpty()) { //do we have a time?
                //if so, create a string array and parse the ints from that string array of [h,m,s]
                toEditTime = toEdit.getTime().split(":");
                //for(int x = 0; x < toEditTime.length; x++) {
                    //Log.d("toEdit Time","Time: " + toEditTime[x]);
                //}
            }
            String[] toEditDateString = toEdit.getDate().split("\\W"); //split the date string
            //make sure date's time is not a whole hour so minutes != "" after taking out 0s
            if (!(toEditDateString[4].equals("00"))) {
                //next line: make sure minutes has no leading 0s
                toEditDateString[4] = toEditDateString[4].replaceAll("^0+", "");
            }
            for(int j = 0; j < toEditDateString.length; j++) { //populate our date array
                toEditDate[j] = Integer.parseInt(toEditDateString[j]);
                //Log.d("toEdit custom fields","Weight:" + toEdit.getWeight() + ". Sets: " + toEdit.getSets() + ". Reps: " + toEdit.getReps() + ". Time: " + toEdit.getTime() + ". Distance: " + toEdit.getDistance() + ".");
                Log.d("toEdit memo: ","Here: " + toEdit.getMemo());
            }
        }

        super.onCreate(savedInstanceState);

        //set content based on whether we are editing or not
        if (!editing) { // If we're not editing, go to initial choice: Cardio/Strength/Custom
            setContentView(R.layout.entry_initial_choice);
        } else { //If we are editing, just go straight to the actual data entry for the correct type
            if(toEdit.getType().equals("Cardio")) { //toEdit is cardio?
                setContentView(R.layout.entry_second_choice_cardio);
            } else if(toEdit.getType().equals("Strength")) { //toEdit is strength?
                setContentView(R.layout.entry_second_choice_strength);
            } else if(toEdit.getType().equals("Custom")) {
                setContentView(R.layout.entry_initial_choice);
            }
        }

        // hide icon and title on action bar
        if(getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(false);
        }

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

    public void getCardioView(View view) {
        _type = WOLog.TYPE_CARDIO;
        setContentView(R.layout.entry_second_choice_cardio);
    }

    public void getStrengthView(View view) {
        _type = WOLog.TYPE_STRENGTH;
        setContentView(R.layout.entry_second_choice_strength);
    }

    public void getTimeCardioView(View view) {
        _subType = WOLog.SUBTYPE_TIME_BODY;
        getCommonDataView(view);
    }

    public void getDistCardioView(View view) {
        _subType = WOLog.SUBTYPE_DIST_WEIGHTS;
        getCommonDataView(view);
    }

    public void getWeightStrengthView(View view) {
        _subType = WOLog.SUBTYPE_DIST_WEIGHTS;
        getCommonDataView(view);
    }

    public void getBodyStrengthView(View view) {
        _subType = WOLog.SUBTYPE_TIME_BODY;
        getCommonDataView(view);
    }

    public void getCustomWorkoutView(View view) {
        _type = WOLog.TYPE_CUSTOM;
        _subType = WOLog.SUBTYPE_NONE;
        getCommonDataView(view);
    }

    public void getCommonDataView(View view) {
        setContentView(R.layout.entry_common_data);
        initializeDateAndTime();
        if (editing) { //if we're editing...
            //...pre-fill the seekbar depending on mood from toEdit...
            SeekBar seek = (SeekBar) findViewById(R.id.mood);
            if (toEdit.getMood().equals("awful")) {
                seek.setProgress(0);
            } else if (toEdit.getMood().equals("bad")) {
                seek.setProgress(1);
            } else if (toEdit.getMood().equals("k")) {
                seek.setProgress(2);
            } else if (toEdit.getMood().equals("good")) {
                seek.setProgress(3);
            } else if (toEdit.getMood().equals("perfect")) {
                seek.setProgress(4);
            }
            //...and pre-fill the date depending on date from toEdit in InitializeDateAndTime()
            //...and pre-fill the memo depending on memo from toEdit
            EditText memorandum = (EditText) findViewById(R.id.memo);
            memorandum.setText(toEdit.getMemo());
        }
    }

    public void goBackFromCommon(View view) {
        if (_type == WOLog.TYPE_CARDIO) {
            getCardioView(view);
        } else if (_type == WOLog.TYPE_STRENGTH) {
            getStrengthView(view);
        } else if (_type == WOLog.TYPE_CUSTOM) {
            getInitialChoiceView(view);
        }
    }

    public void continueFromCommon(View view) {
        _wl = new WOLog();
        setMood();
        setMemo();
        setCommonData();

        if (_type == WOLog.TYPE_CARDIO) {
            setContentView(R.layout.entry_cardio);
            _actv = (AutoCompleteTextView) findViewById(R.id.cardio_actv);
            EditText dist = (EditText) findViewById(R.id.cardio_dist);
            EditText hour = (EditText) findViewById(R.id.cardio_hour);
            EditText minute = (EditText) findViewById(R.id.cardio_minute);
            EditText second = (EditText) findViewById(R.id.cardio_second);
            if (_subType == WOLog.SUBTYPE_TIME_BODY) {
                setActvArray(R.array.time_cardio_array);
            } else {
                setActvArray(R.array.dist_cardio_array);
            }
            if(editing) { //are we editing?
                _actv.setText(toEdit.getName()); //if so, preset the name field
                dist.setText(toEdit.getDistance()); //also preset the distance field
                hour.setText(toEditTime[0]); //also preset all time fields...
                minute.setText(toEditTime[1]);
                second.setText(toEditTime[2]);
            }
        } else if (_type == WOLog.TYPE_STRENGTH) {
            setContentView(R.layout.entry_strength);
            _actv = (AutoCompleteTextView) findViewById(R.id.strength_actv);
            EditText weight = (EditText) findViewById(R.id.strength_weight);
            EditText sets = (EditText) findViewById(R.id.strength_sets);
            EditText reps = (EditText) findViewById(R.id.strength_reps);
            if (_subType == WOLog.SUBTYPE_TIME_BODY) {
                setActvArray(R.array.body_strength_array);
            } else {
                setActvArray(R.array.weights_strength_array);
            }
            if(editing) { //are we editing?
                _actv.setText(toEdit.getName()); //if so, preset the name field
                weight.setText(toEdit.getWeight());
                sets.setText(toEdit.getSets());
                reps.setText(toEdit.getReps());
            }
        } else if (_type == WOLog.TYPE_CUSTOM) {
            //TODO: need to have getCustomWorkoutView's chain of calls and initialization to skip to here, insert into this function
            setContentView(R.layout.entry_custom_workout);
            //get buttons for the deactivation stuff
            ImageButton distButton = (ImageButton) findViewById(R.id.custom_dist_btn);
            ImageButton timeButton = (ImageButton) findViewById(R.id.custom_dur_btn);
            ImageButton weightButton = (ImageButton) findViewById(R.id.custom_wgt_btn);
            ImageButton setRepButton = (ImageButton) findViewById(R.id.custom_setrep_btn);
            //get text fields
            EditText customDist = (EditText) findViewById(R.id.custom_dist);
            EditText customHour = (EditText) findViewById(R.id.custom_hour);
            EditText customMinute = (EditText) findViewById(R.id.custom_minute);
            EditText customWeight = (EditText) findViewById(R.id.custom_wgt);
            EditText customSets = (EditText) findViewById(R.id.custom_sets);
            EditText customReps = (EditText) findViewById(R.id.custom_reps);
            boolean distNull = false; //booleans = true if the field is null
            boolean timeNull = false;
            boolean weightNull = false;
            boolean setRepNull = false;
            if (editing) {
                EditText customName = (EditText) findViewById(R.id.custom_type);
                customName.setText(toEdit.getName());
                //deactivate fields if they are null in the saved log
                if (toEdit.getWeight() == null) {
                    weightButton.performClick();
                    weightNull = true;
                }
                if (toEdit.getDistance() == null) {
                    distButton.performClick();
                    distNull = true;
                }
                if (toEdit.getTime() == null) {
                    timeButton.performClick();
                    timeNull = true;
                }
                if (toEdit.getSets() == null || toEdit.getReps() == null) {
                    setRepButton.performClick();
                    setRepNull = true;
                }
                //fill active fields
                if (!distNull) {
                    customDist.setText(toEdit.getDistance());
                }
                if (!timeNull) {
                    customHour.setText(toEditTime[0]);
                    customMinute.setText(toEditTime[1]);
                }
                if (!weightNull) {
                    customWeight.setText(toEdit.getWeight());
                }
                if (!setRepNull) {
                    customSets.setText(toEdit.getSets());
                    customReps.setText(toEdit.getReps());
                }
            }
        }
    }

    private void initializeDateAndTime() {
        _date = (DatePicker) findViewById(R.id.date);
        _time = (TimePicker) findViewById(R.id.time);
        _time.setIs24HourView(true);
        if (editing) { //if we're editing, prefill the datepicker feilds
            _date.updateDate(toEditDate[2],toEditDate[0]-1,toEditDate[1]);
            _time.setCurrentHour(toEditDate[3]);
            _time.setCurrentMinute(toEditDate[4]);
        }
    }

    //TODO: should go to WOLOG???
    private void setCommonData() {
        int woMonth = _date.getMonth() + 1; // Jan == 0
        int woDay = _date.getDayOfMonth(); // day 1 == 1
        int woYear = _date.getYear();
        int woHour = _time.getCurrentHour(); // midnight == 0
        int woMinute = _time.getCurrentMinute(); // minute 0 == 0
        _wl.setDate(woMonth, woDay, woYear, woHour, woMinute);
        _wl.setMood(WOLog.MOOD_ARRAY[_mood.getProgress()]);
        _wl.setMemo(String.valueOf(_memo.getText()));
    }

    private void setMood() { _mood = (SeekBar) findViewById(R.id.mood);}

    private void setMemo() {
        _memo = (EditText) findViewById(R.id.memo);
    }

    public void getInitialChoiceView(View view) {
        setContentView(R.layout.entry_initial_choice);
    }

    public void onCardioSubmit(View view) {
        EditText dist = (EditText) findViewById(R.id.cardio_dist);
        EditText hour = (EditText) findViewById(R.id.cardio_hour);
        EditText minute = (EditText) findViewById(R.id.cardio_minute);
        EditText second = (EditText) findViewById(R.id.cardio_second);
        _wl.setDistance(String.valueOf(dist.getText()));
        _wl.setTime(String.valueOf(hour.getText()),
                String.valueOf(minute.getText()),String.valueOf(second.getText()));
        _wl.setName(String.valueOf(_actv.getText()));
        onSubmit();
    }

    public void onStrengthSubmit(View view) {
        EditText sets = (EditText) findViewById(R.id.strength_sets);
        EditText reps = (EditText) findViewById(R.id.strength_reps);
        EditText weight = (EditText) findViewById(R.id.strength_weight);
        _wl.setSets(String.valueOf(sets.getText()));
        _wl.setReps(String.valueOf(reps.getText()));
        _wl.setWeight(String.valueOf(weight.getText()));
        _wl.setName(String.valueOf(_actv.getText()));
        onSubmit();
    }

    public void onCustomSubmit(View view) {
        EditText type = (EditText) findViewById(R.id.custom_type);
        _wl.setName(String.valueOf(type.getText()));

        if (_distEnabled) {
            EditText distText = (EditText) findViewById(R.id.custom_dist);
            _wl.setDistance(String.valueOf(distText.getText()));
        }
        if (_durEnabled) {
            EditText hour = (EditText) findViewById(R.id.custom_hour);
            EditText minute = (EditText) findViewById(R.id.custom_minute);
            _wl.setTime(String.valueOf(hour.getText()),String.valueOf(minute.getText()),"");
        }
        if (_setrepEnabled) {
            EditText setText = (EditText) findViewById(R.id.custom_sets);
            EditText repText = (EditText) findViewById(R.id.custom_reps);
            _wl.setSets(String.valueOf(setText.getText()));
            _wl.setReps(String.valueOf(repText.getText()));
        }
        if (_wgtEnabled) {
            EditText wgtText = (EditText) findViewById(R.id.custom_wgt);
            _wl.setWeight(String.valueOf(wgtText.getText()));
        }
        onSubmit();
    }

    private void onSubmit() {
        _wl.setType(WOLog.TYPE_ARRAY[_type]);
        _wl.setSubtype(WOLog.SUBTYPE_ARRAY[_subType]);
        if(!editing) {
            try {
                _dhInstance.addLog(_wl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (editing) {
            try {
                _dhInstance.editLog(_wl,toEdit,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setContentView(R.layout.entry_initial_choice);
        startWOLogListAct();
    }
    public void onCSTDistBtnClicked(View view) {
        FrameLayout distBg = (FrameLayout) findViewById(R.id.custom_dist_bg);
        EditText dist = (EditText) findViewById(R.id.custom_dist);
        ImageButton distBtn = (ImageButton) findViewById(R.id.custom_dist_btn);
        if (_distEnabled) {
            // disable it
            distBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            dist.setFocusable(false);
            distBtn.setBackgroundResource(R.drawable.ic_action_done);
            _distEnabled = false;
        } else {
            // enable it
            distBg.setBackgroundResource(R.drawable.edittext_green_bg);
            dist.setFocusableInTouchMode(true);
            distBtn.setBackgroundResource(R.drawable.ic_action_remove);
            _distEnabled = true;
        }
    }

    public void onCSTDurBtnClicked(View view) {
        FrameLayout durBg = (FrameLayout) findViewById(R.id.custom_dur_bg);
        EditText hour = (EditText) findViewById(R.id.custom_hour);
        EditText min = (EditText) findViewById(R.id.custom_minute);
        ImageButton durBtn = (ImageButton) findViewById(R.id.custom_dur_btn);
        if (_durEnabled) {
            // disable it
            durBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            hour.setFocusable(false);
            min.setFocusable(false);
            durBtn.setBackgroundResource(R.drawable.ic_action_done);
            _durEnabled = false;
        } else {
            // enable it
            durBg.setBackgroundResource(R.drawable.edittext_green_bg);
            hour.setFocusableInTouchMode(true);
            min.setFocusableInTouchMode(true);
            durBtn.setBackgroundResource(R.drawable.ic_action_remove);
            _durEnabled = true;
        }
    }

    public void onCSTWgtBtnClicked(View view) {
        FrameLayout wgtBg = (FrameLayout) findViewById(R.id.custom_wgt_bg);
        EditText wgt = (EditText) findViewById(R.id.custom_wgt);
        ImageButton wgtBtn = (ImageButton) findViewById(R.id.custom_wgt_btn);
        if (_wgtEnabled) {
            // disable it
            wgtBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            wgt.setFocusable(false);
            wgtBtn.setBackgroundResource(R.drawable.ic_action_done);
            _wgtEnabled = false;
        } else {
            // enable it
            wgtBg.setBackgroundResource(R.drawable.edittext_green_bg);
            wgt.setFocusableInTouchMode(true);
            wgtBtn.setBackgroundResource(R.drawable.ic_action_remove);
            _wgtEnabled = true;
        }
    }

    public void onCSTSetrepBtnClicked(View view) {
        FrameLayout setrepBg = (FrameLayout) findViewById(R.id.custom_setrep_bg);
        EditText sets = (EditText) findViewById(R.id.custom_sets);
        EditText reps = (EditText) findViewById(R.id.custom_reps);
        ImageButton setrepBtn = (ImageButton) findViewById(R.id.custom_setrep_btn);
        if (_setrepEnabled) {
            // disable it
            setrepBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            sets.setFocusable(false);
            reps.setFocusable(false);
            setrepBtn.setBackgroundResource(R.drawable.ic_action_done);
            _setrepEnabled = false;
        } else {
            // enable it
            setrepBg.setBackgroundResource(R.drawable.edittext_green_bg);
            sets.setFocusableInTouchMode(true);
            reps.setFocusableInTouchMode(true);
            setrepBtn.setBackgroundResource(R.drawable.ic_action_remove);
            _setrepEnabled = true;
        }
    }

    private void setActvArray(int arrayId) {
        String[] exercises = getResources().getStringArray(arrayId);
        ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,exercises);
        _actv.setAdapter(adapter);
        _actv.requestFocus();
        _actv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }
        });
        _actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _actv.showDropDown();
            }
        });
    }
}
