package com.sawicka.neurosurvey.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sawicka.neurosurvey.enums.GenderEnum;

import org.joda.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 14.03.17.
 */
@Getter
@Setter
public class Patient implements Parcelable {
    private String name;
    private int age;
    private GenderEnum gender;
    private LocalDate actualDate;
    private LocalDate operationDate;
    private String operationName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeSerializable(this.actualDate);
        dest.writeSerializable(this.operationDate);
        dest.writeString(this.operationName);
    }

    public Patient() {
    }

    protected Patient(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : GenderEnum.values()[tmpGender];
        this.actualDate = (LocalDate) in.readSerializable();
        this.operationDate = (LocalDate) in.readSerializable();
        this.operationName = in.readString();
    }

    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
