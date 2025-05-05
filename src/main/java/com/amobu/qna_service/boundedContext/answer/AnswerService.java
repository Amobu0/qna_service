package com.amobu.qna_service.boundedContext.answer;

import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.EntityNotFoundException;
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

    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 질문을 찾을 수 없습니다. id=" + id));
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
