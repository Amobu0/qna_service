package com.amobu.qna_service.boundedContext.question;

import com.amobu.qna_service.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Entity
@AllArgsConstructor
public class Question {

    @Id //PRIMARY_KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // CascadeType.REMOVE: 질문이 삭제되면 답변도 같이 삭제한다.
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

}
