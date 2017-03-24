package com.sawicka.neurosurvey.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.sawicka.neurosurvey.enums.OtherAnswerEnum;
import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.enums.questions.OtherQuestEnum;
import com.sawicka.neurosurvey.enums.questions.RadioQuestEnum;
import com.sawicka.neurosurvey.enums.questions.SeekQuestEnum;
import com.sawicka.neurosurvey.model.Survey;
import com.sawicka.neurosurvey.utils.MyAlert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mloda on 20.03.17.
 */

public class SurveyPresenter {
    private Survey survey;
    private Context context;
    private MyAlert alert;

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
            alert.showAlertDialog("Did not found that radio item", context);
        }
    }

    public void addNewCheckQuestion(Integer keyId, Integer position){
        String keyName = getCheckEnumName(keyId);
        if (keyName == null){
            alert.showAlertDialog("Did not found that check box", context);
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
            alert.showAlertDialog("Did not found this seek bar", context);
        } else {
            List list = new ArrayList();
            list.add(progress);
            this.survey.addNew(keyName, list);
        }
    }

    public void setOtherAnswersText(List<EditText> others){
        for(EditText other : others){
            if(other.getText() != null) {
                String keyName = getOtherAnswerName(other.getId());
                String text = other.getText().toString();
                if(text.equals("") || keyName == null) {
                    alert.showAlertDialog("Could not set other answer with id: " + other.getId(), context);
                    return;
                }

                List list = this.survey.getQuestionList(keyName);
                if(list == null){
                    list = new ArrayList();
                }
                list.add(text);
                this.survey.addNew(keyName, list);
            }
        }
    }

    public void addNewToListView(Integer listViewId, String text){
        String keyName = getOtherQuestName(listViewId);
        if(text.equals("")){
            return;
        }
        if(keyName != null) {
            List list = this.survey.getQuestionList(keyName);
            if (list == null) {
                list = new ArrayList();
            }
            list.add(text);
            this.survey.addNew(keyName, list);
        } else {
            alert.showAlertDialog("List not found", context);
        }
    }

    public List<String> getList(Integer id){
        String keyName = getOtherQuestName(id);
        if(keyName != null) {
            List<String> list = this.survey.getQuestionList(keyName);
            return (list == null) ?
                    new ArrayList<String>() : list;
        } else {
            alert.showAlertDialog("List not found", context);
            return new ArrayList<>();
        }
    }

    @Nullable
    private String getRadioEnumName(Integer questionId){
        for(int i = 0; i < RadioQuestEnum.values().length; i++) {
            int radioEnumValue = RadioQuestEnum.values()[i].getQuestionId();
            if(radioEnumValue == questionId)
                return RadioQuestEnum.values()[i].name();
        }
        return null;
    }

    @Nullable
    private String getCheckEnumName(Integer questionId){
        for(int i = 0; i < CheckQuestEnum.values().length; i++) {
            int checkEnumValue = CheckQuestEnum.values()[i].getQuestionId();
            if(checkEnumValue == questionId)
                return CheckQuestEnum.values()[i].name();
        }
        return null;
    }

    @Nullable
    private String getSeekEnumName(Integer seekBarId){
        for(int i = 0; i < SeekQuestEnum.values().length; i++) {
            int seekEnumValue = SeekQuestEnum.values()[i].getSeekBarId();
            if(seekEnumValue == seekBarId)
                return SeekQuestEnum.values()[i].name();
        }
        return null;
    }

    @Nullable
    private String getOtherQuestName(Integer id){
        for(int i = 0; i < OtherQuestEnum.values().length; i++){
            int enumId = OtherQuestEnum.values()[i].getLv_id();
            if(enumId == id) {
                return OtherQuestEnum.values()[i].name();
            }
        }
        return null;
    }

    @Nullable
    private String getOtherAnswerName(Integer id){
        for(int i = 0; i < OtherAnswerEnum.values().length; i++){
            int enumId = OtherAnswerEnum.values()[i].getId();
            if(enumId == id) {
                return OtherAnswerEnum.values()[i].name();
            }
        }
        return null;
    }
}

