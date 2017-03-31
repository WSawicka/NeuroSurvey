package com.sawicka.neurosurvey;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
    private AppTempData appTempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.appTempData = (AppTempData) getIntent().getSerializableExtra("APP_DATA");
    }
}
