package com.study.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ExamFailStudentResponse {
    private final String studentName;
    private final Double avgScore;
}
