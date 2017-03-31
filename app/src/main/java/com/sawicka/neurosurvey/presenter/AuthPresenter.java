package com.sawicka.neurosurvey.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;

/**
 * Created by mloda on 28.03.17.
 */

public class AuthPresenter implements Parcelable {
    private GoogleApiClient client;

    public void setUpClient(Activity activity, Context context){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_FILE))
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.CLOUD_SAVE))
                .requestEmail()
                .build();

        this.client = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) activity, (GoogleApiClient.OnConnectionFailedListener) activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Drive.API)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) activity)
                .build();
    }

    public GoogleApiClient getClient(){
        if(!this.client.isConnected())
            clientConnect();
        return this.client;
    }

    public void clientConnect(){
        this.client.connect();
    }

    public Intent startSignInActivity(){
        return Auth.GoogleSignInApi.getSignInIntent(this.client);
    }

    public GoogleSignInAccount getClientAuthorized(Intent data){
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        return result.getSignInAccount();
    }

    public void setGoogleDriveFile(){
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .build(client);
        /*try {
            startIntentSenderForResult(
                    intentSender, REQUEST_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.client, flags);
    }

    public AuthPresenter() {
    }

    protected AuthPresenter(Parcel in) {
        this.client = in.readParcelable(GoogleApiClient.class.getClassLoader());
    }

    public static final Parcelable.Creator<AuthPresenter> CREATOR = new Parcelable.Creator<AuthPresenter>() {
        @Override
        public AuthPresenter createFromParcel(Parcel source) {
            return new AuthPresenter(source);
        }

        @Override
        public AuthPresenter[] newArray(int size) {
            return new AuthPresenter[size];
        }
    };
}
