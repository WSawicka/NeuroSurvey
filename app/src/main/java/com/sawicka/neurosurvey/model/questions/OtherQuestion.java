package com.sawicka.neurosurvey.model.questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mloda on 07.04.17.
 */

public class OtherQuestion implements Question {
    private List<String> answers = new ArrayList<>();

    @Override
    public void setOne(Object o) {
        try {
            String answer = (String) o;
            if (this.answers == null) {
                this.answers = new ArrayList<>();
            }
            this.answers.add(answer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object getAll() {
        return this.answers;
    }

    public Object getDataToFile() {
        String result = this.answers.toString();
        return result.substring(1, result.length()-1);
    }
}
