package com.amobu.qna_service.boundedContext.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerForm {

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
