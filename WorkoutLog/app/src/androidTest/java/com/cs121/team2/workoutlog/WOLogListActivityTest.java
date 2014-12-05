package com.cs121.team2.workoutlog;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sam on 12/4/2014.
 *
 * Tests the major actions of the WOLogListActivity.
 */


public class WOLogListActivityTest extends  ActivityUnitTestCase<WOLogListActivity> {

    private WOLogListActivity activity;

    public WOLogListActivityTest() {
        super(WOLogListActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(
                getInstrumentation().getTargetContext(), WOLogListActivity.class
        );
        startActivity(intent, null, null);
        activity = getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testMajorItems() {
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);

        // Check if list exists
        ListView list = (ListView) activity.findViewById(R.id.wologlist_listview);
        assertNotNull("list was null", list);

        // Load test data
        ArrayList<WOLog> data = createData();
        WOLogListAdapter adapter = new WOLogListAdapter(
                getInstrumentation().getContext(), data);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        assertEquals(3, adapter.getCount());

        Button statsButton = (Button) activity.findViewById(R.id.stats_button);
        // Perform a click on the stats button
        statsButton.performClick();

        // Check if the contact details activity got started
        Intent intent = getStartedActivityIntent();
        assertNotNull(intent);
        assertEquals(
                StatsActivity.class.getName(),
                intent.getComponent().getClassName()
        );
    }

    private ArrayList<WOLog> createData() {
        ArrayList<WOLog> logs = new ArrayList<WOLog>();

        WOLog log1 = new WOLog();
        log1.setDate(11, 1, 2014, 20, 15);
        log1.setName("cardio 1");
        log1.setDistance("12");
        log1.setMood("k");
        log1.setTime("12", "2", "2");
        log1.setType("Cardio");
        log1.setCardioUnit("mi");
        log1.setSubtype("1");
        WOLog log2 = new WOLog();
        log2.setDate(12, 2, 2014, 22, 11);
        log2.setName("cardio 2");
        log2.setDistance("111");
        log2.setMood("awful");
        log2.setTime("3", "1", "28");
        log2.setType("Cardio");
        log2.setCardioUnit("m");
        log2.setSubtype("1");
        WOLog log3 = new WOLog();
        log3.setDate(11, 19, 2014, 10, 20);
        log3.setName("strength 2");
        log3.setReps("23");
        log3.setSets("122");
        log3.setWeight("1000");
        log3.setMood("k");
        log3.setTime("8", "1", "12");
        log3.setType("Strength");
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);

       return logs;
    }

}
