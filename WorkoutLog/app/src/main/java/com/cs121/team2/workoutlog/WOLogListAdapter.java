package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListAdapter extends ArrayAdapter<WOLog> {
    private final String TAG = "WOLogListAdapter";

    Context mContext;
    ArrayList<WOLog> data = null;

    ArrayList<WOLog> originalDataToFilter;
    ActivityFilter mactivityFilter;


    public WOLogListAdapter(Context ctx, ArrayList<WOLog> data) {
        super(ctx, android.R.layout.simple_list_item_1, data);
        this.mContext = ctx;
        this.data = data;
        this.originalDataToFilter = new ArrayList<WOLog>();
        if (this.data != null) {
            this.originalDataToFilter.addAll(this.data);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //inflate layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_wolog, parent, false);
        }
        WOLog logItem = data.get(position);

        if (logItem.getMood().equals(WOLog.MOOD_ARRAY[0])) {
            convertView.setBackgroundResource(R.drawable.list_awful);
        } else if (logItem.getMood().equals(WOLog.MOOD_ARRAY[1])) {
            convertView.setBackgroundResource(R.drawable.list_bad);
        } else if (logItem.getMood().equals(WOLog.MOOD_ARRAY[2])) {
            convertView.setBackgroundResource(R.drawable.list_ok);
        } else if (logItem.getMood().equals(WOLog.MOOD_ARRAY[3])) {
            convertView.setBackgroundResource(R.drawable.list_good);
        } else {
            convertView.setBackgroundResource(R.drawable.list_perfect);
        }
        TextView textViewItem = (TextView) convertView.findViewById(R.id.log_date);
        String sourceString = logItem.toStringList(); //source string for HTML formatting of setText
        textViewItem.setText(Html.fromHtml(sourceString));

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public WOLog getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //gets the data currently in the list
    public ArrayList<WOLog> getData(){
        return data;
    }

    //for filtering purposes
    public Filter getFilter() {
        if (mactivityFilter == null) {
            mactivityFilter = new ActivityFilter();
        }
        return mactivityFilter;
    }

    //*************private class to deal with filtering activities********************

    private class ActivityFilter extends Filter {


        /*
        * So, every time either thing gets changed, the time must be filtered first, then
        * the type must be filtered. Time is always done off of the original set, then type
        * is done off of data that was changed by Time.
        */

        //for filtering purposes by date, getting length of a month
        int[] daysOfMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //used with edit text
            StringTokenizer tokens= new StringTokenizer(constraint.toString().toLowerCase(), ":");
            String timeConstraint = tokens.nextToken();
            String typeConstraint = tokens.nextToken();
           // Log.d("FILTER", "my time is: '" + timeConstraint +"'");
            //Log.d("FILTER", "my type is: '" + typeConstraint +"'");
            //used for date compare with spinner
            WOLog todaysDateInfo = new WOLog();
            Date rightNow = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(rightNow);
            int woMonth = cal.get(Calendar.MONTH) + 1; // Jan == 0
            int woDay =  cal.get(Calendar.DAY_OF_MONTH); // day 1 == 1
            int woYear =  cal.get(Calendar.YEAR);
            int woHour =  cal.get(Calendar.HOUR_OF_DAY); // midnight == 0
            int woMinute =  cal.get(Calendar.MINUTE); // minute 0 == 0
            todaysDateInfo.setDate(woMonth, woDay, woYear, woHour, woMinute);
            int timeComparison = todaysDateInfo.getDateCompare();
            String dateInString = todaysDateInfo.getDate();

            ArrayList<String> dateKeywords =  new ArrayList<String>();
            dateKeywords.add("all time");
            dateKeywords.add("last week");
            dateKeywords.add("last 2 weeks");
            dateKeywords.add("today");
            dateKeywords.add("last month");
            dateKeywords.add("last 6 months");

            //these might be a leftover from a different filter implementation--let's
            //see if we actually need them during refactoring or not
            ArrayList<String> typeKeywords =  new ArrayList<String>();
            typeKeywords.add("all workouts");
            typeKeywords.add("cardio");
            typeKeywords.add("strength");
            typeKeywords.add("custom");

            FilterResults result = new FilterResults();
            //Log.d("TIME FILTER", "my datecompare is: " + timeComparison);
           // Log.d("FILTER", "m: " + todaysDateInfo.getMonth() + " d: " + todaysDateInfo.getDay() + " y: " + todaysDateInfo.getYear());

            //First, we sort using time on data
            ArrayList<WOLog> filteredItemsTime = new ArrayList<WOLog>();
              //  Log.d("Filter", "DOING TIME RIGHT NOW");
                if (timeConstraint.equals("today")) {
                //    Log.d("Filter", "into the last day case");
                    filteredItemsTime.clear();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                  //      Log.d("FILTER w", "w's m: " + w.getMonth() + " w's d: " + w.getDay() + " w's y: " + w.getYear());
                        if (w.getMonth() == todaysDateInfo.getMonth()
                                && w.getDay() == todaysDateInfo.getDay() &&
                                w.getYear() == todaysDateInfo.getYear()) {
                          //  Log.d("TIME FILTER", "w's date is: " + w.getDate());
                           // Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItemsTime.add(w);
                        }
                    }


                }
                if (timeConstraint.equals("last week")) {
                      //  Log.d("Filter", "into the last week case");
                    filteredItemsTime.clear();
                        for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                            WOLog w = originalDataToFilter.get(i);
                            if ((w.getMonth() == todaysDateInfo.getMonth()
                                    && w.getYear() == todaysDateInfo.getYear()
                                    && w.getDay() >= (todaysDateInfo.getDay()-7))
                                    || (w.getYear() == todaysDateInfo.getYear()
                                    && w.getMonth() == (todaysDateInfo.getMonth()-1) &&
                                    w.getDay() >= (daysOfMonth[w.getMonth()-1] - (7 - todaysDateInfo.getDay())))
                                    || (todaysDateInfo.getMonth() == 1 && todaysDateInfo.getDay() <= 7
                                    && w.getYear() == (todaysDateInfo.getYear() -1) && w.getMonth() == 12 &&
                                        w.getDay() >= (31-(7-todaysDateInfo.getDay()))   )
                                    ) {
                                // Log.d("TIME FILTER", "w's date is: " + w.getDate());
                                //Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                                filteredItemsTime.add(w);
                            }
                        }

                    }
                if (timeConstraint.equals("last 2 weeks")) {
                   // Log.d("Filter", "into the last 2 wks case");
                    filteredItemsTime.clear();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if ((w.getMonth() == todaysDateInfo.getMonth()
                                && w.getYear() == todaysDateInfo.getYear()
                                && w.getDay() >= (todaysDateInfo.getDay()-14))
                                || (w.getYear() == todaysDateInfo.getYear()
                                && w.getMonth() == (todaysDateInfo.getMonth()-1) &&
                                w.getDay() >= (daysOfMonth[w.getMonth()-1] - (14 - todaysDateInfo.getDay())))
                                || (todaysDateInfo.getMonth() == 1 && todaysDateInfo.getDay() <= 14
                                && w.getYear() == (todaysDateInfo.getYear() -1) && w.getMonth() == 12 &&
                                w.getDay() >= (31-(14-todaysDateInfo.getDay()))   )
                                ) {
                          //  Log.d("TIME FILTER", "w's date is: " + w.getDate());
                          //  Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItemsTime.add(w);
                        }
                    }

                }
                if (timeConstraint.equals("last month")) {
                   // Log.d("Filter", "into the last mo case");
                    filteredItemsTime.clear();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if ( (w.getYear() == todaysDateInfo.getYear() &&
                                ((w.getMonth() == todaysDateInfo.getMonth() && w.getDay() <= todaysDateInfo.getDay()) ||
                                (w.getMonth() == todaysDateInfo.getMonth()-1 && todaysDateInfo.getDay() <= w.getDay())  )   )
                                || (todaysDateInfo.getMonth() ==1 && w.getYear() == todaysDateInfo.getYear()-1
                                && w.getMonth() == 12 && w.getDay() >= todaysDateInfo.getDay())) {
                         //   Log.d("TIME FILTER", "w's date is: " + w.getDate());
                         //   Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItemsTime.add(w);
                        }
                    }


                }
                if (timeConstraint.equals("last 6 months")) {
                    //Log.d("Filter", "into the last 6 mo case");
                    filteredItemsTime.clear();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if ( (w.getMonth() >= (todaysDateInfo.getMonth()-6)
                                && w.getYear() == todaysDateInfo.getYear()
                                && w.getDay() >= todaysDateInfo.getDay())
                                || (w.getYear() == todaysDateInfo.getYear()-1 &&
                                w.getMonth() >= (12-(todaysDateInfo.getMonth()-6)) &&
                                w.getDay() >= todaysDateInfo.getDay())) {
                         //   Log.d("TIME FILTER", "w's date is: " + w.getDate());
                         //   Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItemsTime.add(w);
                        }
                    }

                }

                if (timeConstraint.equals("all time")) {
                  //  Log.d("Filter", "into the all time case");
                    filteredItemsTime.clear();
                    synchronized (this) {
                        filteredItemsTime = originalDataToFilter;
                    }
                }


                //Log.d("Filter", "DOING TYPE RIGHT NOW");
                //and now we take what we had and filter by the type
                if (typeConstraint.equals("all workouts")){
                   // Log.d("Filter", "into the all workouts case");
                    ArrayList<WOLog> filteredItemsType = new ArrayList<WOLog>();
                    for (int i = 0, l = filteredItemsTime.size(); i < l; i++) {
                        WOLog w = filteredItemsTime.get(i);
                        //Log.d("TYPE FILTER", "adding this thing:" + w.getType());
                        filteredItemsType.add(w);

                    }
                    result.count = filteredItemsType.size();
                    result.values = filteredItemsType;

                }
                if (typeConstraint.equals("cardio")){
                   // Log.d("Filter", "into the cardio case");
                    ArrayList<WOLog> filteredItemsType = new ArrayList<WOLog>();
                    for (int i = 0, l = filteredItemsTime.size(); i < l; i++) {
                        WOLog w = filteredItemsTime.get(i);
                        if (w.getType().toLowerCase().contains(typeConstraint)) {
                            //Log.d("TYPE FILTER", "adding this thing:" + w.getType());
                            filteredItemsType.add(w);
                        }
                    }
                    result.count = filteredItemsType.size();
                    result.values = filteredItemsType;

                }
                if (typeConstraint.equals("strength")){
                    //Log.d("Filter", "into the strength case");
                    ArrayList<WOLog> filteredItemsType = new ArrayList<WOLog>();
                    for (int i = 0, l = filteredItemsTime.size(); i < l; i++) {
                        WOLog w = filteredItemsTime.get(i);
                        if (w.getType().toLowerCase().contains(typeConstraint)) {
                            // Log.d("TYPE FILTER", "adding this thing:" + w.getType());
                            filteredItemsType.add(w);
                        }
                    }
                    result.count = filteredItemsType.size();
                    result.values = filteredItemsType;

                }
                if (typeConstraint.equals("custom")){
                   // Log.d("Filter", "into the custom case");
                    ArrayList<WOLog> filteredItemsType = new ArrayList<WOLog>();
                   // Log.d("filter", "filteredItemsTIME size: " + filteredItemsTime.size());
                    for (int i = 0, l = filteredItemsTime.size(); i < l; i++) {
                        WOLog w = filteredItemsTime.get(i);
                        if (w.getType().toLowerCase().contains(typeConstraint)) {
                            // Log.d("TYPE FILTER", "adding this thing:" + w.getType());
                            filteredItemsType.add(w);
                        }
                    }

                    result.count = filteredItemsType.size();
                    result.values = filteredItemsType;
                }
//            Log.d("filter", "results count: " + result.count);
//            Log.d("filter", "result values: " + result.values);
            return result;

        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
                data = (ArrayList<WOLog>) results.values;
                notifyDataSetChanged();
        }

    }
}


