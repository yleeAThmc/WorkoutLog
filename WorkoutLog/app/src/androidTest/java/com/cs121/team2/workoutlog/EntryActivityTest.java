package com.cs121.team2.workoutlog;

/**
 * Created by Sam on 12/2/2014.
 * Used to test the Entry Activity and all its buttons and such.
 */


//TODO: Kelly is responsible for this class;
// don't worry about spending too much time on this--maybe an hour, at most 2--to get basic picking and
//choosing set/checking values selected and stored. Using the android activity tutorial
//I have linked in basecamp is the best bet. I expect because this is our most complex class that it'll
//be hardest to write tests for, so just do what you can it'll work :)

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TimePicker;


public class EntryActivityTest extends
        ActivityInstrumentationTestCase2 {

    private EntryActivity mActivity;
    private Button btn1;
    private SeekBar moodBar;
    private AutoCompleteTextView mAutoCardio;
    private AutoCompleteTextView mAutoStrength;
    private WOLog myLog;
    private DatePicker mDate;
    private TimePicker mTime;

    public EntryActivityTest() {
        super(EntryActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = (EntryActivity) getActivity();
        System.out.println("MACTIVITTY: " + mActivity);
        //startActivity(new Intent(getInstrumentation().getTargetContext(), EntryActivity.class), null, null);
        btn1 = (Button) mActivity.findViewById(R.id.entry_cardio);
        mAutoStrength = (AutoCompleteTextView) mActivity.findViewById(R.id.strength_actv);
        mAutoCardio = (AutoCompleteTextView) mActivity.findViewById(R.id.cardio_actv);
        moodBar = (SeekBar) mActivity.findViewById(R.id.mood);
        myLog = new WOLog();
        mDate = (DatePicker) mActivity.findViewById(R.id.date);
        mTime = (TimePicker) mActivity.findViewById(R.id.time);
        ImageButton miBtn = (ImageButton) mActivity.findViewById(R.id.miBtn);
        ImageButton mBtn = (ImageButton) mActivity.findViewById(R.id.mBtn);
        ImageButton kmBtn = (ImageButton) mActivity.findViewById(R.id.kmBtn);
        ImageButton lbBtn = (ImageButton) mActivity.findViewById(R.id.lbBtn);
        ImageButton kgBtn = (ImageButton) mActivity.findViewById(R.id.kgBtn);
        ImageButton distButton = (ImageButton) mActivity.findViewById(R.id.custom_dist_btn);
        ImageButton timeButton = (ImageButton) mActivity.findViewById(R.id.custom_dur_btn);
        ImageButton weightButton = (ImageButton) mActivity.findViewById(R.id.custom_wgt_btn);
        ImageButton setRepButton = (ImageButton) mActivity.findViewById(R.id.custom_setrep_btn);
        EditText customDist = (EditText) mActivity.findViewById(R.id.custom_dist);
        EditText customHour = (EditText) mActivity.findViewById(R.id.custom_hour);
        EditText customMinute = (EditText) mActivity.findViewById(R.id.custom_minute);
        EditText customWeight = (EditText) mActivity.findViewById(R.id.custom_wgt);
        EditText customSets = (EditText) mActivity.findViewById(R.id.custom_sets);
        EditText customReps = (EditText) mActivity.findViewById(R.id.custom_reps);

    }


    //
    public void testFirstJokeExample() {
        String blah = "ssss";
        assertNotNull(blah);

//        buttonId = getActivity().R.id.view_list_button;
         assertNotNull(btn1);
    }

}