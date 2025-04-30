package com.amobu.qna_service.boundedContext.answer;

import com.amobu.qna_service.boundedContext.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Answer {
    @Id //PRIMARY_KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @ToString.Exclude
    private Question question;
}
