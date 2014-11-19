package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListAdapter extends BaseAdapter {
    private final String TAG = "WOLogListAdapter";

    Context mContext;
    int layoutResourceId;
    ArrayList<WOLog> data = null;
    ActivityFilter mactivityFilter;
    ArrayList<WOLog> originalDataToFilter;

    public WOLogListAdapter(Context mContext, int layoutResourceId, ArrayList<WOLog> data ){
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.mactivityFilter = null;
        this.originalDataToFilter = new ArrayList<WOLog>();
        this.originalDataToFilter.addAll(this.data);
    }

    public WOLogListAdapter(Context mContext, int layoutResourceId){

        ArrayList<WOLog> pulledData = null;
        try {
            pulledData = DataHandler.getDataHandler(this.mContext).getLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = pulledData;
        this.mactivityFilter = null;
        this.originalDataToFilter = new ArrayList<WOLog>();
        this.originalDataToFilter.addAll(this.data);

    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            //inflate layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
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
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //for filtering purposes
    public Filter getFilter() {
        Log.d("FILTER", "hey, making a filter");
        if (mactivityFilter == null)
            mactivityFilter = new ActivityFilter();

        return mactivityFilter;
    }

    //*************private class to deal with filtering activities********************
    private class ActivityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String searchconstraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            Log.d("FILTER", "okay, we're somewhere");
            if(searchconstraint != null && searchconstraint.toString().length() > 0)
            {
                Log.d("FILTER", "made it here!");
                ArrayList<WOLog> filteredItems = new ArrayList<WOLog>();

                for(int i = 0, l = originalDataToFilter.size(); i < l; i++)
                {
                    WOLog w = originalDataToFilter.get(i);

                    if(w.getMood().toLowerCase().contains(searchconstraint))
                        filteredItems.add(w);
                    Log.d("FILTER", "added to new thing!");

                }
                Log.d("FILTER", "length of thing is: " + filteredItems.size());
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
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

            data = (ArrayList<WOLog>)results.values;
            notifyDataSetChanged();
            data.clear();
            for(int i = 0, l = data.size(); i < l; i++)
                data.add(data.get(i));
            notifyDataSetInvalidated();
        }
    }

}

