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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
//        //retrive data from file
//        FileInputStream fis = mContext.openFileInput("jsonLogs.json");
//        int c;
//        String temp="";
//        while( (c = fis.read()) != -1){
//            temp = temp + Character.toString((char)c);
//        }
//        fis.close();
//        //convert to non-JSON
//        ArrayList<WOLog> logList = (ArrayList<WOLog>) gson.fromJson(temp, listType);
        //TODO use getLogs here


        ArrayList<WOLog> logList = getLogs();
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

    public String getStats(){
        ArrayList<WOLog> data = new ArrayList<WOLog>();
        try {
            data = this.getLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int cardioCount = 0;
        int cardioTotalDistance = 0;
        int cardioTotalTime = 0;
        double cardioAverageSpeed = 0;

        int strengthCount = 0;
        int strengthTotalSets = 0;
        int strengthTotalReps = 0;
        int strengthTotalWeight = 0;
        int strengthMaxWeight = 0;
        double strengthAverageSets = 0;
        double strengthAverageReps = 0;
        double strengthAverageWeight = 0;

        int customCount = 0;
        Map customNameCount = new TreeMap<String, Integer>();

        for(int i = 0; i < data.size(); i++){
            WOLog log = data.get(i);
            String type = log.getType();

            //TODO unit handling
            //general case: average mood, time spent
            if (type.equals(WOLog.TYPE_ARRAY[0])){ //cardio case
                cardioCount++;
                if (log.getDistance() != null) {
                    cardioTotalDistance += Integer.parseInt(log.getDistance());
                }
                if (log.getTime() != null) {
                    cardioTotalTime += Integer.parseInt(log.getTime());
                }
            } else if (type.equals(WOLog.TYPE_ARRAY[1])){ //strength case
                strengthCount++;
                if (log.getSets() != null) {
                    strengthTotalSets += Integer.parseInt(log.getSets());
                }
                if (log.getReps() != null) {
                    strengthTotalReps += Integer.parseInt(log.getReps());
                }
                if (log.getWeight() != null) {
                    strengthTotalWeight += Integer.parseInt(log.getWeight());
                    strengthMaxWeight = Math.max(
                            Integer.parseInt(log.getWeight()),
                            strengthMaxWeight);
                }
            } else if (type.equals(WOLog.TYPE_ARRAY[2])){ //custom case
                customCount++;
                Integer integer = (Integer) customNameCount.get(log.getName());
                if (integer == null || log.getName() != null){
                    customNameCount.put(log.getName(), 1);
                } else if (log.getName() != null) {
                    customNameCount.put(log.getName(), integer++);
                }
            }
        }

        cardioAverageSpeed = cardioTotalDistance / cardioTotalTime;
        strengthAverageReps = strengthTotalReps / strengthCount;
        strengthAverageSets = strengthTotalSets / strengthCount;
        strengthAverageWeight = strengthTotalWeight / strengthCount;


        String s = "";

        s += "<b> Cardio </b> (" + cardioCount + ") <br>";
        s += "Total Distance: " + cardioTotalDistance + "<br>";
        s += "Total Time: " + cardioTotalTime + "<br>";
        s += "Average Speed: " + cardioAverageSpeed + "<br>";

        s += "<br> <b> Strength </b> (" + strengthCount + ")<br>";
        s += "Average Sets: " + strengthAverageSets + "<br>";
        s += "Average Reps: " + strengthAverageReps + "<br>";
        s += "Average Weight: " + strengthAverageWeight + "<br>";
        s += "Heaviest Weight: " + strengthMaxWeight + "<br>";

        s += "<br> <b> Custom </b> (" + customCount + ")<br>";
        Set<Map.Entry<String,Integer>> entrySet = customNameCount.entrySet();
        for (Map.Entry entry : entrySet) {
            s += entry.getKey() + ": " + entry.getValue() + "<br>";
        }


        s += "</center>";

        return s;
    }
}
