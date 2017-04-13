package com.sawicka.neurosurvey.model.questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mloda on 07.04.17.
 */

public class CheckQuestion implements Question {
    private List<Integer> answers = new ArrayList<>();

    @Override
    public void setOne(Object o) {
        try {
            Integer answer = (Integer) o;
            if (answers == null) {
                this.answers = new ArrayList<>();
            } else {
                if (this.answers.contains(answer)) {
                    this.answers.remove(answer);
                } else {
                    this.answers.add(answer);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object getAll() {
        return answers;
    }

    @Override
    public Object getDataToFile() {
        return answers;
    }
}
