package com.sawicka.neurosurvey.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.enums.questions.RadioQuestEnum;
import com.sawicka.neurosurvey.enums.questions.SeekQuestEnum;
import com.sawicka.neurosurvey.presenter.SurveyPresenter;
import com.sawicka.neurosurvey.utils.NoScrollListView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTestActivity extends Activity {
    private AppTempData appTempData;
    private SurveyPresenter presenter;

    @BindViews({R.id.other_2b, R.id.other_9, R.id.other_11, R.id.other_23, R.id.other_24, R.id.other_31})
    List<EditText> others;
    @BindView(R.id.added_listView_12) NoScrollListView added_lv_12;
    @BindView(R.id.added_listView_25) NoScrollListView added_lv_25;
    @BindView(R.id.edit_text_12) EditText edit_text_12;
    @BindView(R.id.edit_text_25) EditText edit_text_25;
    @BindView(R.id.comments) EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        ButterKnife.bind(this);

        this.appTempData = getIntent().getParcelableExtra("APP_DATA");
        this.presenter = this.appTempData.getSurveyPresenter();

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

    @OnClick(R.id.button_add_12)
    public void addToListView12(){
        presenter.addNewToListView(added_lv_12.getId(), edit_text_12.getText().toString(), getApplicationContext());
        added_lv_12.setAdapter(new ArrayAdapter<>(
                this, R.layout.label_view_added_text, presenter.getList(added_lv_12.getId(), getApplicationContext())));
        edit_text_12.setText("");
    }

    @OnClick(R.id.button_add_25)
    public void addToListView25(){
        presenter.addNewToListView(added_lv_25.getId(), edit_text_25.getText().toString(), getApplicationContext());
        added_lv_25.setAdapter(new ArrayAdapter<>(
                this, R.layout.label_view_added_text, presenter.getList(added_lv_25.getId(), getApplicationContext())));
        edit_text_25.setText("");
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarListener(){
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                presenter.addNewSeekBarValue(seekBar.getId(), progressValue, getApplicationContext());
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
                presenter.addNewRadioQuestion(parent.getId(), position, getApplicationContext());
            }
        };
    }

    public AdapterView.OnItemClickListener getCheckListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.addNewCheckQuestion(parent.getId(), position, getApplicationContext());
            }
        };
    }

    @OnClick(R.id.button_save)
    public void handleSave(){
        presenter.setOtherAnswersText(others, getApplicationContext());
        if(comments.getText() != null)
            presenter.setComments(comments.getText().toString());
    }
}
