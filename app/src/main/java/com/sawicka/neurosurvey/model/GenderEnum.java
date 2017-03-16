package com.sawicka.neurosurvey.model;

/**
 * Created by mloda on 14.03.17.
 */

public enum GenderEnum {
    MALE("Male"), FEMALE("Female");
    String text;

    GenderEnum(String text){
        this.text = text;
    }
}
