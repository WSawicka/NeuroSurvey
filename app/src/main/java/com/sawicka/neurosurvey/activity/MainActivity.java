package com.sawicka.neurosurvey.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.utils.ImageLoadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.logged_user_name) TextView user_name_value;
    @BindView(R.id.user_image) ImageView user_image;

    private GoogleApiClient client;
    private DriveId driveId;

    private static final int SIGN_IN = 1;
    private static final int RESOLVE_CONNECTION = 2; //trying to connect again
    private static final int REQUEST_OPENER = 3; // trying to open file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Intent intent = getIntent();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_FILE))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.CLOUD_SAVE))
                .requestEmail()
                .build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Drive.API)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
        startActivityForResult(signInIntent, SIGN_IN);
    }

    @OnClick(R.id.new_test_button)
    public void newTest(){
        Intent intent = new Intent(this, PatientPersonalDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESOLVE_CONNECTION) {
            if (resultCode == RESULT_OK) {
                client.connect();
            }
        } else if (requestCode == REQUEST_OPENER && resultCode == RESULT_OK) {
            driveId = data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
        } else if (requestCode == SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                user_name_value.setText("You're logged as " + acct.getDisplayName());
                new ImageLoadTask(acct.getPhotoUrl().toString(), user_image).execute();
            }

            IntentSender intentSender = Drive.DriveApi
                    .newOpenFileActivityBuilder()
                    .build(client);
            /*try {
                startIntentSenderForResult(
                        intentSender, REQUEST_OPENER, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }*/
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION);
            } catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    }
}
