package com.amobu.qna_service.boundedContext.answer;

import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    private LocalDateTime modifyDate;

    @ManyToOne
    @ToString.Exclude
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    private Set<SiteUser> voters = new LinkedHashSet<>();

    public void addVoter(SiteUser voter) {
        voters.add(voter);
    }
}
