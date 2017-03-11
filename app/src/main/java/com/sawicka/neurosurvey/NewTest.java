package com.sawicka.neurosurvey;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class NewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
    }

    public void onClick_1(View view){
        View questions_2_4 = findViewById(R.id.questions_2_4);
        View questions_13 = findViewById(R.id.questions_13);

        RadioGroup group1 = (RadioGroup) findViewById(R.id.quest1_group);
        int checkedId = group1.getCheckedRadioButtonId();

        if(checkedId == R.id.quest1_ans2){
            questions_2_4.setVisibility(View.VISIBLE);
        } else {
            questions_2_4.setVisibility(View.GONE);
            questions_13.setVisibility(View.VISIBLE);
        }
    }
}
