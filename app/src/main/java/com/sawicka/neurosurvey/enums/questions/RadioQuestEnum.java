package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 18.03.17.
 */
@Getter
public enum RadioQuestEnum {
    Q1(R.id.lv_q1, R.array.q1),             Q2a(R.id.lv_q2a, R.array.q2a),
    Q2b(R.id.lv_q2b, R.array.q2b),          Q3(R.id.lv_q3, R.array.q3),
    Q4(R.id.lv_q4, R.array.q4),             Q5(R.id.lv_q5, R.array.q5),
    Q6(R.id.lv_q6, R.array.q6),
    Q8(R.id.lv_q8, R.array.q8),       
    Q10(R.id.lv_q10, R.array.q10),          Q11(R.id.lv_q11, R.array.q11),
    Q13a(R.id.lv_q13a, R.array.q13a),
    Q13b(R.id.lv_q13b, R.array.q13b),       Q14(R.id.lv_q14, R.array.q14),
    Q19(R.id.lv_q19, R.array.q19),          Q20(R.id.lv_q20, R.array.q20),
    Q21(R.id.lv_q21, R.array.q21),          Q22(R.id.lv_q22, R.array.q22),
    Q27(R.id.lv_q27, R.array.q27),
    Q29(R.id.lv_q29, R.array.q29),          Q30(R.id.lv_q30, R.array.q30);

    public Integer questionId;
    Integer optionsArrayId;

    RadioQuestEnum(Integer questionId, Integer optionsArrayId){
        this.questionId = questionId;
        this.optionsArrayId = optionsArrayId;
    }
}
