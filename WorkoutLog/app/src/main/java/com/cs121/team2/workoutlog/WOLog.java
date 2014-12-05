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
    final static int UNIT_MI_LB = 0;
    final static int UNIT_M_KG = 1;
    final static int UNIT_KM = 2;

    final static String[] MOOD_ARRAY = {"awful", "bad", "k", "good", "perfect"};
    final static String[] TYPE_ARRAY = {"Cardio", "Strength", "Custom"};
    final static String[] SUBTYPE_ARRAY = {"None", "Time/Body", "Distance/Weights"};
    final static String[] CARDIO_UNIT_ARRAY = {"mi", "m", "km"};
    final static String[] STRENGTH_UNIT_ARRAY = {"lb", "kg"};

    // Data stored in log
    private int dateCompare, month, day, year;
    private String date, name, time, distance, mood, weight, sets, reps, memo, type, subtype,
                   cardioUnit, strengthUnit;

    //tag for debug logging.
    private static final String TAG = "WOLog";

    public WOLog()
    {
        dateCompare = month = day = year = 0;
        date = name = time = distance = mood = weight = sets = reps = memo = type = subtype
                = cardioUnit = strengthUnit = null;
    }


    // Setter Methods
    public void setDate(int m, int dy, int yr, int hr, int min){
        date = m + "-" + dy + "-" + yr + " " + hr + ":";
        if (min < 10) date += "0" + min;
        else date += min;

        month = m;
        day = dy;
        year = yr;
        dateCompare = min;
        dateCompare += hr * 100;
        dateCompare += dy * 10000;
        dateCompare += m * 1000000;
        dateCompare += yr * 100000000;
    }

    public void setName(String t){ name = t; }

    public void setTime(String h, String m, String s){ time = h + ":" + m + ":" + s; }

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

    public int getMonth() {return month;}

    public int getDay() {return day;}

    public int getYear() {return year;}

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
        String s = "";
        s += "<center><b>" + name.toUpperCase() + "</b><br>";
        s += "<b>(Workout Info):</b><br>";

        if(date != null && !date.isEmpty()){
            s += "<b>Date: </b>" + date;
        }

        if(time != null && !time.isEmpty()){
            s +=  "<br>" + "<b>Time: </b>" + time;
        }
        if(mood != null && !mood.isEmpty()){
            s += "<br>" + "<b>Mood: </b>" + mood;
        }
        if(distance != null && !distance.isEmpty() && cardioUnit != null){
            s += "<br>" + "<b>Distance: </b>" + distance + " " + cardioUnit;
        } else if (distance != null && !distance.isEmpty()) {
            s += "<br>" + "<b>Distance: </b>" + distance;
        }
        if(weight != null && !weight.isEmpty() && strengthUnit != null && !strengthUnit.isEmpty()) {
            s += "<br>" + "<b>Weight: </b>" + weight + " " + strengthUnit;
        } else if(weight != null && !weight.isEmpty()) {
            s += "<br>" + "<b>Weight: </b>" + weight;
        }
        if(sets != null && !sets.isEmpty()) {
            s += "<br>" + "<b>Sets: </b>" + sets;
        }
        if(reps != null && !reps.isEmpty()) {
            s += "<br>" + "<b>Reps: </b>" + reps;
        }
        if(memo != null && !memo.isEmpty()) {
            s += "<br>" + "<b>Memo: </b>" + memo;
        }

        s += "</center>";

        return s;
    }

    // TODO: Write comparable function in WOLog instead of overriding in DataHandler?


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        WOLog woLog = (WOLog) o;

        if (cardioUnit != null ? !cardioUnit.equals(woLog.cardioUnit) : woLog.cardioUnit != null)
            return false;
        if (date != null ? !date.equals(woLog.date) : woLog.date != null) return false;
        if (distance != null ? !distance.equals(woLog.distance) : woLog.distance != null)
            return false;
        if (memo != null ? !memo.equals(woLog.memo) : woLog.memo != null) return false;
        if (mood != null ? !mood.equals(woLog.mood) : woLog.mood != null) return false;
        if (name != null ? !name.equals(woLog.name) : woLog.name != null) return false;
        if (reps != null ? !reps.equals(woLog.reps) : woLog.reps != null) return false;
        if (sets != null ? !sets.equals(woLog.sets) : woLog.sets != null) return false;
        if (strengthUnit != null ? !strengthUnit.equals(woLog.strengthUnit) : woLog.strengthUnit != null)
            return false;
        if (subtype != null ? !subtype.equals(woLog.subtype) : woLog.subtype != null) return false;
        if (time != null ? !time.equals(woLog.time) : woLog.time != null) return false;
        if (type != null ? !type.equals(woLog.type) : woLog.type != null) return false;
        if (weight != null ? !weight.equals(woLog.weight) : woLog.weight != null) return false;

        return true;
    }

    //in case this project ever uses hash coding, make sure they know they have to write it
    @Override public int hashCode() {
        throw new UnsupportedOperationException();

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
        dest.writeInt(dateCompare);
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
        dest.writeString(type);
        dest.writeString(subtype);
    }

    private WOLog(Parcel in) {
        dateCompare = 0;
        dateCompare = in.readInt();
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
        type = null;
        type = in.readString();
        subtype = null;
        subtype = in.readString();
    }
}
