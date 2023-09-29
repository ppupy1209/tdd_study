package com.study.dayonetest.service;

import com.study.dayonetest.MyCalculator;
import com.study.dayonetest.controller.response.ExamFailStudentResponse;
import com.study.dayonetest.controller.response.ExamPassStudentResponse;
import com.study.dayonetest.model.StudentFail;
import com.study.dayonetest.model.StudentPass;
import com.study.dayonetest.model.StudentScore;
import com.study.dayonetest.repository.StudentFailRepository;
import com.study.dayonetest.repository.StudentPassRepository;
import com.study.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.stream.Collectors;

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

        StudentScore expectStudentScore = StudentScore
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentPass expectStudentPass = StudentPass
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                ).build();
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository,studentPassRepository,studentFailRepository);

        String givenStudentName = "kim";
        String givenExam = "testExam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 50;
        Integer givenMathScore = 60;

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        StudentScore expectStudentScore = StudentScore.builder()
                                .studentName(givenStudentName)
                                .exam(givenExam)
                                .korScore(givenKorScore)
                                .englishScore(givenEnglishScore)
                                .mathScore(givenMathScore)
                                .build();

        StudentFail expectStudentFail = StudentFail.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                )
                .build();

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1=StudentPass.builder().id(1L).studentName("kim").exam("testexam").avgScore(70.0).build();
        StudentPass expectStudent2=StudentPass.builder().id(2L).studentName("lee").exam("testexam").avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("secondexam").avgScore(90.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository,studentPassRepository,studentFailRepository);

        String givenTestExam = "testexam";

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(studentScoreRepository,studentPassRepository,studentFailRepository);

        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("kim").exam("testexam").avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("lee").exam("testexam").avgScore(40.0).build();
        StudentFail notExpectStudent1 = StudentFail.builder().id(3L).studentName("park").exam("iamnot").avgScore(30.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
             expectStudent1,expectStudent2,notExpectStudent1
        ));

        String givenTestExam = "testexam";

        List<ExamFailStudentResponse> expectResponses = List.of(expectStudent1,expectStudent2)
                .stream()
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .collect(Collectors.toList());

        List<ExamFailStudentResponse> failStudentsList = studentScoreService.getFailStudentsList(givenTestExam);

        Assertions.assertIterableEquals(expectResponses,failStudentsList);
    }
}

