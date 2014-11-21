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

import java.io.IOException;
import java.util.ArrayList;

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
        Log.d("FILTER", "hey, making a filter");
        if (mactivityFilter == null) {
            Log.d("FILTER", "first time through!");

            mactivityFilter = new ActivityFilter();
        }
        Log.d("FILTER", "using what we have");
        return mactivityFilter;
    }

    //*************private class to deal with filtering activities********************

    private class ActivityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String searchconstraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            Log.d("FILTER", "okay, we're somewhere");
            if (searchconstraint != null && searchconstraint.toString().length() > 0) {
                Log.d("FILTER", "made it here!");
                ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();

                for (int i = 0, l = originalDataToFilter.size(); i < l; i++) {
                    WOLog w = originalDataToFilter.get(i);
                    Log.d("FILTER", "and it's type is: " + w.getType().toLowerCase());
                    Log.d("FILTER", "and it's name is: " + w.getName().toLowerCase());
                    if (w.getType().toLowerCase().contains(searchconstraint)) {
                        filteredItems.add(w);
                    }
                    Log.d("FILTER", "added to new thing!");

                }
                Log.d("FILTER", "length of thing is: " + filteredItems.size());
                result.count = filteredItems.size();
                result.values = filteredItems;
                Log.d("FILTER", "this is result: " + result.values);
            } else {
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


