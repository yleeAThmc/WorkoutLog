package com.cs121.team2.workoutlog;

import android.content.Intent;
import android.os.Parcelable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sam on 12/4/2014.
 * Tests for the DetailActivity basic functions.
 */

public class DetailActivityTest extends
        ActivityUnitTestCase<DetailActivity> {

    //private data members
    private WOLog log1;
    private Button editButton;
    DetailActivity mActivity;


    //constructor
    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    //setup
    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(
                getInstrumentation().getTargetContext(), WOLogListActivity.class
        );
        log1 = new WOLog();
        log1.setDate(11, 1, 2014, 20, 15);
        log1.setName("cardio 1");
        log1.setDistance("12");
        log1.setMood("k");
        log1.setTime("12", "2", "2");
        log1.setType("Cardio");
        log1.setCardioUnit("mi");
        log1.setSubtype("1");
        intent.putExtra("log", (android.os.Parcelable) (log1));
        startActivity(intent, null, null);
        mActivity = (DetailActivity) getActivity();


    }

    public void testInputLog() throws Exception {
        //perform a click on the edit button
        editButton = (Button) mActivity.findViewById(R.id.editButton);
        editButton.performClick();

        //check if the extra in the launch intent is the same as inputLog
        Intent intent = getStartedActivityIntent();
        assertNotNull(intent);
        assertTrue(log1.equals(intent.getParcelableExtra("toEdit")));

    }

}
