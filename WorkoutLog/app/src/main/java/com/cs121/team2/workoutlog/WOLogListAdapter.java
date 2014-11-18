package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sam E on 10/7/2014.
 */
public class WOLogListAdapter extends BaseAdapter {


    Context mContext;
    int layoutResourceId;
    ArrayList<WOLog> data = null;

    public WOLogListAdapter(Context mContext, int layoutResourceId, ArrayList<WOLog> data ){
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
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
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            //inflate layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        WOLog logItem = data.get(position);
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

}
