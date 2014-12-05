package com.cs121.team2.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {
    private static final int RUNNING = 0;
    private static final int WEIGHT_TRAINING = 1;
    private static final int HARDCORE_KARAOKE = 2;
    private Context _context;
    private String TAG = "Custom Listener";

    public CustomOnItemSelectedListener(Context context) {
        _context = context;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        View insertPoint = ((Activity)_context).findViewById(R.id.entry_view);
        LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
        View fields;
        if (pos == RUNNING) {
            Log.e(TAG, "Running selected");
            //fields = inflater.inflate(R.layout.entry_running, parent, false);
        } else if (pos == WEIGHT_TRAINING) {
            Log.e(TAG, "wt selected");
            //fields = inflater.inflate(R.layout.entry_weight_training, parent, false);
        } else {
            Log.e(TAG, "hk selected");
            //fields = inflater.inflate(R.layout.entry_hardcore_karaoke, parent, false);
        }
        return;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.e(TAG, "nothing selected");
    }

}