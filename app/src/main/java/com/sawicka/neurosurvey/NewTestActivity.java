package com.sawicka.neurosurvey;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

public class NewTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);

        ListView lv_q1 = setListView(R.id.lv_q1, R.array.q1, true);
        ListView lv_q2a = setListView(R.id.lv_q2a, R.array.q2a, true);
        ListView lv_q2b = setListView(R.id.lv_q2b, R.array.q2b, true);
        ListView lv_q3 = setListView(R.id.lv_q3, R.array.q3, true);
        ListView lv_q4 = setListView(R.id.lv_q4, R.array.q4, true);
        ListView lv_q5 = setListView(R.id.lv_q5, R.array.q5, true);
        ListView lv_q6 = setListView(R.id.lv_q6, R.array.q6, true);
        setSeekBarLabel(R.id.q7_layout_label);
        ListView lv_q8 = setListView(R.id.lv_q8, R.array.q8, true);
        ListView lv_q9 = setListView(R.id.lv_q9, R.array.q9, false);
        ListView lv_q10 = setListView(R.id.lv_q10, R.array.q10, true);
        ListView lv_q11 = setListView(R.id.lv_q11, R.array.q11, true);
        ListView lv_q13a = setListView(R.id.lv_q13a, R.array.q13a, true);
        ListView lv_q13b = setListView(R.id.lv_q13b, R.array.q13b, true);
        ListView lv_q14 = setListView(R.id.lv_q14, R.array.q14, true);
        setSeekBarLabel(R.id.q15_layout_label);
        setSeekBarLabel(R.id.q16_layout_label);
        setSeekBarLabel(R.id.q17_layout_label);
        setSeekBarLabel(R.id.q18_layout_label);
        ListView lv_q19 = setListView(R.id.lv_q19, R.array.q19, true);
        ListView lv_q20 = setListView(R.id.lv_q20, R.array.q20, true);
        ListView lv_q21 = setListView(R.id.lv_q21, R.array.q21, true);
        ListView lv_q22 = setListView(R.id.lv_q22, R.array.q22, true);
        ListView lv_q23 = setListView(R.id.lv_q23, R.array.q23, false);
        ListView lv_q24 = setListView(R.id.lv_q24, R.array.q24, false);
        ListView lv_q27 = setListView(R.id.lv_q27, R.array.q27, true);
        setSeekBarLabel(R.id.q28_layout_label);
        ListView lv_q29 = setListView(R.id.lv_q29, R.array.q29, true);
        ListView lv_q30 = setListView(R.id.lv_q30, R.array.q30, true);
        ListView lv_q31 = setListView(R.id.lv_q31, R.array.q31, false);
    }

    public ListView setListView(int viewId, int stringArrayId, boolean ifSingle){
        ListView lv_q = (ListView) findViewById(viewId);
        List<String> q_arr = Arrays.asList(getResources().getStringArray(stringArrayId));
        if (ifSingle) {
            lv_q.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, q_arr));
        } else {
            lv_q.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, q_arr));
        }
        lv_q.setOnItemClickListener(getListener());
        return lv_q;
    }

    public void setSeekBarLabel(int layoutId){
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) findViewById(layoutId);
        View label = inflater.inflate(R.layout.label_view, layout, false);
        layout.addView(label);
    }

    public AdapterView.OnItemClickListener getListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        };
    }

    private int getCheckedRadioButtonId(int id){
        RadioGroup group = (RadioGroup) findViewById(id);
        return group.getCheckedRadioButtonId();
    }
}
