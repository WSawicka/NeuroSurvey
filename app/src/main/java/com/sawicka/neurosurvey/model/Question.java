package com.sawicka.neurosurvey.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 19.03.17.
 */
@Getter
@Setter
public class Question {
    private String questionBody;
    private List<String> options;
    private List answer;
}
