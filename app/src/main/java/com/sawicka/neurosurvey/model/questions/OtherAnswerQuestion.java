package com.sawicka.neurosurvey.model.questions;

/**
 * Created by mloda on 22.04.17.
 */

public class OtherAnswerQuestion implements Question {
    private String otherAnswer = "";

    @Override
    public void setOne(Object o) {
        try {
            this.otherAnswer = (String) o;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object getAll() {
        return this.otherAnswer;
    }

    @Override
    public Object getDataToFile() {
        return this.otherAnswer;
    }
}
