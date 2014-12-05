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
    private String myText;
    private WOLog log1;
    private WOLog log2;
    private WOLog log3;
    private DataHandler dhInstance;

    //constructor
    public StatsActivityTest() {
        super(StatsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        dhInstance = DataHandler.getDataHandler(new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test"));

        log1 = new WOLog();
        log2 = new WOLog();
        log3 = new WOLog();

        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();
        listOfLogs.add(log1);
        listOfLogs.add(log2);
        listOfLogs.add(log3);

        myText = dhInstance.getStats(listOfLogs);
    }

    public void testEmptyGetStats(){
        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText, "<center> No Logs With Present Filters </center>");
    }

    public void testGetStatsTypeCount(){
        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();

        log1 = new WOLog();
        log1.setType(WOLog.TYPE_ARRAY[0]);
        log2 = new WOLog();
        log2.setType(WOLog.TYPE_ARRAY[1]);
        log3 = new WOLog();
        log3.setType(WOLog.TYPE_ARRAY[2]);

        listOfLogs.add(log1);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Cardio </b> (1) <br>"), true);

        listOfLogs.add(log2);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Strength </b> (1) <br>"), true);

        listOfLogs.add(log3);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Custom </b> (1) <br>"), true);

        listOfLogs.add(log1);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Cardio </b> (2) <br>"), true);

        listOfLogs.add(log2);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Strength </b> (2) <br>"), true);

        listOfLogs.add(log3);
        myText = dhInstance.getStats(listOfLogs);
        assertEquals(myText.contains("<b> Custom </b> (2) <br>"), true);
    }

    public void testGetStatsCardio(){
        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();

        log1 = new WOLog();
        log1.setType(WOLog.TYPE_ARRAY[0]);
        log1.setDistance("2");
        log1.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]); //mi
        log1.setTime("1", "00", "00");

        listOfLogs.add(log1);
        myText = dhInstance.getStats(listOfLogs);

        assertEquals(myText.contains("Total Distance: 2.0 mi"), true);
        assertEquals(myText.contains("Total Time: 1.0 hr"), true);
        assertEquals(myText.contains("Average Speed: 2.0 mi/hr"), true);


        log2 = new WOLog();
        log2.setType(WOLog.TYPE_ARRAY[0]);
        log2.setDistance("1");
        log2.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]);
        log2.setTime("2", "00", "00");

        listOfLogs.add(log2);
        myText = dhInstance.getStats(listOfLogs);

        assertEquals(myText.contains("Total Distance: 3.0 mi"), true);
        assertEquals(myText.contains("Total Time: 3.0 hr"), true);
        assertEquals(myText.contains("Average Speed: 1.0 mi/hr"), true);
    }

    public void testGetStatsStrength(){
        ArrayList<WOLog> listOfLogs = new ArrayList<WOLog>();

        log1 = new WOLog();
        log1.setType(WOLog.TYPE_ARRAY[1]);
        log1.setWeight("200");
        log1.setStrengthUnit(WOLog.STRENGTH_UNIT_ARRAY[0]); //lbs
        log1.setSets("2");
        log1.setReps("10");

        listOfLogs.add(log1);
        myText = dhInstance.getStats(listOfLogs);

        assertEquals(myText.contains("Average Sets: 2.0"), true);
        assertEquals(myText.contains("Average Reps: 10.0"), true);
        assertEquals(myText.contains("Average Weight: 200.0 lbs"), true);
        assertEquals(myText.contains("Heaviest Weight: 200.0 lbs"), true);


        log2 = new WOLog();
        log2.setType(WOLog.TYPE_ARRAY[1]);
        log2.setWeight("400");
        log2.setStrengthUnit(WOLog.STRENGTH_UNIT_ARRAY[0]); //lbs
        log2.setSets("10");
        log2.setReps("2");

        listOfLogs.add(log2);
        myText = dhInstance.getStats(listOfLogs);

        assertEquals(myText.contains("Average Sets: 6.0"), true);
        assertEquals(myText.contains("Average Reps: 6.0"), true);
        assertEquals(myText.contains("Average Weight: 300.0 lbs"), true);
        assertEquals(myText.contains("Heaviest Weight: 400.0 lbs"), true);
    }

}
