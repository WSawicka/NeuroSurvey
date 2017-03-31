package com.sawicka.neurosurvey.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mloda on 15.03.17.
 */

public class Survey implements Parcelable {
    private Map<String, List> questions;
    private String comments;

    public Survey(){
        this.questions = new HashMap<>();
    }

    protected Survey(Parcel in) {
        if(this.questions == null)
            this.questions = new HashMap<>();
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

    public void addNew(String key, List list){
        try {
            this.questions.put(key, list);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List getQuestionList(String key){
        return this.questions.get(key);
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    public String getComments(){
        return this.comments;
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