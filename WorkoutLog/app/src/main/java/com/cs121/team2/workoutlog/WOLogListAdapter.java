package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

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

        ArrayList<WOLog> pulledData = DataHandler.getDataHandler().getLogs();

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
        textViewItem.setText(logItem.getDate());

        return convertView;
    }


//TODO: implement sorting function so listview shows newest --> oldest
//    public WOLogListAdapter sortData(){
//        return this;
//    }


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
