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

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListAdapter extends ArrayAdapter<WOLog> {
    private final String TAG = "WOLogListAdapter";

    Context mContext;
    ArrayList<WOLog> data = null;
    ActivityFilter mactivityFilter;
    ArrayList<WOLog> originalDataToFilter;


    public WOLogListAdapter(Context ctx, ArrayList<WOLog> data) {
        super(ctx, android.R.layout.simple_list_item_1, data);
        this.mContext = ctx;
        this.data = data;
        this.mactivityFilter = null;
        this.originalDataToFilter = new ArrayList<WOLog>();
        this.originalDataToFilter.addAll(this.data);
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

    //for filtering purposes
    public Filter getFilter() {
        if (mactivityFilter == null) {
            mactivityFilter = new ActivityFilter();
        }
        return mactivityFilter;
    }

    //*************private class to deal with filtering activities********************

    private class ActivityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //used with edit text
            String searchconstraint = constraint.toString().toLowerCase();
            Log.d("FILTER", "my string is: '" + searchconstraint +"'");
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

            ArrayList<String> dateKeywords =  new ArrayList<String>();
            dateKeywords.add("all time");
            dateKeywords.add("last week");
            dateKeywords.add("last 2 weeks");
            dateKeywords.add("last day");
            dateKeywords.add("last month");
            dateKeywords.add("last 6 months");
            FilterResults result = new FilterResults();

            //if there is text entered to search by
            if (searchconstraint != null && searchconstraint.length() > 0 ) {
                ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                for (int i = 0, l = data.size(); i < l; i++) {
                    WOLog w = data.get(i);
                    if (w.getType().toLowerCase().contains(searchconstraint)) {
                        filteredItems.add(w);
                    }
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }

            //if the filtering is done by time values
            Log.d("TIME FILTER", "my datecompare is: " + timeComparison);
            if (dateKeywords.contains(searchconstraint) && !searchconstraint.equals("all time")){
                Log.d("Filter", "got something to filter w time");
                if (searchconstraint.equals("last day")){
                    Log.d("Filter", "into the last day case");
                    ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if (w.getDateCompare() >= timeComparison-1000) {
                            Log.d("TIME FILTER", "w's date is: " + w.getDate());
                            Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItems.add(w);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;

                }
                if (searchconstraint.equals("last week")){
                    Log.d("Filter", "into the last week case");
                    ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if (w.getDateCompare() >= timeComparison-7000) {
                            Log.d("TIME FILTER", "w's date is: " + w.getDate());
                            Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItems.add(w);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;

                }
                if (searchconstraint.equals("last 2 weeks")){
                    Log.d("Filter", "into the last 2 wks case");
                    ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if (w.getDateCompare() >= timeComparison-14000) {
                            Log.d("TIME FILTER", "w's date is: " + w.getDate());
                            Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItems.add(w);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;

                }
                if (searchconstraint.equals("last month")){
                    Log.d("Filter", "into the last mo case");
                    ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if (w.getDateCompare() >= timeComparison-100000) {
                            Log.d("TIME FILTER", "w's date is: " + w.getDate());
                            Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItems.add(w);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;

                }
                if (searchconstraint.equals("last 6 months")){
                    Log.d("Filter", "into the last 6 mo case");
                    ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();
                    for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                        WOLog w = originalDataToFilter.get(i);
                        if (w.getDateCompare() >= timeComparison-600000) {
                            Log.d("TIME FILTER", "w's date is: " + w.getDate());
                            Log.d("TIME FILTER", "adding w's time compare!: " + w.getDateCompare());
                            filteredItems.add(w);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;

                }
            }
            if (searchconstraint.equals("all time")){
                Log.d("Filter", "into the all time case");
                synchronized (this) {
                    result.values = originalDataToFilter;
                    result.count = originalDataToFilter.size();
                }
            }


            else {
                Log.d("Filter", "into the else case!");
                synchronized (this) {
                    result.values = originalDataToFilter;
                    result.count = originalDataToFilter.size();
                }
            }

            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                data = (ArrayList<WOLog>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}


