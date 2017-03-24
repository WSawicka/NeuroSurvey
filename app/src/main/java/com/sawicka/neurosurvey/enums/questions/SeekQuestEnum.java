package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 20.03.17.
 */
@Getter
public enum SeekQuestEnum {
    Q7(R.id.q7_layout_label, R.layout.label_view_0_7, R.id.seek_bar_7),
    Q15(R.id.q15_layout_label, R.layout.label_view_0_7, R.id.seek_bar_15),
    Q16(R.id.q16_layout_label, R.layout.label_view_0_7, R.id.seek_bar_16),
    Q17(R.id.q17_layout_label, R.layout.label_view_0_7, R.id.seek_bar_17),
    Q18(R.id.q18_layout_label, R.layout.label_view_0_7, R.id.seek_bar_18),
    Q28(R.id.q28_layout_label, R.layout.label_view_0_7, R.id.seek_bar_28),

    Q26_1(R.id.q26_1_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_1),
    Q26_2(R.id.q26_2_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_2),
    Q26_3(R.id.q26_3_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_3),
    Q26_4(R.id.q26_4_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_4),
    Q26_5(R.id.q26_5_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_5),
    Q26_6(R.id.q26_6_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_6),
    Q26_7(R.id.q26_7_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_7),
    Q26_8(R.id.q26_8_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_8),
    Q26_9(R.id.q26_9_layout_label, R.layout.label_view_4val, R.id.seek_bar_26_9);

    Integer questionId;
    Integer optionsArrayId;
    Integer seekBarId;

    SeekQuestEnum(Integer questionId, Integer optionsArrayId, Integer seekBarId){
        this.questionId = questionId;
        this.optionsArrayId = optionsArrayId;
        this.seekBarId = seekBarId;
    }
}
