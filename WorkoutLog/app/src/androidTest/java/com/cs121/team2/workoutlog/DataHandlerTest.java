package com.cs121.team2.workoutlog;

import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import java.io.IOException;


/**
 * Created by Sam on 12/4/2014.
 */

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

    public void testEmptyAddGetLog(){
        WOLog log = new WOLog();

        try {
            dhInstance.addLog(log);
            assertEquals(dhInstance.getLogs().isEmpty(), false);
            assertEquals(dhInstance.getLogs().contains(log), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testAddGetLog(){
        WOLog log = new WOLog();
        log.setType(WOLog.TYPE_ARRAY[0]);
        log.setName("addGetTestLog");
        log.setDistance("2");
        log.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]); //mi
        log.setTime("1", "00", "00");

        try {
            dhInstance.addLog(log);
            assertEquals(dhInstance.getLogs().isEmpty(), false);
            assertEquals(dhInstance.getLogs().contains(log), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testDelete(){
        WOLog log = new WOLog();
        log.setName("deleteTestLog");
        log.setType(WOLog.TYPE_ARRAY[0]);
        log.setDistance("2");
        log.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]); //mi
        log.setTime("1", "00", "00");

        try {
            dhInstance.addLog(log);

            dhInstance.editLog(log, log, true);

            assertEquals(dhInstance.getLogs().contains(log), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testEdit(){
        WOLog logOld = new WOLog();
        logOld.setName("editTestLogOld");
        logOld.setType(WOLog.TYPE_ARRAY[0]);
        logOld.setDistance("2");
        logOld.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]); //mi
        logOld.setTime("1", "00", "00");

        WOLog logNew = new WOLog();
        logNew.setName("editTestLogNew");
        logNew.setType(WOLog.TYPE_ARRAY[0]);
        logNew.setDistance("2");
        logNew.setCardioUnit(WOLog.CARDIO_UNIT_ARRAY[0]); //mi
        logNew.setTime("1", "00", "00");
        try {
            dhInstance.addLog(logOld);
            assertEquals(dhInstance.getLogs().contains(logOld), true);

            //replace old log with new log
            dhInstance.editLog(logOld, logNew, false);

            //old log no exist
            assertEquals(dhInstance.getLogs().contains(logOld), false);
            //new log exist
            assertEquals(dhInstance.getLogs().contains(logNew), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TESTING OF GetStats(ArrayList<WOLog> param) FUNCTION IS IN STATSACTIVITYTEST

}
