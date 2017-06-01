package com.sawicka.neurosurvey.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.file.ExcelRead;
import com.sawicka.neurosurvey.presenter.AuthPresenter;
import com.sawicka.neurosurvey.utils.HistoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Cell;

/**
 * Created by mloda on 18.05.17.
 */

public class HistoryActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private AppTempData appData;
    private final String NAME = "name";
    private final String AGE = "age";
    private final String DATE = "date";
    private AuthPresenter authPresenter = new AuthPresenter();

    @BindView(R.id.history_patients_list_view) ListView historyPatientsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        this.appData = getIntent().getParcelableExtra("APP_DATA");

        this.authPresenter.setUpClient(this, getApplicationContext());
        this.authPresenter.clientConnect();

        this.appData.getDriveId().asDriveFile()
                .open(this.authPresenter.getClient(), DriveFile.MODE_READ_ONLY, null)
                .setResultCallback(driveContentsCallbackOpenFile);
    }

    private ArrayList<HashMap<String, String>> getPatientList(){
        ArrayList<HashMap<String, String>> patientList = new ArrayList<>();
        for(Cell[] cell : appData.getRows()){
            HashMap<String, String> patient = new HashMap<>();
            patient.put(NAME, cell[0].getContents());
            patient.put(AGE, cell[1].getContents());
            patient.put(DATE, cell[3].getContents());
            patientList.add(patient);
        }
        return patientList;
    }

    private void setListView(){
        HistoryAdapter adapter = new HistoryAdapter(this, getPatientList());
        historyPatientsListView.setAdapter(adapter);
    }

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallbackOpenFile =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) { return; }
                    ExcelRead excelRead = new ExcelRead(result);
                    try {
                        appData.setRows(excelRead.execute().get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    setListView();
                }
            };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
