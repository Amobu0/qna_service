package com.amobu.qna_service.boundedContext.answer;


import com.amobu.qna_service.boundedContext.question.Question;
import com.amobu.qna_service.boundedContext.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Long id, @Valid AnswerForm answerForm, BindingResult bindingResult) {

        Question question = questionService.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute(question);
            return "question_detail";
        }
        String content = answerForm.getContent();
        answerService.save(question, content);

        return "redirect:/question/detail/%s".formatted(id);
    }
}
