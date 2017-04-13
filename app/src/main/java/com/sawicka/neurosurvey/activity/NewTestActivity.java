package com.sawicka.neurosurvey.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.enums.questions.RadioQuestEnum;
import com.sawicka.neurosurvey.enums.questions.SeekQuestEnum;
import com.sawicka.neurosurvey.file.ExcelRead;
import com.sawicka.neurosurvey.file.ExcelWrite;
import com.sawicka.neurosurvey.presenter.AuthPresenter;
import com.sawicka.neurosurvey.presenter.SurveyPresenter;
import com.sawicka.neurosurvey.utils.NoScrollListView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jxl.Cell;

public class NewTestActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private AppTempData appData;
    private SurveyPresenter presenter;
    private AuthPresenter authPresenter = new AuthPresenter();
    private List<Cell[]> rows;
    private GoogleApiClient client;

    @BindViews({R.id.other_2b, R.id.other_9, R.id.other_11, R.id.other_23, R.id.other_24, R.id.other_31})
    List<EditText> others;
    @BindView(R.id.added_listView_12)
    NoScrollListView added_lv_12;
    @BindView(R.id.added_listView_25)
    NoScrollListView added_lv_25;
    @BindView(R.id.edit_text_12)
    EditText edit_text_12;
    @BindView(R.id.edit_text_25)
    EditText edit_text_25;
    @BindView(R.id.comments)
    EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        ButterKnife.bind(this);

        this.appData = getIntent().getParcelableExtra("APP_DATA");
        this.presenter = this.appData.getSurveyPresenter();
        this.authPresenter.setUpClient(this, getApplicationContext());
        this.authPresenter.clientConnect();

        this.appData.getDriveId().asDriveFile()
                .open(authPresenter.getClient(), DriveFile.MODE_READ_ONLY, null)
                .setResultCallback(driveContentsCallbackOpenFile);

        setRadioListViews();
        setCheckListViews();
        setSeekBarLabels();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setRadioListViews() {
        for (int i = 0; i < RadioQuestEnum.values().length; i++) {
            ListView lv = setListView(RadioQuestEnum.values()[i].getQuestionId(), RadioQuestEnum.values()[i].getOptionsArrayId(), true);
            lv.setOnItemClickListener(getRadioListener());
        }
    }

    private void setCheckListViews() {
        for (int i = 0; i < CheckQuestEnum.values().length; i++) {
            ListView lv = setListView(CheckQuestEnum.values()[i].getQuestionId(), CheckQuestEnum.values()[i].getOptionsArrayId(), false);
            lv.setOnItemClickListener(getCheckListener());
        }
    }

    public ListView setListView(int viewId, int stringArrayId, boolean ifSingle) {
        ListView lv_q = (ListView) findViewById(viewId);
        List<String> q_arr = Arrays.asList(getResources().getStringArray(stringArrayId));
        if (ifSingle) {
            lv_q.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, q_arr));
        } else {
            lv_q.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, q_arr));
        }
        return lv_q;
    }

    private void setSeekBarLabels() {
        for (int i = 0; i < SeekQuestEnum.values().length; i++) {
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
    public void addToListView12() {
        presenter.addNewToListView(added_lv_12.getId(), edit_text_12.getText().toString(), getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.label_view_added_text,
                presenter.getList(added_lv_12.getId(), getApplicationContext()));
        added_lv_12.setAdapter(adapter);
        edit_text_12.setText("");
    }

    @OnClick(R.id.button_add_25)
    public void addToListView25() {
        presenter.addNewToListView(added_lv_25.getId(), edit_text_25.getText().toString(), getApplicationContext());
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.label_view_added_text,
                presenter.getList(added_lv_25.getId(), getApplicationContext()));
        added_lv_25.setAdapter(adapter);
        edit_text_25.setText("");
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                presenter.addNewSeekBarValue(seekBar.getId(), progressValue, getApplicationContext());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
    }

    public AdapterView.OnItemClickListener getRadioListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.addNewRadioQuestion(parent.getId(), position, getApplicationContext());
            }
        };
    }

    public AdapterView.OnItemClickListener getCheckListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.addNewCheckQuestion(parent.getId(), position, getApplicationContext());
            }
        };
    }

    @OnClick(R.id.button_save)
    public void handleSave() {
        //presenter.setOtherAnswersText(others, getApplicationContext());
        if (comments.getText() != null)
            presenter.setComments(comments.getText().toString());

        this.appData.getDriveId().asDriveFile()
                .open(authPresenter.getClient(), DriveFile.MODE_WRITE_ONLY, null)
                .setResultCallback(driveContentsCallbackOpenWrite);
    }

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallbackOpenFile =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) { return; }
                    ExcelRead excelRead = new ExcelRead(result);
                    try {
                        rows = excelRead.execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallbackOpenWrite =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        return;
                    }
                    ExcelWrite excelWrite = new ExcelWrite(result, authPresenter.getClient(), rows,
                            appData, getApplicationContext(), getResources());
                    excelWrite.execute();
                }
            };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
