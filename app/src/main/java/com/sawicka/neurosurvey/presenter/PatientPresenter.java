package com.sawicka.neurosurvey.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import com.sawicka.neurosurvey.enums.GenderEnum;
import com.sawicka.neurosurvey.model.Patient;

import org.joda.time.LocalDate;

import lombok.Getter;

/**
 * Created by mloda on 16.03.17.
 */
@Getter
public class PatientPresenter implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.patient, flags);
    }

    protected PatientPresenter(Parcel in) {
        this.patient = in.readParcelable(Patient.class.getClassLoader());
    }

    public static final Parcelable.Creator<PatientPresenter> CREATOR = new Parcelable.Creator<PatientPresenter>() {
        @Override
        public PatientPresenter createFromParcel(Parcel source) {
            return new PatientPresenter(source);
        }

        @Override
        public PatientPresenter[] newArray(int size) {
            return new PatientPresenter[size];
        }
    };
}
