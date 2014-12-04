package com.cs121.team2.workoutlog;

import android.test.InstrumentationTestCase;

/**
 * Created by Sam on 12/4/2014.
 */

//TODO: Claire is responsible for this class
    //if you run out of time, let Sam E know and I can help. Just test basic things like setting
    //and getting (probs copy/paste that stuff repeatedly lol) and maybe the equals function/
    //any other really crazy stuff in there.


public class WOLogTest extends InstrumentationTestCase {

    private WOLog myLog;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        myLog = new WOLog();
    }


    public void testInstantiationWOLog() throws Exception {
        assertNotNull(myLog);
    }
}
