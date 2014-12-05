package com.cs121.team2.workoutlog;

/**
 * Created by Sam on 12/2/2014.
 * Used to test the Entry Activity and all its buttons and such.
 */


import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
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

    public EntryActivityTest() {
        super(EntryActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        //setActivityInitialTouchMode(false);
        mActivity = (EntryActivity) getActivity();
    }


    // check if it the wolog gets the right type and subtype assigned
    @UiThreadTest
    public void testCardioTime() {
        Button cardioBtn = (Button) mActivity.findViewById(R.id.entry_cardio);
        cardioBtn.performClick();
        Button timeBtn = (Button) mActivity.findViewById(R.id.entry_time);
        timeBtn.performClick();
        EditText memo = (EditText) mActivity.findViewById(R.id.memo);
        assertNotNull(memo);

        // Check if type / subtypes are assigned correctly
        assertEquals(mActivity._type, WOLog.TYPE_CARDIO);
        assertEquals(mActivity._subType, WOLog.SUBTYPE_TIME_BODY);

        Button nextBtn = (Button) mActivity.findViewById(R.id.common_next);
        nextBtn.performClick();

        // Check if the correct layout is displayed
        assertNotNull(mActivity.findViewById(R.id.cardio_dist));
        assertNull(mActivity.findViewById(R.id.strength_weight));
        assertNull(mActivity.findViewById(R.id.custom_dist));

        // Check if the correct default unit button is selected
        assertEquals(mActivity._cardioUnit, WOLog.UNIT_MI_LB);
    }

    @UiThreadTest
    public void testCardioDist() {
        Button cardioBtn = (Button) mActivity.findViewById(R.id.entry_cardio);
        cardioBtn.performClick();
        Button distBtn = (Button) mActivity.findViewById(R.id.entry_dist);
        distBtn.performClick();
        EditText memo = (EditText) mActivity.findViewById(R.id.memo);
        assertNotNull(memo);

        // Check if type / subtypes are assigned correctly
        assertEquals(mActivity._type, WOLog.TYPE_CARDIO);
        assertEquals(mActivity._subType, WOLog.SUBTYPE_DIST_WEIGHTS);

        Button nextBtn = (Button) mActivity.findViewById(R.id.common_next);
        nextBtn.performClick();

        // Check if the correct layout is displayed
        assertNotNull(mActivity.findViewById(R.id.cardio_dist));
        assertNull(mActivity.findViewById(R.id.strength_weight));
        assertNull(mActivity.findViewById(R.id.custom_dist));

        // Check if the correct default unit button is selected
        assertEquals(mActivity._cardioUnit, WOLog.UNIT_MI_LB);
    }

    @UiThreadTest
    public void testStrengthWeights() {
        Button strengthBtn = (Button) mActivity.findViewById(R.id.entry_strength);
        strengthBtn.performClick();
        Button wgtBtn = (Button) mActivity.findViewById(R.id.entry_wgt);
        wgtBtn.performClick();
        EditText memo = (EditText) mActivity.findViewById(R.id.memo);
        assertNotNull(memo);

        // check if type / subtypes are assigned correctly
        assertEquals(mActivity._type, WOLog.TYPE_STRENGTH);
        assertEquals(mActivity._subType, WOLog.SUBTYPE_DIST_WEIGHTS);

        Button nextBtn = (Button) mActivity.findViewById(R.id.common_next);
        nextBtn.performClick();

        // Check if the correct layout is displayed
        assertNull(mActivity.findViewById(R.id.cardio_dist));
        assertNotNull(mActivity.findViewById(R.id.strength_weight));
        assertNull(mActivity.findViewById(R.id.custom_dist));

        // Check if the correct default unit button is selected
        assertEquals(mActivity._strengthUnit, WOLog.UNIT_MI_LB);
    }

    @UiThreadTest
    public void testStrengthBodyWeights() {
        Button strengthBtn = (Button) mActivity.findViewById(R.id.entry_strength);
        strengthBtn.performClick();
        Button bodyWgtBtn = (Button) mActivity.findViewById(R.id.entry_body_wgt);
        bodyWgtBtn.performClick();
        EditText memo = (EditText) mActivity.findViewById(R.id.memo);
        assertNotNull(memo);

        // check if type / subtypes are assigned correctly
        assertEquals(mActivity._type, WOLog.TYPE_STRENGTH);
        assertEquals(mActivity._subType, WOLog.SUBTYPE_TIME_BODY);

        Button nextBtn = (Button) mActivity.findViewById(R.id.common_next);
        nextBtn.performClick();

        // Check if the correct layout is displayed
        assertNull(mActivity.findViewById(R.id.cardio_dist));
        assertNotNull(mActivity.findViewById(R.id.strength_weight));
        assertNull(mActivity.findViewById(R.id.custom_dist));

        // Check if the correct default unit button is selected
        assertEquals(mActivity._strengthUnit, WOLog.UNIT_MI_LB);
    }

    @UiThreadTest
    public void testCustom() {
        Button customBtn = (Button) mActivity.findViewById(R.id.entry_custom);
        customBtn.performClick();
        EditText memo = (EditText) mActivity.findViewById(R.id.memo);
        assertNotNull(memo);

        // check if type / subtypes are assigned correctly
        assertEquals(mActivity._type, WOLog.TYPE_CUSTOM);
        assertEquals(mActivity._subType, WOLog.SUBTYPE_NONE);

        Button nextBtn = (Button) mActivity.findViewById(R.id.common_next);
        nextBtn.performClick();

        // Check if the correct layout is displayed
        assertNull(mActivity.findViewById(R.id.cardio_dist));
        assertNull(mActivity.findViewById(R.id.strength_weight));
        assertNotNull(mActivity.findViewById(R.id.custom_dist));

        // Check if the correct default unit button is selected
        assertEquals(mActivity._cardioUnit, WOLog.UNIT_MI_LB);
        assertEquals(mActivity._strengthUnit, WOLog.UNIT_MI_LB);
    }

}