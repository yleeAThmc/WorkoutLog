package com.cs121.team2.workoutlog;

import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;


/**
 * Created by Sam on 12/4/2014.
 */


//TODO: Sam J is responsible for this class;
// just do things testing about making/putting in logs/taking out logs of the datafile and make
    //sure things work out that way, also a test for the equals function would probably be
    //a good idea

public class DataHandlerTest extends InstrumentationTestCase {
    //Instrumentation test case is used here, as this  isn't an activity, so you don't
    //need all the extra stuff
    private DataHandler dhInstance;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dhInstance = DataHandler.getDataHandler(new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test"));
    }


    public void testInstantiation() throws Exception {
        assertNotNull(dhInstance);
    }


}
