package com.example.codingtest2.controller;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.service.MCQService;
import com.example.codingtest2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final MCQService mcqService;
    private final UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("question", mcqService.findAll());
        return "Main";
    }

    @ResponseBody
    @PostMapping(value = "/summitMCQ")
    public String summitMCQ(@ModelAttribute MCQResultDto dto) {
        mcqService.insertResult(dto);
        return "Main";
    }
}