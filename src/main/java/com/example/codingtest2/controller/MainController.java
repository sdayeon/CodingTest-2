package com.example.codingtest2.controller;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.service.MCQService;
import com.example.codingtest2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final MCQService mcqService;
    private final UserService userService;

    @GetMapping(value = "/")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "Login";
    }
    @PostMapping(value = "/login")
    public String loginCheck(@ModelAttribute UserDto dto, Model model, HttpServletRequest request) {
        Integer resultSeq = userService.loginCheck(dto);

        switch (resultSeq){
            case 0 -> {
                log.info("[Login Fail] Not found : {}/{}", dto.getUserId(), dto.getUserPassword());
                model.addAttribute("error", "사용자 계정이 없습니다.");
                return "Login";
            }
            case -1 -> {
                log.info("[Login Fail] Incorrect password : {}/{}", dto.getUserId(), dto.getUserPassword());
                model.addAttribute("error", "비밀번호를 잘못 입력하였습니다.");
                return "Login";
            }
            case -2 -> {
                log.info("[Login Fail] Not test time : {}/{}", dto.getUserId(), dto.getUserPassword());
                model.addAttribute("error", "시험 응시 기간이 아닙니다.");
                return "Login";
            }
            default -> {
                log.info("[Login Success] : {}", dto.getUserId());
                dto.setUserSeq(resultSeq);

                HttpSession session = request.getSession();
                session.setAttribute("user", dto);
                return "redirect:main";
            }
        }
    }

    @GetMapping(value = "/main")
    public String main(Model model, @SessionAttribute("user") UserDto user) {
        userService.setLoginDt(user.getUserSeq());
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("question", mcqService.findAll());
        return "Main";
    }

    @PostMapping(value = "/submitMCQ")
    public String submitMCQ(@ModelAttribute MCQResultDto dto, @SessionAttribute("user") UserDto user) {
        dto.setUserSeq(user.getUserSeq());
        mcqService.insertResult(dto);
        return "Main";
    }

    @GetMapping(value = "/finish")
    public String finish(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "Finish";
    }
}