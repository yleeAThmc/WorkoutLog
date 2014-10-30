package com.cs121.team2.workoutlog;
import java.util.*;

public class WOLog {
    // Data stored in log
    private HashMap dataHM;

    //time stuff
    private String date;
    private int dateCompare = 0;

    // static string stuff

    static final String TIME = "time";
    static final String MOOD = "mood";
    static final String DIST = "dist";
    static final String WGT = "wgt";
    static final String DURATION = "duration";
    static final String EXERCISE_NAME = "exercise_name";
    static final String DIST_UNIT = "dist_unit";
    static final String WGT_UNIT = "wgt_unit";
    static final String SETS = "sets";
    static final String REPS = "reps";


    public WOLog() {
        dataHM = new HashMap();
    }

    public String detailPrint() {
        String s = "";
        Iterator i = dataHM.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            s += me.getKey() + ": ";
            s += me.getValue();
            s += "\n";
        }
        return s;
    }

    // To String
    public String toString() {
        String s = "";
        Iterator i = dataHM.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            s += me.getKey() + ": ";
            s += me.getValue();
            s += "\n";
        }
        return s;
    }

    // Getter Methods

    public Object getData(String key) {
        return dataHM.get(key);
    }


    public void setDate(int m, int dy, int yr, int hr, int min) {
        date = m + "-" + dy + "-" + yr + " " + hr + ":";
        if (min < 10) date += "0" + min;
        else date += min;

        dateCompare += min;
        dateCompare += hr * 10;
        dateCompare += dy * 1000;
        dateCompare += m * 100000;
        dateCompare += yr * 10000000;
    }

    public void setData(String field, Object data) {
        dataHM.put(field, data);
    }

    public int getDateCompare() {
        return dateCompare;
    }
}

