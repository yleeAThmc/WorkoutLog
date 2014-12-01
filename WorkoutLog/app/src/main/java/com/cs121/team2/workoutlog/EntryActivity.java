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

    private int _cardioUnit = -1;
    private int _strengthUnit = -1;

    private AutoCompleteTextView _actv;

    // specific to custom wo
    private Boolean _distEnabled = true;
    private Boolean _durEnabled = true;
    private Boolean _wgtEnabled = true;
    private Boolean _setrepEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initial choice: Cardio/Strength/Custom
        setContentView(R.layout.entry_initial_choice);

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
            if (_subType == WOLog.SUBTYPE_TIME_BODY) {
                setActvArray(R.array.time_cardio_array);
            } else {
                setActvArray(R.array.dist_cardio_array);
            }
        } else if (_type == WOLog.TYPE_STRENGTH) {
            setContentView(R.layout.entry_strength);
            _actv = (AutoCompleteTextView) findViewById(R.id.strength_actv);
            if (_subType == WOLog.SUBTYPE_TIME_BODY) {
                setActvArray(R.array.body_strength_array);
            } else {
                setActvArray(R.array.weights_strength_array);
            }
        } else if (_type == WOLog.TYPE_CUSTOM) {
            setContentView(R.layout.entry_custom_workout);
        }
    }

    private void initializeDateAndTime() {
        _date = (DatePicker) findViewById(R.id.date);
        _time = (TimePicker) findViewById(R.id.time);
        _time.setIs24HourView(true);
    }

    private void setTime(String h, String m, String s) {
        if (h.length() == 0) {
            h = "0";
        }
        if (m.length() == 0) {
            m = "00";
        }
        if (s.length() == 0) {
            s = "00";
        }
        _wl.setTime(h+":"+m+":"+s);
    }

    public void onClickFt(View view) {
        ImageButton ftBtn = (ImageButton) findViewById(R.id.ftBtn);
        ImageButton mBtn = (ImageButton) findViewById(R.id.mBtn);
        ImageButton kmBtn = (ImageButton) findViewById(R.id.kmBtn);
        ftBtn.setBackgroundResource(R.drawable.cream_dark_ft);
        mBtn.setBackgroundResource(R.drawable.cream_m);
        kmBtn.setBackgroundResource(R.drawable.cream_km);
        _cardioUnit = WOLog.UNIT_FT_LB;
    }

    public void onClickM(View view) {
        ImageButton ftBtn = (ImageButton) findViewById(R.id.ftBtn);
        ImageButton mBtn = (ImageButton) findViewById(R.id.mBtn);
        ImageButton kmBtn = (ImageButton) findViewById(R.id.kmBtn);
        ftBtn.setBackgroundResource(R.drawable.cream_ft);
        mBtn.setBackgroundResource(R.drawable.cream_dark_m);
        kmBtn.setBackgroundResource(R.drawable.cream_km);
        _cardioUnit = WOLog.UNIT_M_KG;

    }

    public void onClickKm(View view) {
        ImageButton ftBtn = (ImageButton) findViewById(R.id.ftBtn);
        ImageButton mBtn = (ImageButton) findViewById(R.id.mBtn);
        ImageButton kmBtn = (ImageButton) findViewById(R.id.kmBtn);
        ftBtn.setBackgroundResource(R.drawable.cream_ft);
        mBtn.setBackgroundResource(R.drawable.cream_m);
        kmBtn.setBackgroundResource(R.drawable.cream_dark_km);
        _cardioUnit = WOLog.UNIT_KM;
    }

    public void onClickLb(View view) {
        ImageButton lbBtn = (ImageButton) findViewById(R.id.lbBtn);
        ImageButton kgBtn = (ImageButton) findViewById(R.id.kgBtn);
        lbBtn.setBackgroundResource(R.drawable.cream_dark_lb);
        kgBtn.setBackgroundResource(R.drawable.cream_kg);
        _strengthUnit = WOLog.UNIT_FT_LB;
    }

    public void onClickKg(View view) {
        ImageButton lbBtn = (ImageButton) findViewById(R.id.lbBtn);
        ImageButton kgBtn = (ImageButton) findViewById(R.id.kgBtn);
        lbBtn.setBackgroundResource(R.drawable.cream_lb);
        kgBtn.setBackgroundResource(R.drawable.cream_dark_kg);
        _strengthUnit = WOLog.UNIT_M_KG;
    }

    private void setCardioUnit() {

        Log.e(TAG, _cardioUnit+"");
        if (_cardioUnit != -1) {
            _wl.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[_cardioUnit]);
        }

    }

    private void setStrengthUnit() {
        if (_strengthUnit != -1) {
            _wl.setStrengthUnit(WOLog.STRENGTH_UNIT_ARRAY[_strengthUnit]);
        }
    }

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

    private void setMood() { _mood = (SeekBar) findViewById(R.id.mood); }

    private void setMemo() {
        _memo = (EditText) findViewById(R.id.memo);
    }

    public void getInitialChoiceView(View view) {
        setContentView(R.layout.entry_initial_choice);
    }

    public void onCardioSubmit(View view) {
        EditText dist = (EditText) findViewById(R.id.cardio_dist);
        EditText hour = (EditText) findViewById(R.id.cardio_hour);
        EditText min = (EditText) findViewById(R.id.cardio_minute);
        EditText sec = (EditText) findViewById(R.id.cardio_second);
        _wl.setDistance(String.valueOf(dist.getText()));
        _wl.setName(String.valueOf(_actv.getText()));
        setTime(String.valueOf(hour.getText()), String.valueOf(min.getText()),
                String.valueOf(sec.getText()));
        setCardioUnit();
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
        setStrengthUnit();
        onSubmit();
    }

    public void onCustomSubmit(View view) {
        EditText type = (EditText) findViewById(R.id.custom_type);
        _wl.setName(String.valueOf(type.getText()));

        if (_distEnabled) {
            EditText distText = (EditText) findViewById(R.id.custom_dist);
            _wl.setDistance(String.valueOf(distText.getText()));
            setCardioUnit();
        }
        if (_durEnabled) {
            EditText hour = (EditText) findViewById(R.id.custom_hour);
            EditText min = (EditText) findViewById(R.id.custom_minute);
            EditText sec = (EditText) findViewById(R.id.custom_second);
            setTime(String.valueOf(hour.getText()), String.valueOf(min.getText()),
                    String.valueOf(sec.getText()));
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
            setStrengthUnit();
        }
        onSubmit();
    }

    private void onSubmit() {
        _wl.setType(WOLog.TYPE_ARRAY[_type]);
        _wl.setSubtype(WOLog.SUBTYPE_ARRAY[_subType]);
        try {
            _dhInstance.addLog(_wl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.entry_initial_choice);
        startWOLogListAct();
    }
    public void onCSTDistBtnClicked(View view) {
        FrameLayout distBg = (FrameLayout) findViewById(R.id.custom_dist_bg);
        EditText dist = (EditText) findViewById(R.id.custom_dist);
        ImageButton distBtn = (ImageButton) findViewById(R.id.custom_dist_btn);
        ImageButton ftBtn = (ImageButton) findViewById(R.id.ftBtn);
        ImageButton mBtn = (ImageButton) findViewById(R.id.mBtn);
        ImageButton kmBtn = (ImageButton) findViewById(R.id.kmBtn);
        if (_distEnabled) {
            // disable it
            distBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            dist.setFocusable(false);
            distBtn.setBackgroundResource(R.drawable.ic_action_done);
            ftBtn.setClickable(false);
            mBtn.setClickable(false);
            kmBtn.setClickable(false);
            _distEnabled = false;
        } else {
            // enable it
            distBg.setBackgroundResource(R.drawable.edittext_green_bg);
            dist.setFocusableInTouchMode(true);
            distBtn.setBackgroundResource(R.drawable.ic_action_remove);
            ftBtn.setClickable(true);
            mBtn.setClickable(true);
            kmBtn.setClickable(true);
            _distEnabled = true;
        }
    }

    public void onCSTDurBtnClicked(View view) {
        FrameLayout durBg = (FrameLayout) findViewById(R.id.custom_dur_bg);
        EditText hour = (EditText) findViewById(R.id.custom_hour);
        EditText min = (EditText) findViewById(R.id.custom_minute);
        EditText sec = (EditText) findViewById(R.id.custom_second);
        ImageButton durBtn = (ImageButton) findViewById(R.id.custom_dur_btn);
        if (_durEnabled) {
            // disable it
            durBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            hour.setFocusable(false);
            min.setFocusable(false);
            sec.setFocusable(false);
            durBtn.setBackgroundResource(R.drawable.ic_action_done);
            _durEnabled = false;
        } else {
            // enable it
            durBg.setBackgroundResource(R.drawable.edittext_green_bg);
            hour.setFocusableInTouchMode(true);
            min.setFocusableInTouchMode(true);
            sec.setFocusableInTouchMode(true);
            durBtn.setBackgroundResource(R.drawable.ic_action_remove);
            _durEnabled = true;
        }
    }

    public void onCSTWgtBtnClicked(View view) {
        FrameLayout wgtBg = (FrameLayout) findViewById(R.id.custom_wgt_bg);
        EditText wgt = (EditText) findViewById(R.id.custom_wgt);
        ImageButton wgtBtn = (ImageButton) findViewById(R.id.custom_wgt_btn);
        ImageButton lbBtn = (ImageButton) findViewById(R.id.lbBtn);
        ImageButton kgBtn = (ImageButton) findViewById(R.id.kgBtn);
        if (_wgtEnabled) {
            // disable it
            wgtBg.setBackgroundResource(R.drawable.edittext_cream_bg);
            wgt.setFocusable(false);
            wgtBtn.setBackgroundResource(R.drawable.ic_action_done);
            lbBtn.setClickable(false);
            kgBtn.setClickable(false);
            _wgtEnabled = false;
        } else {
            // enable it
            wgtBg.setBackgroundResource(R.drawable.edittext_green_bg);
            wgt.setFocusableInTouchMode(true);
            wgtBtn.setBackgroundResource(R.drawable.ic_action_remove);
            lbBtn.setClickable(true);
            kgBtn.setClickable(true);
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
