package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sam J on 10/4/14.
 */

public class DataHandler extends Activity {
    private final String TAG = "Data Handler";
    public static DataHandler _dh; //the DataHandler instance that will be constructed and kept
    private static Gson gson;
    private static Type listType;
    private static Context mContext;

    private DataHandler() throws IOException { //this is a singleton class, so this is kept private
        gson = new Gson();
        listType = new TypeToken<ArrayList<WOLog>>(){}.getType();
        File file = new File(mContext.getFilesDir(), "jsonLogs.json");
        file.createNewFile();
    }
    public synchronized static DataHandler getDataHandler(Context context) { //used to make/get the DH
        mContext = context;
        if (_dh == null) { //does the DH already exist?
            try {
                _dh = new DataHandler(); //if not, create a new one
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _dh; //if so, just return the DH that is already instantiated
    }

    //sends the ArrayList of Logs to LLAdapter
    public synchronized ArrayList<WOLog> getLogs() throws IOException {
        //find and read data from data storage to string temp
        FileInputStream fis = mContext.openFileInput("jsonLogs.json");
        int c;
        String temp="";
        while( (c = fis.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        fis.close();

        //convert to non-JSON
        ArrayList<WOLog> toReturn = (ArrayList<WOLog>) gson.fromJson(temp, listType);
        if (toReturn == null){
            toReturn = new ArrayList<WOLog>();
            //TODO show user friendly error message
        }
        //send to LLAdapter
        return toReturn;
    }

    //appends a new log to the Log AList
    public synchronized void addLog(WOLog toAdd) throws IOException {
        //retrive data from file
        FileInputStream fis = mContext.openFileInput("jsonLogs.json");
        int c;
        String temp="";
        while( (c = fis.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        fis.close();
        //convert to non-JSON
        ArrayList<WOLog> logList = (ArrayList<WOLog>) gson.fromJson(temp, listType);

        if (logList == null) {
            logList = new ArrayList<WOLog>();
        }
        logList.add(toAdd);



        // Sorts the list of logs from oldest to newest
        Collections.sort(logList, new Comparator<WOLog>() {
            @Override
            public int compare(WOLog woLog, WOLog woLog2) {
                return woLog2.getDateCompare() - woLog.getDateCompare();
            }
        });

        // For clearing the file while testing: logList = null;

        //convert to JSON
        String jsonLog = gson.toJson(logList);
        //save to a .txt file
        FileOutputStream fos = mContext.openFileOutput("jsonLogs.json", Context.MODE_PRIVATE);
        //write to internal storage
        fos.write(jsonLog.getBytes());
        fos.close();
    }

    //edits or removes an existing log
    public synchronized void editLog(WOLog newLog, WOLog oldLog, boolean delete) throws IOException {
        //save a dummy copy of oldLog
        WOLog dummy = oldLog;
        //retrieve data from file
        FileInputStream fis = mContext.openFileInput("jsonLogs.json");
        int c;
        String temp="";
        while( (c = fis.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        fis.close();

        //convert to non-JSON
        ArrayList<WOLog> logList = (ArrayList<WOLog>) gson.fromJson(temp, listType);

        if(delete) { //are we deleting the log?
            logList.remove(oldLog); //...if so, delete the log
        }
        else { //...if not, we're editing the log
            int myIndex = logList.indexOf(oldLog); //find the index of the old log
            logList.set(myIndex, newLog); //set the old log to the new log
        }

        // For clearing the file while testing: logList = new ArrayList<WOLog>();

        //sort loglist
        if(!logList.isEmpty()) {
            Collections.sort(logList, new Comparator<WOLog>() {
                @Override
                public int compare(WOLog woLog, WOLog woLog2) {
                    return woLog2.getDateCompare() - woLog.getDateCompare();
                }
            });
        }
        Log.d("past sort","we got past Collections.sort");

        //convert to JSON
        String jsonLog = gson.toJson(logList);
        Log.d("past toJson","we got past jsonLog = gson.toJson(logList)");
        //save to a .txt file
        FileOutputStream fos = mContext.openFileOutput("jsonLogs.json", Context.MODE_PRIVATE);
        Log.d("past fos","we got past fos = mContext.openFileOutput(...)");
        //write to internal storage
        fos.write(jsonLog.getBytes());
        Log.d("past write","we got past fos.write(...)");
        fos.close();
        Log.d("past close","we got past fos.close()");
    }
}
