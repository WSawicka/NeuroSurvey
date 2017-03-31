package com.sawicka.neurosurvey.presenter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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

public class SurveyPresenter implements Parcelable {
    private Survey survey = new Survey();

    public void addNewRadioQuestion(Integer keyId, int position, Context context){
        String keyName = getRadioEnumName(keyId);
        if (keyName != null) {
            List list = new ArrayList();
            list.add(position + 1);
            this.survey.addNew(keyName, list);
        } else {
            new MyAlert().showAlertDialog("Did not found that radio item", context);
        }
    }

    public void addNewCheckQuestion(Integer keyId, Integer position, Context context){
        String keyName = getCheckEnumName(keyId);
        if (keyName == null){
            new MyAlert().showAlertDialog("Did not found that check box", context);
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

    public void addNewSeekBarValue(Integer seekBarId, Integer progress, Context context){
        String keyName = getSeekEnumName(seekBarId);
        if (keyName == null){
            new MyAlert().showAlertDialog("Did not found this seek bar", context);
        } else {
            List list = new ArrayList();
            list.add(progress);
            this.survey.addNew(keyName, list);
        }
    }

    public void setOtherAnswersText(List<EditText> others, Context context){
        for(EditText other : others){
            if(other.getText() != null) {
                String keyName = getOtherAnswerName(other.getId());
                String text = other.getText().toString();
                if(text.equals(""))
                    return;
                if(keyName == null) {
                    new MyAlert().showAlertDialog("Could not set other answer with id: " + other.getId(), context);
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

    public void setComments(String comments){
        this.survey.setComments(comments);
    }

    public void addNewToListView(Integer listViewId, String text, Context context){
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
            new MyAlert().showAlertDialog("List not found", context);
        }
    }

    public List<String> getList(Integer id, Context context){
        String keyName = getOtherQuestName(id);
        if(keyName != null) {
            List<String> list = this.survey.getQuestionList(keyName);
            return (list == null) ?
                    new ArrayList<String>() : list;
        } else {
            new MyAlert().showAlertDialog("List not found", context);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.survey, flags);
    }

    public SurveyPresenter() {
    }

    protected SurveyPresenter(Parcel in) {
        this.survey = in.readParcelable(Survey.class.getClassLoader());
    }

    public static final Parcelable.Creator<SurveyPresenter> CREATOR = new Parcelable.Creator<SurveyPresenter>() {
        @Override
        public SurveyPresenter createFromParcel(Parcel source) {
            return new SurveyPresenter(source);
        }

        @Override
        public SurveyPresenter[] newArray(int size) {
            return new SurveyPresenter[size];
        }
    };
}

