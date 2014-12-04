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

    public String getStats(ArrayList<WOLog> inputList) {
        ArrayList<WOLog> data = inputList;
        if (data.isEmpty()) {
            return "<center> No Logs With Present Filters </center>";
        }

        int cardioCount = 0;
        double cardioTotalDistanceMi = 0;
        double cardioTotalTimeHr = 0;
        double cardioAverageSpeed = 0;

        int strengthCount = 0;
        int strengthTotalSets = 0;
        int strengthTotalReps = 0;
        double strengthTotalWeightLb = 0;
        double strengthMaxWeightLb = 0;
        double strengthAverageSets = 0;
        double strengthAverageReps = 0;
        double strengthAverageWeightLb = 0;

        int customCount = 0;
        Map customNameCount = new TreeMap<String, Integer>();

        for (int i = 0; i < data.size(); i++) {
            WOLog log = data.get(i);
            String type = log.getType();

            if (type.equals(WOLog.TYPE_ARRAY[0])) { //cardio case
                cardioCount++;
                //calculate distance and generalize measurements to miles
                if (log.getDistance() != null &&
                        !log.getDistance().equals("") && log.getCardioUnit()!=null) {
                    if (log.getCardioUnit().equals(WOLog.CARDIO_UNIT_ARRAY[0])) { //miles
                        cardioTotalDistanceMi += Double.parseDouble(log.getDistance());
                    }
                    if (log.getCardioUnit().equals(WOLog.CARDIO_UNIT_ARRAY[1])) { //meters
                        cardioTotalDistanceMi +=
                                //conversion m to mi from google
                                Double.parseDouble(log.getDistance()) * 0.000621371;
                    }
                    if (log.getCardioUnit().equals(WOLog.CARDIO_UNIT_ARRAY[2])) { //kilometers
                        cardioTotalDistanceMi +=
                                //conversion km to mi from google
                                Double.parseDouble(log.getDistance()) * 0.621371;
                    }
                }
                if (log.getTime() != null && !log.getTime().equals("")) {
                    //TODO: test this
                    String time = log.getTime();
                    String delims = "[:]";
                    String[] tokens = time.split(delims);

                    double hr = Double.parseDouble(tokens[0]);
                    double min = Double.parseDouble(tokens[1]);
                    double sec = Double.parseDouble(tokens[2]);

                    //generalize time to hr, conversion s and m to hr from
                    cardioTotalTimeHr += hr;
                    cardioTotalTimeHr += min * 0.0166667;
                    cardioTotalTimeHr += sec * 0.000277778;
                }
            } else if (type.equals(WOLog.TYPE_ARRAY[1])) { //strength case
                strengthCount++;
                if (log.getSets() != null && !log.getSets().equals("")) {
                    strengthTotalSets += Integer.parseInt(log.getSets());
                }
                if (log.getReps() != null && !log.getReps().equals("")) {
                    strengthTotalReps += Integer.parseInt(log.getReps());
                }
                //Weight is generalized to pounds
                if (log.getWeight() != null && !log.getWeight().equals("")) {
                    if (log.getStrengthUnit().equals(WOLog.STRENGTH_UNIT_ARRAY[0])) { //Case lb
                        strengthTotalWeightLb += Double.parseDouble(log.getWeight());
                        strengthMaxWeightLb = Math.max(
                                Double.parseDouble(log.getWeight()),
                                strengthMaxWeightLb);
                    }
                    if (log.getStrengthUnit().equals(WOLog.STRENGTH_UNIT_ARRAY[1])) { //Case kg
                        //conversion kg to lb from google
                        strengthTotalWeightLb += Double.parseDouble(log.getWeight()) * 2.20462;
                        strengthMaxWeightLb = Math.max(
                                Double.parseDouble(log.getWeight()) * 2.20462,
                                strengthMaxWeightLb);
                    }
                }
            } else if (type.equals(WOLog.TYPE_ARRAY[2])) { //custom case
                customCount++;
                if (log.getName() != null && !log.getName().equals("")) {
                    Integer integer = (Integer) customNameCount.get(log.getName());
                    if (integer == null) {
                        customNameCount.put(log.getName(), 1);
                    } else {
                        customNameCount.put(log.getName(), integer++);
                    }
                }
            }
        }

        if (cardioTotalTimeHr != 0) {
            cardioAverageSpeed = cardioTotalDistanceMi / cardioTotalTimeHr;
        }
        if (strengthCount != 0) {
            strengthAverageReps = strengthTotalReps / strengthCount;
            strengthAverageSets = strengthTotalSets / strengthCount;
            strengthAverageWeightLb = strengthTotalWeightLb / strengthCount;
        }


        // HTML formatted string that shows all data. Decimals are rounded four places.
        String s = "<center>";

        if (cardioCount != 0) {
            s += "<b> Cardio </b> (" + cardioCount + ") <br>";

            s += "Total Distance: ";
            s += (double) Math.round(cardioTotalDistanceMi * 10000) / 10000 + " mi <br>";

            s += "Total Time: ";
            s += (double) Math.round(cardioTotalTimeHr * 10000) / 10000 + " hr <br>";

            s += "Average Speed: ";
            s += (double) Math.round(cardioAverageSpeed * 10000) / 10000 + " mi/hr <br>";
        }

        if (strengthCount != 0) {
            s += "<br> <b> Strength </b> (" + strengthCount + ")<br>";
            s += "Average Sets: ";
            s += (double) Math.round(strengthAverageSets * 10000) / 10000 + "<br>";

            s += "Average Reps: ";
            s += (double) Math.round(strengthAverageReps * 10000) / 10000 + "<br>";


            s += "Average Weight: ";
            s += (double) Math.round(strengthAverageWeightLb * 10000) / 10000 + " lbs <br>";

            s += "Heaviest Weight: ";
            s += (double) Math.round(strengthAverageWeightLb * 10000) / 10000 + " lbs <br>";
        }

        if (customCount != 0) {
            s += "<br> <b> Custom </b> (" + customCount + ")<br>";
            Set<Map.Entry<String, Integer>> entrySet = customNameCount.entrySet();
            for (Map.Entry entry : entrySet) {
                s += entry.getKey() + ": " + entry.getValue() + "<br>";

            }
        }

        s += "</center>";

        return s;
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
            Log.d("Hello","Here: " + logList.contains(oldLog));
            Log.d("fields","Here: name: " + oldLog.getName() + ". DateCompare: " + logList.get(0).getDateCompare() +  ". Type: " + oldLog.getType() + ". Date: " + oldLog.getDate() + ". Time: " + oldLog.getDistance() + ". Mood: " + oldLog.getMood() + ". Weight: " + oldLog.getWeight() + ". Sets: " + oldLog.getSets() + ". Reps: " + oldLog.getReps() + ". Memo: " + oldLog.getMemo() + ". Type: " + oldLog.getType() + ". Subtype: " + oldLog.getSubtype() + oldLog.getCardioUnit() + "<cardiounit. " + oldLog.getStrengthUnit());
            Log.d("fields","Here: name: " + logList.get(0).getName() + ". DateCompare: " + logList.get(0).getDateCompare() + ". Type: " + logList.get(0).getType() + ". Date: " + logList.get(0).getDate() + ". Time: " + logList.get(0).getDistance() + ". Mood: " + logList.get(0).getMood() + ". Weight: " + logList.get(0).getWeight() + ". Sets: " + logList.get(0).getSets() + ". Reps: " + logList.get(0).getReps() + ". Memo: " + logList.get(0).getMemo() + ". Type: " + logList.get(0).getType() + ". Subtype: " + logList.get(0).getSubtype() + logList.get(0).getCardioUnit() + "<cardiounit. " + logList.get(0).getStrengthUnit());
            Log.d("fields logList(0)","Here: " + logList.get(0).getCardioUnit() + "< cardio unit. " + logList.get(0).getStrengthUnit() + "< strength unit");

            logList.remove(oldLog); //...if so, delete the log
        }
        else { //...if not, we're editing the log
            int myIndex = logList.indexOf(oldLog); //find the index of the oldLog
            logList.set(myIndex,newLog); //set the old log to the new log
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

        //convert to JSON
        String jsonLog = gson.toJson(logList);
        //save to a .txt file
        FileOutputStream fos = mContext.openFileOutput("jsonLogs.json", Context.MODE_PRIVATE);
        //write to internal storage
        fos.write(jsonLog.getBytes());
        fos.close();
    }
}
