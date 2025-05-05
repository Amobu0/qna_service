package com.amobu.qna_service.boundedContext.answer;

import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create (Question question, String content, SiteUser author) {
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setAuthor(author);
        answer.setCreateDate(LocalDateTime.now());

        question.addAnswer(answer);
        answerRepository.save(answer);
        return answer;
    }
}
