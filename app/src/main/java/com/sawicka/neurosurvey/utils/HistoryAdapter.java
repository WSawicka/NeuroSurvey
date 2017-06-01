package com.sawicka.neurosurvey.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sawicka.neurosurvey.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mloda on 18.05.17.
 */

public class HistoryAdapter extends BaseAdapter {
    public ArrayList<? extends HashMap<String, String>> list;
    private Activity activity;
    private TextView name;
    private TextView age;
    private TextView date;

    public HistoryAdapter(Activity activity, ArrayList<? extends HashMap<String, String>> list){
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null){
            convertView = inflater.inflate(R.layout.history_row_layout, null);
            name = (TextView) convertView.findViewById(R.id.history_name);
            age = (TextView) convertView.findViewById(R.id.history_age);
            date = (TextView) convertView.findViewById(R.id.history_date);
        }

        HashMap<String, String> map = list.get(position);
        name.setText(map.get("name"));
        age.setText(map.get("age"));
        date.setText(map.get("date"));

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
