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
    private WOLog myOtherLog;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        myLog = new WOLog();
        myOtherLog = new WOLog();
    }


    public void testInstantiationWOLog() throws Exception {
        assertNotNull(myLog);
    }

    public void testGetSetDate() {
        myLog.setDate(11, 26, 1994, 11, 00); //birthday!!!
        assertNotNull(myLog.getDate());
        assertEquals(myLog.getDate(), "11-26-1994 11:00");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testDateCompare() {
        //test that DateCompare is properly dealing handling date comparisons
        myLog.setDate(11, 26, 1994, 11, 00);

        //greater than comparisons
        myOtherLog.setDate(11, 26, 1994, 11, 01); //minute
        assertEquals(myLog.getDateCompare() < myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 26, 1994, 12, 00); //hour
        assertEquals(myLog.getDateCompare() < myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 26, 1995, 11, 00); //year
        assertEquals(myLog.getDateCompare() < myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 27, 1994, 11, 00); //day
        assertEquals(myLog.getDateCompare() < myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(12, 26, 1994, 11, 00); //month
        assertEquals(myLog.getDateCompare() < myOtherLog.getDateCompare(), true);

        //less than comparisons
        myOtherLog.setDate(11, 26, 1994, 10, 59); //minute
        assertEquals(myLog.getDateCompare() > myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 26, 1994, 10, 00); //hour
        assertEquals(myLog.getDateCompare() > myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 26, 1993, 11, 00); //year
        assertEquals(myLog.getDateCompare() > myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(11, 25, 1994, 11, 00); //day
        assertEquals(myLog.getDateCompare() > myOtherLog.getDateCompare(), true);
        myOtherLog.setDate(10, 26, 1994, 11, 00); //month
        assertEquals(myLog.getDateCompare() > myOtherLog.getDateCompare(), true);

        //equal comparisons
        myOtherLog.setDate(11, 26, 1994, 11, 00);
        assertEquals(myLog.getDateCompare() == myOtherLog.getDateCompare(), true);

        //revert for next tests
        myLog = new WOLog();
        myOtherLog = new WOLog();
    }

    public void testGetSetName() {
        myLog.setName("spam");
        assertEquals(myLog.getName(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetTime() {
        myLog.setTime("hours", "minutes", "seconds");
        assertEquals(myLog.getTime(), "hours:minutes:seconds");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetDistance() {
        myLog.setDistance("spam");
        assertEquals(myLog.getDistance(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetReps() {
        myLog.setReps("spam");
        assertEquals(myLog.getReps(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetSets() { //whoops
        myLog.setSets("spam");
        assertEquals(myLog.getSets(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetWeight() {
        myLog.setWeight("spam");
        assertEquals(myLog.getWeight(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetMood() {
        myLog.setMood("spam");
        assertEquals(myLog.getMood(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetMemo() {
        myLog.setMemo("spam");
        assertEquals(myLog.getMemo(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetType() {
        myLog.setType("spam");
        assertEquals(myLog.getType(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetSubtype() {
        myLog.setSubtype("spam");
        assertEquals(myLog.getSubtype(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetCardioUnit() {
        myLog.setCardioUnit("spam");
        assertEquals(myLog.getCardioUnit(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testGetSetStrengthUnit() {
        myLog.setStrengthUnit("spam");
        assertEquals(myLog.getStrengthUnit(), "spam");
        //revert for next tests
        myLog = new WOLog();
    }

    public void testEqualsEmptyWOLog(){
        assertEquals(myLog.equals(myOtherLog), true);
    }

    public void testEqualsWOLog(){
        myLog.setCardioUnit("cardio unit");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setCardioUnit("cardio unit");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setDate(11,26,1994,11,00);
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setDate(11,26,1994,11,00);
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setDistance("distance");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setDistance("distance");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setMemo("memo");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setMemo("memo");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setMood("mood");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setMood("mood");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setName("name");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setName("name");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setReps("reps");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setReps("reps");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setSets("sets");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setSets("sets");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setStrengthUnit("strength unit");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setStrengthUnit("strength unit");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setSubtype("subtype");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setSubtype("subtype");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setTime("hours", "minutes", "seconds");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setTime("hours", "minutes", "seconds");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setType("type");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setType("type");
        assertEquals(myLog.equals(myOtherLog), true);

        myLog.setWeight("weight");
        assertEquals(myLog.equals(myOtherLog), false);
        myOtherLog.setWeight("weight");
        assertEquals(myLog.equals(myOtherLog), true);
    }
}