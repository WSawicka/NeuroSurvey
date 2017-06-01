package com.sawicka.neurosurvey.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;
import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.presenter.AuthPresenter;
import com.sawicka.neurosurvey.utils.ImageLoadTask;
import com.sawicka.neurosurvey.utils.MyAlert;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.logged_user_name) TextView user_name_value;
    @BindView(R.id.user_image) ImageView user_image;
    @BindView(R.id.data_file_name) TextView data_file_name;
    @BindView(R.id.logging_button) Button logging_Button;

    private AppTempData appData;
    private AuthPresenter authPresenter = new AuthPresenter();
    private String userName;
    private Metadata metadata;

    private static final int SIGN_IN = 1;
    private static final int RESOLVE_CONNECTION = 2; //trying to connect again
    private static final int REQUEST_OPENER = 3; // trying to open file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appData = new AppTempData();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        authPresenter.setUpClient(this, getApplicationContext());
    }

    @OnClick(R.id.select_file)
    public void selectFile(){
        if(userName != null){
            Drive.DriveApi.newDriveContents(this.authPresenter.getClient())
                    .setResultCallback(driveContentsCallback);
        }
    }

    @OnClick(R.id.logging_button)
    public void logging(){
        if (userName == null) {
            authPresenter.clientConnect();
            startActivityForResult(authPresenter.startSignInActivity(), SIGN_IN);
        } else {
            authPresenter.clientDisconnect();
            user_name_value.setText("");
            userName = null;
            user_image.setImageBitmap(null);
            data_file_name.setText("");
            logging_Button.setText("Log me!");
        }
    }

    @OnClick(R.id.new_test_button)
    public void newTest(){
        Intent intent = new Intent(this, PatientPersonalDetailsActivity.class);
        intent.putExtra("APP_DATA", this.appData);
        startActivity(intent);
    }

    @OnClick(R.id.history_button)
    public void history(){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("APP_DATA", this.appData);
        startActivity(intent);
    }

    @OnClick(R.id.settings_button)
    public void settings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("APP_DATA", this.appData);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESOLVE_CONNECTION && resultCode == RESULT_OK) {
            authPresenter.clientConnect();
        } else if (requestCode == REQUEST_OPENER && resultCode == RESULT_OK) {
            this.appData.setDriveId((DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID));
            DriveFile file = Drive.DriveApi.getFile(authPresenter.getClient(), appData.getDriveId());
            file.getMetadata(authPresenter.getClient()).setResultCallback(metadataRetrievedCallback);
        } else if (requestCode == SIGN_IN) {
            GoogleSignInAccount acct = authPresenter.getClientAuthorized(data);
            if (acct != null) {
                user_name_value.setText("You're logged as: \n" + acct.getDisplayName());
                userName = acct.getDisplayName();
                if(acct.getPhotoUrl() != null)
                    new ImageLoadTask(acct.getPhotoUrl().toString(), user_image).execute();
                logging_Button.setText("Log out");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION);
            } catch (IntentSender.SendIntentException e) {
                new MyAlert().showAlertDialog("Unable to connect", getApplicationContext());
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    }

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
        @Override
        public void onResult(DriveApi.DriveContentsResult result) {
            if(!authPresenter.getClient().isConnected()){
                authPresenter.clientConnect();
            }
            IntentSender intentSender = Drive.DriveApi
                    .newOpenFileActivityBuilder()
                    .setSelectionFilter(Filters.contains(SearchableField.TITLE, ".xls"))
                    .build(authPresenter.getClient());
            try {
                startIntentSenderForResult(intentSender, REQUEST_OPENER, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }

        }
    };

    final ResultCallback<DriveResource.MetadataResult> metadataRetrievedCallback = new
            ResultCallback<DriveResource.MetadataResult>() {
                @Override
                public void onResult(@NonNull DriveResource.MetadataResult result) {
                    if (!result.getStatus().isSuccess()) {
                        return;
                    }
                    metadata = result.getMetadata();
                    data_file_name.setText(metadata.getTitle());
                }
            };
}
