package com.amobu.qna_service.boundedContext.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @ResponseBody
    @GetMapping("/home/main")
    public String showHome() {
        return "안녕";
    }
}
