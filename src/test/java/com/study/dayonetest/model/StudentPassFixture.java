package com.study.dayonetest.model;

import com.study.dayonetest.MyCalculator;

public class StudentPassFixture {

    public static StudentPass created(StudentScore studentScore) {
        return StudentPass
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(new MyCalculator()
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();
    }

    public static StudentPass created(String studentName, String exam) {
        return StudentPass.builder()
                        .studentName(studentName)
                        .exam(exam)
                        .avgScore(80.0)
                        .build();

    }
}
