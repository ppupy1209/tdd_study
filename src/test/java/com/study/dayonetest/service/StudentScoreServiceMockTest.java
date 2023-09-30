package com.study.dayonetest.service;

import com.study.dayonetest.MyCalculator;
import com.study.dayonetest.controller.response.ExamFailStudentResponse;
import com.study.dayonetest.controller.response.ExamPassStudentResponse;
import com.study.dayonetest.model.*;
import com.study.dayonetest.repository.StudentFailRepository;
import com.study.dayonetest.repository.StudentPassRepository;
import com.study.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class StudentScoreServiceMockTest {

    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    public void beforeEach() {
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        studentScoreService = new StudentScoreService(
                studentScoreRepository,studentPassRepository,studentFailRepository
        );
    }

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
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
        StudentScore expectStudentScore = StudentScoreTestDataBuilder.passed().build();
        StudentPass expectStudentPass = StudentPassFixture.created(expectStudentScore);

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // when
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        // then
        Mockito.verify(studentScoreRepository,Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository,Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentPass.getStudentName(),capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectStudentPass.getAvgScore(),capturedStudentPass.getAvgScore());
        Assertions.assertEquals(expectStudentPass.getExam(),capturedStudentPass.getExam());


        Mockito.verify(studentFailRepository,Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        StudentScore expectStudentScore = StudentScoreFixture.failed();

        StudentFail expectStudentFail = StudentFailFixture.created(expectStudentScore);

        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        Mockito.verify(studentScoreRepository,Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(),capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(),capturedStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(),capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(),capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(),capturedStudentScore.getMathScore());

        Mockito.verify(studentFailRepository,Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentFail.getStudentName(),capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectStudentFail.getExam(),capturedStudentFail.getExam());
        Assertions.assertEquals(expectStudentFail.getAvgScore(),capturedStudentFail.getAvgScore());


        Mockito.verify(studentPassRepository,Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {

        String givenTestExam = "testexam";

        StudentPass expectStudent1= StudentPassFixture.created("kim",givenTestExam);
        StudentPass expectStudent2= StudentPassFixture.created("lee",givenTestExam);
        StudentPass notExpectStudent3 = StudentPassFixture.created("park","notexam");

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));

        List<ExamPassStudentResponse> expectResponses = List.of(expectStudent1,expectStudent2)
                .stream()
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .collect(Collectors.toList());
        List<ExamPassStudentResponse> passStudentList = studentScoreService.getPassStudentList(givenTestExam);

        Assertions.assertIterableEquals(expectResponses,passStudentList);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    public void getFailStudentsListTest() {
        String givenTestExam = "testexam";

        StudentFail expectStudent1 = StudentFailFixture.created("kim",givenTestExam);
        StudentFail expectStudent2 = StudentFailFixture.created("lee",givenTestExam);
        StudentFail notExpectStudent1 = StudentFailFixture.created("park","notexam");

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
             expectStudent1,expectStudent2,notExpectStudent1
        ));



        List<ExamFailStudentResponse> expectResponses = List.of(expectStudent1,expectStudent2)
                .stream()
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .collect(Collectors.toList());

        List<ExamFailStudentResponse> failStudentsList = studentScoreService.getFailStudentsList(givenTestExam);

        Assertions.assertIterableEquals(expectResponses,failStudentsList);
    }
}

