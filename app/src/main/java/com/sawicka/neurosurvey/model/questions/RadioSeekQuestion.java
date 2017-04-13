package com.sawicka.neurosurvey.model.questions;

/**
 * Created by mloda on 07.04.17.
 */

public class RadioSeekQuestion implements Question {
    private Integer answer;

    @Override
    public void setOne(Object o) {
        try {
            this.answer = (Integer) o;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object getAll() {
        return answer;
    }

    @Override
    public Object getDataToFile() {
        return answer;
    }
}
