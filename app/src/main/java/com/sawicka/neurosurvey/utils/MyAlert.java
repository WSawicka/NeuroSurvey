package com.sawicka.neurosurvey.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mloda on 24.03.17.
 */

public class MyAlert implements Parcelable {
    public MyAlert(Parcel in) {
    }

    public MyAlert(){

    }

    public static final Creator<MyAlert> CREATOR = new Creator<MyAlert>() {
        @Override
        public MyAlert createFromParcel(Parcel in) {
            return new MyAlert(in);
        }

        @Override
        public MyAlert[] newArray(int size) {
            return new MyAlert[size];
        }
    };

    public AlertDialog showAlertDialog(String text, Context context){
        return new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
