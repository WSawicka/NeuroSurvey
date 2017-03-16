package com.sawicka.neurosurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatientPersonalDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_details);
    }

    public void goNext(View view){
        Intent intent = new Intent(this, NewTestActivity.class);
        startActivity(intent);

    }
}
