package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 24.03.17.
 */
@Getter
public enum OtherAnswerEnum {
    Q2b(R.id.other_2b), Q9_9(R.id.other_9), Q11(R.id.other_11),
    Q23_7(R.id.other_23), Q24_8(R.id.other_24), Q31_11(R.id.other_31);

    Integer id;

    OtherAnswerEnum(Integer id){
        this.id = id;
    }
}
