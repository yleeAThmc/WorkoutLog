package com.cs121.team2.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class WOLog implements Parcelable {
    // Just a lot of static data
    final static int TYPE_CARDIO = 0;
    final static int TYPE_STRENGTH = 1;
    final static int TYPE_CUSTOM = 2;
    final static int SUBTYPE_NONE = 0;
    final static int SUBTYPE_TIME_BODY = 1;
    final static int SUBTYPE_DIST_WEIGHTS = 2;

    final static String[] MOOD_ARRAY = {"awful", "bad", "k", "good", "perfect"};
    final static String[] TYPE_ARRAY = {"Cardio", "Strength", "Custom"};
    final static String[] SUBTYPE_ARRAY = {"None", "Time/Body", "Distance/Weights"};

    // Data stored in log
    private int dateCompare;
    private String date, name, time, distance, mood, weight, sets, reps, memo, type, subtype;

    //tag for debug logging.
    private static final String TAG = "WOLog";

    public WOLog()
    {
        dateCompare = 0;
        date = name = time = distance = mood = weight = sets = reps = memo = type = subtype = null;
    }


    // Setter Methods
    public void setDate(int m, int dy, int yr, int hr, int min){
        date = m + "-" + dy + "-" + yr + " " + hr + ":";
        if (min < 10) date += "0" + min;
        else date += min;

        dateCompare += min;
        dateCompare += hr * 10;
        dateCompare += dy * 1000;
        dateCompare += m * 100000;
        dateCompare += yr * 10000000;
    }

    public void setName(String t){ name = t; }

    public void setTime(String t){ time = t; }

    public void setDistance(String d){ distance = d; }

    public void setReps(String r){ reps = r; }

    public void setSets(String s){ sets = s; }

    public void setWeight(String w){ weight = w;}

    public void setMood(String m){ mood = m; }

    public void setMemo(String m) { memo = m; }

    public void setType(String t) { type = t; }

    public void setSubtype(String t) { subtype = t; }


    // Getter Methods
    // TODO: Remove these if we don't wind up using them for stats
    public int getDateCompare(){ return dateCompare; }

    public String getName(){ return name; }

    public String getDate(){ return date; }

    public String getTime(){ return time; }

    public String getDistance(){ return distance; }

    public String getMood(){ return mood; }

    public String getMemo() { return memo; }

    public String getType() { return type; }

    public String getSubtype() { return subtype; }


    // toString formatted with HTML for ListView
    public String toStringList(){
        String s = "";

        s += "<center><b>" + name + "</b>";

        if(date != null){
            s += "<br><b>Date: </b>" + date;
        }
        if(mood != null){
            s += "<br><b>Mood: </b>" + mood;
        }

        s += "</center>";

        return s;
    }

    // toString formatted with HTML for DetailView
    public String toStringDetail(){
        String s = "";

        s += "<center><b>" + name.toUpperCase() + "</b><br>";
        s += "<b>(Workout Info):</b><br>";

        if(date != null && !date.isEmpty()){
            s += "<b>Date: </b>" + date + "<br>";
        }

        if(time != null && !time.isEmpty()){
            s += "<b>Time: </b>" + time + "<br>";
        }
        if(distance != null && !distance.isEmpty()){
            s += "<b>Distance: </b>" + distance + "<br>";
        }
        if(mood != null && !mood.isEmpty()){
            s += "<b>Mood: </b>" + mood + "<br>";
        }
        if(memo != null && !memo.isEmpty()) {
            s += "<b>Memo: </b>" + memo + "<br>";
        }
        Log.d(TAG, "weight: ." + weight + ".");
        if(weight != null && !weight.isEmpty()) {
            s += "<b>Weight: </b>" + weight + "<br>";
        }
        if(sets != null && !sets.isEmpty()) {
            s += "<b>Sets: </b>" + sets + "<br>";
        }
        if(reps != null && !reps.isEmpty()) {
            s += "<b>Reps: </b>" + reps + "<br>";
        }

        s += "</center>";

        return s;
    }


    //The following functions allow for a WOLog to be passed as a Parcel
    public static final Parcelable.Creator<WOLog> CREATOR = new Parcelable.Creator<WOLog>() {
        public WOLog createFromParcel(Parcel in) {
            return new WOLog(in);
        }

        public WOLog[] newArray(int size) {
            return new WOLog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(distance);
        dest.writeString(mood);
        dest.writeString(date);
        dest.writeString(memo);
        dest.writeString(weight);
        dest.writeString(sets);
        dest.writeString(reps);
    }

    private WOLog(Parcel in) {
        name = null;
        name = in.readString();
        time = null;
        time = in.readString();
        distance = null;
        distance = in.readString();
        mood = null;
        mood = in.readString();
        date = null;
        date = in.readString();
        memo = null;
        memo = in.readString();
        weight = null;
        weight = in.readString();
        sets = null;
        sets = in.readString();
        reps = null;
        reps = in.readString();
        dateCompare = 0;
    }
}
