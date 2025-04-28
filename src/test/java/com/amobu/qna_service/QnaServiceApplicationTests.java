package com.amobu.qna_service;

import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QnaServiceApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("데이터 저장하기")
	void t001() {
		Question q1 = new Question();
		q1.setSubject("제목입니다.1");
		q1.setContent("내용입니다.1");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);	// 첫번쨰 질문 저장
		// INSERT 쿼리 실행

		Question q2 = new Question();
		q2.setSubject("제목입니다.2");
		q2.setContent("내용입니다.2");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);	// 두번쨰 질문 저장
	}

	@Test
	@DisplayName("findAll")
	void t002() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size()); // 리스트에 2개의 데이터(사이즈)가 들어있느냐?

		Question q = all.getFirst(); // all 리스트에 1번째 q 객체를 가져오는데
		assertEquals("제목입니다.1", q.getSubject()); // q 객체의 제목이 "제목입니다.1"이냐
	}

	@Test
	@DisplayName("findById")
	void t003() {
		Optional<Question> oq = questionRepository.findById(1L);
        oq.ifPresent(q -> assertEquals("제목입니다.1", q.getSubject()));
	}

	@Test
	@DisplayName("findBySubject")
	void t004() {
		Optional<Question> oq = questionRepository.findBySubject("제목입니다.1");
		oq.ifPresent(q -> assertEquals(1, q.getId()));
	}

	/*
	SELECT
		*
	FROM
		question
	WHERE
		subject =  '제목입니다.1'
	AND
		content = '내용입니다.1';
	*/
	@Test
	@DisplayName("findBySubjectAndContent")
	void t005() {
		Optional<Question> oq = questionRepository.findBySubjectAndContent("제목입니다.1", "내용입니다.1");
		oq.ifPresent(q -> assertEquals(1, q.getId()));
	}

	/*
	SELECT
		*
	FROM
		question
	WHERE
		subject LIKE '%제목%'
	*/
	@Test
	@DisplayName("findBySubjectLike")
	void t006() {
		List<Question> qList= questionRepository.findBySubjectLike("%제목%");
		Question q = qList.getFirst();
		assertEquals("제목입니다.1", q.getSubject());
	}
}
