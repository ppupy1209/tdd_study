package com.study.dayonetest;

import com.study.dayonetest.model.StudentScore;
import com.study.dayonetest.model.StudentScoreFixture;
import com.study.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;


class DayonetestApplicationTests extends IntegrationTest {

	@Autowired private StudentScoreRepository studentScoreRepository;

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		var studentScore = StudentScoreFixture.passed();
		var savedStudentScore = studentScoreRepository.save(studentScore);

		em.flush();
		em.clear();

		var queryStudentScore = studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

		Assertions.assertEquals(savedStudentScore.getId(),queryStudentScore.getId());
	}

}
