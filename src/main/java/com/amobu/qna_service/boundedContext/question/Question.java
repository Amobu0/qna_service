package com.amobu.qna_service.boundedContext.question;

import com.amobu.qna_service.boundedContext.answer.Answer;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id //PRIMARY_KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author;

    // Set 자료형은 중복된 값을 무시한다.
    @ManyToMany
    private Set<SiteUser> voters = new LinkedHashSet<>();

    // CascadeType.REMOVE: 질문이 삭제되면 답변도 같이 삭제한다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }

    public void addVoter(SiteUser voter) {
        voters.add(voter);
    }

}
