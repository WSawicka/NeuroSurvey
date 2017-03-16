package com.sawicka.neurosurvey.model;

import org.joda.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 14.03.17.
 */
@Getter
@Setter
public class Patient {
    private String name;
    private int age;
    private GenderEnum gender;
    private LocalDate actualDate;
    private LocalDate operationDate;
    private String operationName;
}
