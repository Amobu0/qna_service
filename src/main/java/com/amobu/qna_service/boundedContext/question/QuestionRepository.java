package com.amobu.qna_service.boundedContext.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findBySubject(String subject);

    Optional<Question> findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}
