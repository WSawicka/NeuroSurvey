package com.sawicka.neurosurvey.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mloda on 15.03.17.
 */

public class Survey {
    Map<String, List> questions;

    public Survey(){
        this.questions = new HashMap<>();
    }

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

    /*private int question1 - 8
    private int question10 - 11
    private int question13a - 22
    private int question27 - 30

    private Set<Integer> question9;
    private Set<Integer> question23;
    private Set<Integer> question24;
    private Set<Integer> question31;

    private Set<Medication> question12; // drugs

    private Set<String> question25; // other complications

    private Map<Integer, Integer> question26;
    */
}