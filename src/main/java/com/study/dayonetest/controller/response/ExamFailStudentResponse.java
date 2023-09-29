package com.study.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExamFailStudentResponse {
    private final String studentName;
    private final Double avgScore;
}
