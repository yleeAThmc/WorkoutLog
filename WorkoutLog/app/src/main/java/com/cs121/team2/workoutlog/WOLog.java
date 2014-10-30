package com.cs121.team2.workoutlog;

public class WOLog {
    // Data stored in log
    private String type = null;
    private String time = null;
    private String distance = null;
    private String mood = null;
    // Kelly's
    private String weight = null;
    private String sets = null;
    private String reps = null;
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

        if(mood != null){
            s += "Mood: " + mood + "\n";
        }

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

    public int getDateCompare(){
        return dateCompare;
    }

}
