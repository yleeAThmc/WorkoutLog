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
    final static int UNIT_FT_LB = 0;
    final static int UNIT_M_KG = 1;
    final static int UNIT_KM = 2;

    final static String[] MOOD_ARRAY = {"awful", "bad", "k", "good", "perfect"};
    final static String[] TYPE_ARRAY = {"Cardio", "Strength", "Custom"};
    final static String[] SUBTYPE_ARRAY = {"None", "Time/Body", "Distance/Weights"};
    final static String[] CARDIO_UNIT_ARRAY = {"ft", "m", "km"};
    final static String[] STRENGTH_UNIT_ARRAY = {"lb", "kg"};

    // Data stored in log
    private int dateCompare;
    private String date, name, time, distance, mood, weight, sets, reps, memo, type, subtype,
                   cardioUnit, strengthUnit;

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

    public void setSubtype(String st) { subtype = st; }

    public void setCardioUnit(String cu) { cardioUnit = cu; }

    public void setStrengthUnit(String su) { strengthUnit = su; }




    // Getter Methods
    // TODO: Remove these if we don't wind up using them for stats
    public int getDateCompare(){ return dateCompare; }

    public String getDate(){ return date; }

    public String getName(){ return name; }

    public String getTime(){ return time; }

    public String getDistance(){ return distance; }

    public String getReps() { return  reps; }

    public String getSets() { return sets; }

    public String getWeight() { return weight; }

    public String getMood(){ return mood; }

    public String getMemo() { return memo; }

    public String getType() { return type; }

    public String getSubtype() { return subtype; }

    public String getCardioUnit() { return cardioUnit; }

    public String getStrengthUnit() {return strengthUnit; }


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

        Log.e(TAG, "is cardioUnit null: "+cardioUnit);
        String s = "";

        s += "<center><b>" + name.toUpperCase() + "</b><br>";
        s += "<b>(Workout Info):</b><br>";

        if(date != null && !date.isEmpty()){
            s += "<b>Date: </b>" + date + "<br>";
        }

        if(time != null && !time.isEmpty()){
            s += "<b>Time: </b>" + time + "<br>";
        }
        if(mood != null && !mood.isEmpty()){
            s += "<b>Mood: </b>" + mood + "<br>";
        }
        if(distance != null && !distance.isEmpty() && cardioUnit != null){
            s += "<b>Distance: </b>" + distance + " " + cardioUnit + "<br>";
        } else if (distance != null && !distance.isEmpty()) {
            s += "<b>Distance: </b>" + distance + "<br>";
        }
        if(weight != null && !weight.isEmpty() && strengthUnit != null && !strengthUnit.isEmpty()) {
            s += "<b>Weight: </b>" + weight + " " + strengthUnit + "<br>";
        } else if(weight != null && !weight.isEmpty()) {
            s += "<b>Weight: </b>" + weight + "<br>";
        }
        if(sets != null && !sets.isEmpty()) {
            s += "<b>Sets: </b>" + sets + "<br>";
        }
        if(reps != null && !reps.isEmpty()) {
            s += "<b>Reps: </b>" + reps + "<br>";
        }
        if(memo != null && !memo.isEmpty()) {
            s += "<b>Memo: </b>" + memo + "<br>";
        }

        s += "</center>";

        return s;
    }

    // TODO: Write comparable function in WOLog instead of overriding in DataHandler?

    //Equals function
    public boolean equals(WOLog otherLog){
        return (this.getDateCompare() == otherLog.getDateCompare() &&
                this.getDate().equals(otherLog.getDate()) &&
                this.getName().equals(otherLog.getName()) &&
                this.getTime().equals(otherLog.getTime()) &&
                this.getDistance().equals(otherLog.getDistance()) &&
                this.getMood().equals(otherLog.getMood()) &&
                this.getWeight().equals(otherLog.getWeight()) &&
                this.getSets().equals(otherLog.getSets()) &&
                this.getReps().equals(otherLog.getReps()) &&
                this.getMemo().equals(otherLog.getMemo()) &&
                this.getType().equals(otherLog.getType()) &&
                this.getSubtype().equals(otherLog.getSubtype()) &&
                this.getCardioUnit().equals(otherLog.getCardioUnit()) &&
                this.getStrengthUnit().equals(otherLog.getStrengthUnit()));
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
        dest.writeString(cardioUnit);
        dest.writeString(strengthUnit);
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
        cardioUnit = null;
        cardioUnit = in.readString();
        strengthUnit = null;
        strengthUnit = in.readString();
        dateCompare = 0;
    }
}
