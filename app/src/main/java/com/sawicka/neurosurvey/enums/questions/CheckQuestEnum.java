package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 18.03.17.
 */
@Getter
public enum CheckQuestEnum {
    Q9(R.id.lv_q9, R.array.q9),
    Q23(R.id.lv_q23, R.array.q23),
    Q24(R.id.lv_q24, R.array.q24),
    Q31(R.id.lv_q31, R.array.q31);

    public Integer questionId;
    Integer optionsArrayId;

    CheckQuestEnum(Integer questionId, Integer optionsArrayId){
        this.questionId = questionId;
        this.optionsArrayId = optionsArrayId;
    }
}
