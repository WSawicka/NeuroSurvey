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
        View questions_2_12 = findViewById(R.id.questions_2_12);
        View questions_13 = findViewById(R.id.questions_13);

        if(getCheckedRadioButtonId(R.id.quest1_group) == R.id.quest1_ans2){
            questions_2_12.setVisibility(View.VISIBLE);
            questions_13.setVisibility(View.GONE);
        } else {
            questions_2_12.setVisibility(View.GONE);
            questions_13.setVisibility(View.VISIBLE);
        }
    }

    public void onClick_4(View view){
        View questions_5_6 = findViewById(R.id.questions_5_6);
        View questions_7_10 = findViewById(R.id.questions_7_10);

        if(getCheckedRadioButtonId(R.id.quest4_group) == R.id.quest4_ans2){
            questions_5_6.setVisibility(View.GONE);
            questions_7_10.setVisibility(View.VISIBLE);
        } else {
            questions_5_6.setVisibility(View.VISIBLE);
            questions_7_10.setVisibility(View.VISIBLE);
        }
    }

    public void onClick_10(View view){
        View questions_11_12 = findViewById(R.id.questions_11_12);
        View questions_13 = findViewById(R.id.questions_13);

        if(getCheckedRadioButtonId(R.id.quest10_group) == R.id.quest10_ans1){
            questions_11_12.setVisibility(View.GONE);
            questions_13.setVisibility(View.VISIBLE);
        } else {
            questions_11_12.setVisibility(View.VISIBLE);
            questions_13.setVisibility(View.VISIBLE);
        }
    }

    public void onClick_13(View view){
        View questions_14_16 = findViewById(R.id.questions_14_16);
        View questions_17_31 = findViewById(R.id.questions_17_31);

        if(getCheckedRadioButtonId(R.id.quest13b_group) == R.id.quest13b_ans1){
            questions_14_16.setVisibility(View.GONE);
            questions_17_31.setVisibility(View.VISIBLE);
        } else {
            questions_14_16.setVisibility(View.VISIBLE);
            questions_17_31.setVisibility(View.VISIBLE);
        }

        View buttonSave = findViewById(R.id.button_save);
        buttonSave.setVisibility(View.VISIBLE);
    }

    public void onClick_23(View view){
        View checkBoxes = findViewById(R.id.quest23_checkBoxes);
        if(getCheckedRadioButtonId(R.id.quest23_group) == R.id.quest23_ans2){
            checkBoxes.setVisibility(View.VISIBLE);
        } else {
            checkBoxes.setVisibility(View.GONE);
        }
    }

    public void onClick_24(View view){
        View checkBoxes = findViewById(R.id.quest24_checkBoxes);
        if(getCheckedRadioButtonId(R.id.quest24_group) == R.id.quest24_ans2){
            checkBoxes.setVisibility(View.VISIBLE);
        } else {
            checkBoxes.setVisibility(View.GONE);
        }
    }

    public void onClick_31(View view){
        View checkBoxes = findViewById(R.id.quest31_checkBoxes);
        if(getCheckedRadioButtonId(R.id.quest31_group) == R.id.quest31_ans2){
            checkBoxes.setVisibility(View.VISIBLE);
        } else {
            checkBoxes.setVisibility(View.GONE);
        }
    }

    private int getCheckedRadioButtonId(int id){
        RadioGroup group = (RadioGroup) findViewById(id);
        return group.getCheckedRadioButtonId();
    }
}
