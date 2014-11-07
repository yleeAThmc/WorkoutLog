package com.cs121.team2.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class WOLog implements Parcelable {
    // Data stored in log
    private String type = null;
    private String time = null;
    private String distance = null;
    private String mood = null;
    private String weight = null;
    private String sets = null;
    private String reps = null;
    private String memo = null;
    static String[] MOOD_ARRAY = {"awful", "bad", "k", "good", "perfect"};

    //time stuff
    private String date;
    private int dateCompare = 0;

    public WOLog()
    {

    }

	/*// Constructor for Running case
	//in future, possible use of flag to show how constructor should deal
	// or calling of the setter functions in the entry activity
	public WOLog(String type, String date, String time, String dist, String mood)
	{
		setType(type);
		setDate(date);
		setTime(time);
		setDistance(dist);
		setMood(mood);
	}*/

    // To String
    public String toString(){
        String s = "";

        if(date != null){
            s += "Date: " + date + "\n";
        }

        s += "~" + type + "~ \n";

        if(time != null){
            s += "Time: " + time + "\n";
        }
        if(distance != null){
            s += "Distance: " + distance + "\n";
        }
        if(mood != null){
            s += "Mood: " + mood + "\n";
        }
        if(memo != null) {
            s += "Memo: " + memo + "\n";
        }

        return s;
    }

    //toString for log list activity, including HTML formatting
    public String toStringList(){
        String s = "";

        s += "<center><b>" + type + "</b><br>";

        if(date != null){
            s += "<b>Date: </b>" + date + "<br>";
        }
        if(mood != null){
            s += "<b>Mood: </b>" + mood + "<br>";
        }

        s += "</center>";

        return s;
    }

    // To String with HTML formatting for Detail view
    public String toStringHTML(){
        String s = "";

        s += "<center><b>" + type + "</b><br>";

        if(date != null){
            s += "<b>Date: </b>" + date + "<br>";
        }

        if(time != null){
            s += "<b>Time: </b>" + time + "<br>";
        }
        if(distance != null){
            s += "<b>Distance: </b>" + distance + "<br>";
        }
        if(mood != null){
            s += "<b>Mood: </b>" + mood + "<br>";
        }
        if(memo != null) {
            s += "<b>Memo: </b>" + memo + "<br>";
        }
        if(weight != null) {
            s += "<b>Weight: </b>" + weight + "<br>";
        }
        if(sets != null) {
            s += "<b>Sets: </b>" + sets + "<br>";
        }
        if(reps != null) {
            s += "<b>Reps: </b>" + reps + "<br>";
        }

        s += "</center>";

        return s;
    }

    // Getter Methods

    public String getType(){
        return type;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getDistance(){
        return distance;
    }

    public String getMood(){
        return mood;
    }

    public String getMemo() { return memo; }

    // Setter Methods
    public void setType(String t){
        type = t;
    }

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

    public void setTime(String t){
        time = t;
    }

    public void setDistance(String d){
        distance = d;
    }

    public void setReps(String r){
        reps = r;
    }

    public void setSets(String s){
        sets = s;
    }

    public void setWeight(String w){
        weight = w;
    }

    public void setMood(String m){
        mood = m;
    }

    public void setMemo(String m) { memo = m; }

    public int getDateCompare(){
        return dateCompare;
    }




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
        dest.writeString(type);
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
        type = in.readString();
        time = in.readString();
        distance = in.readString();
        mood = in.readString();
        date = in.readString();
        memo = in.readString();
        weight = in.readString();
        sets = in.readString();
        reps = in.readString();
    }
}
