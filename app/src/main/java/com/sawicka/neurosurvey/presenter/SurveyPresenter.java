package com.sawicka.neurosurvey.presenter;

import android.app.AlertDialog;
import android.content.Context;

import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.enums.questions.RadioQuestEnum;
import com.sawicka.neurosurvey.enums.questions.SeekQuestEnum;
import com.sawicka.neurosurvey.model.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mloda on 20.03.17.
 */

public class SurveyPresenter {
    private Survey survey;
    Context context;

    public SurveyPresenter(Context context){
        this.survey = new Survey();
        this.context = context;
    }

    public void addNewRadioQuestion(Integer keyId, int position){
        String keyName = getRadioEnumName(keyId);
        if (keyName != null) {
            List list = new ArrayList();
            list.add(position + 1);
            this.survey.addNew(keyName, list);
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Did not found that radio item")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void addNewCheckQuestion(Integer keyId, Integer position){
        String keyName = getCheckEnumName(keyId);
        if (keyName == null){
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Did not found that check box")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            List list = this.survey.getQuestionList(keyName);
            if (list == null) {
                list = new ArrayList();
                list.add(position + 1);
            } else {
                if (list.contains(position + 1)) {
                    list.remove(position + 1);
                } else {
                    list.add(position + 1);
                }
            }
            this.survey.addNew(keyName, list);
        }
    }

    public void addNewSeekBarValue(Integer seekBarId, Integer progress){
        String keyName = getSeekEnumName(seekBarId);
        if (keyName == null){
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Did not found this seek bar")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            List list = new ArrayList();
            list.add(progress);
            this.survey.addNew(keyName, list);
        }
    }

    public String getRadioEnumName(Integer questionId){
        for(int i = 0; i < RadioQuestEnum.values().length; i++) {
            int radioEnumValue = RadioQuestEnum.values()[i].getQuestionId();
            if(radioEnumValue == questionId)
                return RadioQuestEnum.values()[i].name();
        }
        return null;
    }

    public String getCheckEnumName(Integer questionId){
        for(int i = 0; i < CheckQuestEnum.values().length; i++) {
            int checkEnumValue = CheckQuestEnum.values()[i].getQuestionId();
            if(checkEnumValue == questionId)
                return CheckQuestEnum.values()[i].name();
        }
        return null;
    }

    public String getSeekEnumName(Integer seekBarId){
        for(int i = 0; i < SeekQuestEnum.values().length; i++) {
            int seekEnumValue = SeekQuestEnum.values()[i].getSeekBarId();
            if(seekEnumValue == seekBarId)
                return SeekQuestEnum.values()[i].name();
        }
        return null;
    }
}

