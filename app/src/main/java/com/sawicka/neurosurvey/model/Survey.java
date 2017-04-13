package com.sawicka.neurosurvey.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sawicka.neurosurvey.model.questions.Question;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 15.03.17.
 */
@Getter
@Setter
public class Survey implements Parcelable {
    private Map<String, Question> questions;
    private String comments;

    public Survey(){
        this.questions = new LinkedHashMap<>();
    }

    protected Survey(Parcel in) {
        if(this.questions == null)
            this.questions = new LinkedHashMap<>();
        this.comments = in.readString();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public void addNew(String key, Question question){
        try {
            this.questions.put(key, question);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Question getQuestion(String key){
        return this.questions.get(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comments);
    }

    /*private int question1 - 8
    private int question10 - 11
    private int question13a - 22
    private int question27 - 30

    private Set<Integer> question9;
    private Set<Integer> question23;
    private Set<Integer> question24;
    private Set<Integer> question31;

    private Set<String> question12; // drugs
    private Set<String> question25; // other complications

    private Map<Integer, Integer> question26;
    */
}