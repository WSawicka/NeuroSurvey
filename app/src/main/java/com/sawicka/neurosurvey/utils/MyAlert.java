package com.sawicka.neurosurvey.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by mloda on 24.03.17.
 */

public class MyAlert {
    public AlertDialog showAlertDialog(String text, Context context){
        return new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
