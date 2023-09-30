package com.study.dayonetest.model;

import com.study.dayonetest.MyCalculator;

public class StudentFailFixture {

    public static StudentFail created(StudentScore studentScore) {
        return StudentFail.builder()
                .studentName(studentScore.getStudentName())
                .exam(studentScore.getExam())
                .avgScore(new MyCalculator()
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();
    }

    public static StudentFail created(String studentName, String exam) {
        return StudentFail.builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(50.0)
                .build();
    }
}
