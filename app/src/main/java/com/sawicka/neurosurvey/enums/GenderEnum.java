package com.sawicka.neurosurvey.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mloda on 14.03.17.
 */

public enum GenderEnum {
    MALE("Male"), FEMALE("Female");
    String text;

    static Map<String, GenderEnum> map = new HashMap<>();
    static {
        for(GenderEnum genderEnum : GenderEnum.values()){
            map.put(genderEnum.text, genderEnum);
        }
    }

    GenderEnum(String text){
        this.text = text;
    }

    public static GenderEnum nameOf(String text){
        return map.get(text);
    }
}
