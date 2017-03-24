package com.sawicka.neurosurvey.presenter;

import com.sawicka.neurosurvey.enums.GenderEnum;
import com.sawicka.neurosurvey.model.Patient;

import org.joda.time.LocalDate;

/**
 * Created by mloda on 16.03.17.
 */

public class PatientPresenter {
    private Patient patient;

    public PatientPresenter(){
        this.patient = new Patient();
        this.patient.setActualDate(new LocalDate());
    }

    public void setSelectedGender(String gender){
        this.patient.setGender(GenderEnum.valueOf(gender));
    }

    public void setTypedName(String name){
        this.patient.setName(name);
    }

    public void setTypedAge(Integer age){
        this.patient.setAge(age);
    }

    public void setTypedOperationName(String name){
        this.patient.setOperationName(name);
    }

    public void setSelectedOperationDate(int year,int month,int dayOfMonth){
        LocalDate date = new LocalDate().withYear(year).withMonthOfYear(month).withDayOfMonth(dayOfMonth);
        this.patient.setOperationDate(date);
    }
}
