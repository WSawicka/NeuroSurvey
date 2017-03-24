package com.sawicka.neurosurvey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.enums.questions.RadioQuestEnum;
import com.sawicka.neurosurvey.enums.questions.SeekQuestEnum;
import com.sawicka.neurosurvey.presenter.SurveyPresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public class NewTestActivity extends Activity {
    private SurveyPresenter presenter = new SurveyPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        ButterKnife.bind(this);

        setRadioListViews();
        setCheckListViews();
        setSeekBarLabels();
    }

    private void setRadioListViews(){
        for(int i = 0; i < RadioQuestEnum.values().length; i++) {
            ListView lv = setListView(RadioQuestEnum.values()[i].getQuestionId(), RadioQuestEnum.values()[i].getOptionsArrayId(), true);
            lv.setOnItemClickListener(getRadioListener());
        }
    }

    private void setCheckListViews(){
        for(int i = 0; i < CheckQuestEnum.values().length; i++) {
            ListView lv = setListView(CheckQuestEnum.values()[i].getQuestionId(), CheckQuestEnum.values()[i].getOptionsArrayId(), false);
            lv.setOnItemClickListener(getCheckListener());
        }
    }

    public ListView setListView(int viewId, int stringArrayId, boolean ifSingle){
        ListView lv_q = (ListView) findViewById(viewId);
        List<String> q_arr = Arrays.asList(getResources().getStringArray(stringArrayId));
        if (ifSingle) {
            lv_q.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, q_arr));
        } else {
            lv_q.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, q_arr));
        }
        return lv_q;
    }

    private void setSeekBarLabels(){
        for(int i = 0; i < SeekQuestEnum.values().length; i++) {
            LayoutInflater inflater = LayoutInflater.from(this);
            SeekQuestEnum sqe = SeekQuestEnum.values()[i];

            LinearLayout layout = (LinearLayout) findViewById(sqe.getQuestionId());
            View label = inflater.inflate(sqe.getOptionsArrayId(), layout, false);
            layout.addView(label);

            SeekBar seekBar = (SeekBar) findViewById(sqe.getSeekBarId());
            seekBar.setOnSeekBarChangeListener(getSeekBarListener());
        }
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarListener(){
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                presenter.addNewSeekBarValue(seekBar.getId(), progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    public AdapterView.OnItemClickListener getRadioListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.addNewRadioQuestion(parent.getId(), position);
            }
        };
    }

    public AdapterView.OnItemClickListener getCheckListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.addNewCheckQuestion(parent.getId(), position);
            }
        };
    }
}
