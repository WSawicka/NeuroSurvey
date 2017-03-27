package com.sawicka.neurosurvey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sawicka.neurosurvey.R;
import com.sawicka.neurosurvey.enums.GenderEnum;
import com.sawicka.neurosurvey.presenter.PatientPresenter;
import com.sawicka.neurosurvey.utils.NoScrollListView;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientPersonalDetailsActivity extends Activity {
    private PatientPresenter presenter = new PatientPresenter();

    @BindView(R.id.name_value) EditText nameValue;
    @BindView(R.id.age_value) EditText ageValue;
    @BindView(R.id.operation_date_picker) DatePicker operationDate;
    @BindView(R.id.operation_name_value) EditText operationName;
    @BindView(R.id.button_next_open_test) Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_details);
        ButterKnife.bind(this);

        operationDate.setMaxDate(new LocalDate().toDate().getTime());

        NoScrollListView lv_q = (NoScrollListView) findViewById(R.id.gender_list_view);
        final List<String> q_arr = Arrays.asList(getResources().getStringArray(R.array.gender_array));
        lv_q.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, q_arr));
        lv_q.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.setSelectedGender(GenderEnum.values()[position].toString());
            }
        });
    }

    @OnClick(R.id.button_next_open_test)
    public void goNext(View view) {
        presenter.setTypedName(nameValue.getText().toString());
        presenter.setTypedOperationName(operationName.getText().toString());

        int year = operationDate.getYear();
        int month = operationDate.getMonth() + 1;
        int day = operationDate.getDayOfMonth();
        presenter.setSelectedOperationDate(year, month, day);

        if(!ageValue.getText().toString().equals("")) {
            try {
                Integer age = Integer.parseInt(ageValue.getText().toString());
                presenter.setTypedAge(age);
            } catch (Exception ex) {
                System.out.println("Got: " + ageValue.getText().toString());
                return;
            }
        }

        Intent intent = new Intent(this, NewTestActivity.class);
        startActivity(intent);
    }
}
