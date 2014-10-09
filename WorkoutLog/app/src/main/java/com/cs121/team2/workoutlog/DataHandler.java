package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sam J on 10/4/14.
 */

/*
 * TODO:
 * add variables needed for this class??????
 * finish constructor
 * fill in the methods
 * think of a better name for "Log" because that is a Java object already
 */
public class DataHandler extends Activity {
    public static DataHandler _dh; //the DataHandler instance that will be constructed and kept
    private static Gson gson;
    private static Type listType;
    private DataHandler() { //this is a singleton class, so this is kept private
        gson = new Gson();
        listType = new TypeToken<ArrayList<WOLog>>(){}.getType();
    }
    public synchronized static DataHandler getDataHandler() { //used to make/get the DH
        if (_dh == null) { //does the DH already exist?
            _dh = new DataHandler(); //if not, create a new one
        }
        return _dh; //if so, just return the DH that is already instantiated
    }
    //sends the ArrayList of Logs to LLAdapter
    public synchronized ArrayList<WOLog> getLogs() throws IOException {
        //find and read data from data storage to string temp
        FileInputStream fis = openFileInput("jsonLog.json");
        int c;
        String temp="";
        while( (c = fis.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        fis.close();
        //convert to non-JSON
        ArrayList<WOLog> toReturn = (ArrayList<WOLog>) gson.fromJson(temp, listType);
        //send to LLAdapter
        return toReturn;
    }
    //appends a new log to the Log AList
    public synchronized void addLog(WOLog toAdd) throws IOException {
        //convert to JSON
        WOLog newLog = toAdd;
        String jsonLog = gson.toJson(newLog);
        //save to a .txt file
        FileOutputStream fos = openFileOutput(jsonLog+".json", Context.MODE_APPEND);
        //write to internal storage
        fos.write(jsonLog.getBytes());
        fos.close();
    }
    private ArrayList<WOLog> convertToLogs() { //converts the data file into Log AList

    }
}
