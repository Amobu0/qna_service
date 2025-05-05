package com.amobu.qna_service.boundedContext.answer;


import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.question.QuestionService;
import com.amobu.qna_service.boundedContext.user.SiteUser;
import com.amobu.qna_service.boundedContext.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               @PathVariable("id") Long id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal
                               )
    {

        Question question = questionService.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute(question);
            return "question_detail";
        }
        SiteUser author = userService.getUser(principal.getName());
        String content = answerForm.getContent();
        answerService.create(question, content, author);

        return "redirect:/question/detail/%s".formatted(id);
    }
}
