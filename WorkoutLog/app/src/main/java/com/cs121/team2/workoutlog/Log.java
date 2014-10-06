package com.cs121.team2.workoutlog;


// created by Claire Rosburg 10-5-14
public class Log {
	
	// Data stored in log
	private String type;
	private String date;
	private String time;
	private String distance;
	private String mood;
	
	public Log()
	{
		
	}
	
	// Constructor for Running case
	//in future, possible use of flag to show how constructor should deal
	// or calling of the setter functions in the entry activity 
	public Log(String type, String date, String time, String dist, String mood)
	{
		setType(type);
		setDate(date);
		setTime(time);
		setDistance(dist);
		setMood(mood);
	}
	
	// To String
	public String toString(){
		String s = "~" + type + "~ \n";
		
		if(date != null){
			s += "Date: " + date + "\n";
		}
		if(time != null){
			s += "Time: " + time + "\n";
		}
		if(distance != null){
			s += "Distance: " + distance + "\n";
		}
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
	
	public void setDate(String d){
		date = d;
	}
	
	public void setTime(String t){
		time = t;
	}
	
	public void setDistance(String d){
		distance = d;
	}
	
	public void setMood(String m){
		mood = m;
	}
	
}
