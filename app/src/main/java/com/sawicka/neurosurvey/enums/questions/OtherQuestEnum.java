package com.sawicka.neurosurvey.enums.questions;

import com.sawicka.neurosurvey.R;

import lombok.Getter;

/**
 * Created by mloda on 20.03.17.
 */
@Getter
public enum OtherQuestEnum {
    Q12(R.id.added_listView_12, R.id.auto_complete_12), Q25(R.id.added_listView_25, R.id.auto_complete_25);

    Integer id;
    Integer autocompleteId;

    OtherQuestEnum(Integer id, Integer autocompleteId){
        this.id = id;
        this.autocompleteId = autocompleteId;
    }
}
