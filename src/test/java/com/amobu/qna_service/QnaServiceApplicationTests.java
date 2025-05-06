package com.amobu.qna_service;

import com.amobu.qna_service.boundedContext.answer.Answer;
import com.amobu.qna_service.boundedContext.answer.AnswerRepository;
import com.amobu.qna_service.boundedContext.answer.AnswerService;
import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.question.QuestionRepository;
import com.amobu.qna_service.boundedContext.question.QuestionService;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import com.amobu.qna_service.boundedContext.user.UserRepository;
import com.amobu.qna_service.boundedContext.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QnaServiceApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
        // 테스트 케이스 실행 전에 딱 1번 실행
    void beforeEach() {

        // 모든 질문 데이터 삭제
        questionRepository.deleteAll();
        // AUTO_INCREMENT 초기화
        questionRepository.clearAutoIncrement();
        // 모든 답변 데이터 삭제
        answerRepository.deleteAll();
        // AUTO_INCREMENT 초기화
        answerRepository.clearAutoIncrement();

        // 모든 답변 데이터 삭제
        userRepository.deleteAll();
        // AUTO_INCREMENT 초기화
        userRepository.clearAutoIncrement();

        SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
        SiteUser user2 = userService.create("user2", "user2@test.com", "1234");

        Question q1 = new Question();
        q1.setSubject("제목입니다.1");
        q1.setContent("내용입니다.1");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(user1);
        questionRepository.save(q1);    // 첫번쨰 질문 저장
        // INSERT 쿼리 실행

        Question q2 = new Question();
        q2.setSubject("제목입니다.2");
        q2.setContent("내용입니다.2");
        q2.setCreateDate(LocalDateTime.now());
        q2.setAuthor(user2);
        questionRepository.save(q2);    // 두번쨰 질문 저장

        Answer a1 = answerService.create(q2, "답변입니다.2", user2);
        answerRepository.save(a1);

        q1.addVoter(user1);
        q1.addVoter(user2);
        questionRepository.save(q1);

        q2.addVoter(user1);
        q2.addVoter(user2);
        questionRepository.save(q2);

        a1.addVoter(user1);
        a1.addVoter(user2);
        answerRepository.save(a1);
    }

    @Test
    @DisplayName("데이터 저장하기")
    void t001() {
        Question q = new Question();
        q.setSubject("제목입니다.3");
        q.setContent("내용입니다.3");
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
        assertEquals(3, questionRepository.count());
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
        List<Question> qList = questionRepository.findBySubjectLike("%제목%");
        Question q = qList.getFirst();
        assertEquals("제목입니다.1", q.getSubject());
    }

    @Test
    @DisplayName("데이터 수정하기")
    void t007() {
        Optional<Question> oq = questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("제목입니다.1수정");
        questionRepository.save(q);
    }

    @Test
    @DisplayName("데이터 삭제하기")
    void t008() {
        assertEquals(2, questionRepository.count());
        Optional<Question> oq = questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
        assertEquals(1, questionRepository.count());
    }

    @Test
    @DisplayName("답변 데이터 생성 후 저장")
    void t009() {
        Optional<Question> oq = questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("답변입니다.1");
        a.setQuestion(q); // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }

    @Test
    @DisplayName("답변 데이터 조회")
    void t010() {
        Optional<Answer> oa = answerRepository.findById(1L);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    // findById 메서드를 실행하고 나면 DB가 끝어지기 때문에
    // @Transactional 사용하면 메서드가 종료될 때까지 DB 연결이 유지된다.
    @Transactional
    @Test
    @DisplayName("질문을 통해 답변 찾기")
    void t011() {
        Optional<Question> oq = questionRepository.findById(2L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList(); // DB 연결이 끊긴 뒤 answerList 를 가져 옴 => 실패 @Transactional 사용이유

        assertEquals(1, answerList.size());
        assertEquals("답변입니다.2", answerList.getFirst().getContent());
    }

    @Test
    @DisplayName("대량의 테스트 데이터 만들기")
    void test012() {
        SiteUser user2 = userService.getUser("user2");

        LongStream.rangeClosed(3, 300)
                .forEach(
                        no -> questionService.create("테스트 제목입니다.%d".formatted(no), "테스트 내용입니다.%d".formatted(no), user2));
    }
}