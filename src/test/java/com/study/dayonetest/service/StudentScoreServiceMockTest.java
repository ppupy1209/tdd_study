package com.study.dayonetest.service;

import com.study.dayonetest.repository.StudentFailRepository;
import com.study.dayonetest.repository.StudentPassRepository;
import com.study.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;

public class StudentScoreServiceMockTest {

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "kim";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest() {
        // given : 평균점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);
        String givenStudentName = "kim";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );


        // then
        Mockito.verify(studentScoreRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository,Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository,studentPassRepository,studentFailRepository);

        String givenStudentName = "kim";
        String givenExam = "testExam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 50;
        Integer givenMathScore = 60;

        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        Mockito.verify(studentScoreRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository,Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository,Mockito.times(1)).save(Mockito.any());
    }
}

