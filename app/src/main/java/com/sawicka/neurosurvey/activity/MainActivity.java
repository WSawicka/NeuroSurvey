package com.sawicka.neurosurvey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sawicka.neurosurvey.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newTest(View view){
        Intent intent = new Intent(this, PatientPersonalDetailsActivity.class);
        startActivity(intent);
    }
}
