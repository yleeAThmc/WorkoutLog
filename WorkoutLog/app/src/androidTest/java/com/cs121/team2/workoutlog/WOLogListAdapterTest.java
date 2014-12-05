package com.cs121.team2.workoutlog;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Sam E on 12/4/2014.
 * This is used for basic unit testing of the WOLogListAdapter class, which deals
 * with the data shown in the WOLogListActivity.
 */


public class WOLogListAdapterTest extends AndroidTestCase {

    private WOLogListAdapter mAdapter;

    private WOLog log1;
    private WOLog log2;
    private WOLog log3;

    //constructor for testing class
    public WOLogListAdapterTest() {
        super();
    }

    //setup runs before every test, and sets initial values that are used in said testing
    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<WOLog> data = new ArrayList<WOLog>();

        log1 = new WOLog();
        log1.setDate(11, 1, 2014, 20, 15);
        log1.setName("cardio 1");
        log1.setDistance("12");
        log1.setMood("k");
        log1.setTime("12", "2", "2");
        log1.setType("Cardio");
        log1.setCardioUnit("mi");
        log1.setSubtype("1");
        log2 = new WOLog();
        log2.setDate(12, 2, 2014, 22, 11);
        log2.setName("cardio 2");
        log2.setDistance("111");
        log2.setMood("awful");
        log2.setTime("3", "1", "28");
        log2.setType("Cardio");
        log2.setCardioUnit("m");
        log2.setSubtype("1");
        log3 = new WOLog();
        log3.setDate(11, 19, 2014, 10, 20);
        log3.setName("strength 2");
        log3.setReps("23");
        log3.setSets("122");
        log3.setWeight("1000");
        log3.setMood("k");
        log3.setTime("8", "1", "12");
        log3.setType("Strength");
        data.add(log1);
        data.add(log2);
        data.add(log3);

        mAdapter = new WOLogListAdapter(getContext(), data);
    }

//Sample adapter tests adopted from StackOverflow post,
//http://stackoverflow.com/questions/11541114/unittesting-of-arrayadapter

    //check that we're accessing the correct logs based on position
    public void testGetItemLog1() {
        assertEquals("cardio 1 was expected", log1.getName(),
                (mAdapter.getItem(0)).getName());
    }

    public void testGetItemLog2() {
        assertEquals("cardio 2 was expected", log2.getName(),
                (mAdapter.getItem(1)).getName());
    }

    public void testGetItemLog3() {
        assertEquals("strength 1 was expected", log3.getName(),
                ( mAdapter.getItem(2)).getName());
    }

    //test the IDs from the adapter
    public void testGetItemId1() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetItemId2() {
        assertEquals("Wrong ID.", 1, mAdapter.getItemId(1));
    }

    public void testGetItemId3() {
        assertEquals("Wrong ID.", 1, mAdapter.getItemId(1));
    }

       //test that we can get the correct number of logs held by the adapter
    public void testGetCount() {
        assertEquals("Contacts amount incorrect.", 3, mAdapter.getCount());
    }

    //test for not-null view and textview
    public void testGetViewLog1() {
        View view = mAdapter.getView(0, null, null);

        TextView workoutInfo = (TextView) view
                .findViewById(R.id.log_date);

        assertNotNull("View is null. ", view);
        assertNotNull("TextView is null. ", workoutInfo);

    }

    public void testGetViewLog2() {
        View view = mAdapter.getView(1, null, null);

        TextView workoutInfo = (TextView) view
                .findViewById(R.id.log_date);

        assertNotNull("View is null. ", view);
        assertNotNull("TextView is null. ", workoutInfo);
    }

    public void testGetViewLog3() {
        View view = mAdapter.getView(2, null, null);

        TextView workoutInfo = (TextView) view
                .findViewById(R.id.log_date);

        assertNotNull("View is null. ", view);
        assertNotNull("TextView is null. ", workoutInfo);
    }

}
