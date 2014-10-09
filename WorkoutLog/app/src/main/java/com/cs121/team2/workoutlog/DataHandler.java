package com.cs121.team2.workoutlog;

import java.util.ArrayList;

/**
 * Created by Sam J on 10/4/14.
 */

/*
 * TODO:
 * add variables needed for this class
 * finish constructor
 * fill in the methods
 * think of a better name for "Log" because that is a Java object already
 * change return type of readData() and convertFromLogs to something proper
 */
public class DataHandler {
    public static DataHandler _dh; //the DataHandler instance that will be constructed and kept
    private DataHandler() { //this is a singleton class, so this is kept private

    }
    public synchronized static DataHandler getDataHandler() { //used to make/get the DH
        if (_dh == null) { //does the DH already exist?
            _dh = new DataHandler(); //if not, create a new one
        }
        return _dh; //if so, just return the DH that is already instantiated
    }
    public synchronized ArrayList<WOLog> getLogs() { //sends the ArrayList of Logs to LLAdapter
        return null;
    }
    public synchronized void addLog(WOLog toAdd) { //appends a new log to the Log AList

    }
    private void readData() { //reads the data file from internal storage

    }
    private void writeData() { //writes the data into internal storage

    }
    private ArrayList<WOLog> convertToLogs() { //converts the data file into Log AList
        return null;
    }
    private void convertFromLogs() { //converts Log AList into usable file for storage

    }
}
