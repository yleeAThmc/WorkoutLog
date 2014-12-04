package com.cs121.team2.workoutlog;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.RenamingDelegatingContext;
import android.text.Html;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sam on 12/4/2014.
 */

//TODO: Claire is responsible for this class
    //do what you can/gotta do. Maybe just check that the data being displayed is correct/
    //the mathing is correct?

    //Also: I'm not totally sure I set up the logs to get the stats correctly, so check that. Also, if
    //actually want there to be math, you might want to set values for the logs.


public class StatsActivityTest extends ActivityInstrumentationTestCase2 {

    //private data members
    private TextView myText;
    private WOLog log1;
    private WOLog log2;
    private WOLog log3;
    private DataHandler dhInstance;
    private StatsActivity mActivity;


    //constructor
    public StatsActivityTest() {
        super(StatsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = (StatsActivity) getActivity();
        myText = (TextView) getActivity().findViewById(R.id.stats_page);

        log1 = new WOLog();
        log2 = new WOLog();
        log3 = new WOLog();
        dhInstance = DataHandler.getDataHandler(new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test"));
        dhInstance.addLog(log1);
        dhInstance.addLog(log2);
        dhInstance.addLog(log3);
        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();
        listOfLogs.add(log1);
        listOfLogs.add(log2);
        listOfLogs.add(log3);
        //source string with HTML formatting tags for setText()
        String sourceString = dhInstance.getStats(listOfLogs);
        //set the text for the TextView
        myText.setText(Html.fromHtml(sourceString));
    }
}
