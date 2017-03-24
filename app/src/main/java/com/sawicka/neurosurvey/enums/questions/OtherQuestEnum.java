package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 20.03.17.
 */
@Getter
public enum OtherQuestEnum {
    Q12(R.id.added_listView_12), Q25(R.id.added_listView_25);

    Integer lv_id;

    OtherQuestEnum(Integer id){
        this.lv_id = id;
    }
}
