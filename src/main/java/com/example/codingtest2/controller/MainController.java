package com.example.codingtest2.controller;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.dto.SQResultDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.service.MCQService;
import com.example.codingtest2.service.SQService;
import com.example.codingtest2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final MCQService mcqService;
    private final SQService sqService;
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
                HttpSession session = request.getSession();
                session.setAttribute("user", userService.findBySeq(resultSeq).get());
                return "redirect:main";
            }
        }
    }

    @GetMapping(value = "/main")
    public String main(Model model, @SessionAttribute("user") User user) {
        userService.setLoginDt(user.getUserSeq());
        model.addAttribute("userInfo", user);
        model.addAttribute("timer", user.getUserTestEnd().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        model.addAttribute("question", mcqService.findByLevel(user.getUserLevel()));
        model.addAttribute("sQuestion", sqService.findByLevel(user.getUserLevel()));
        return "Main";
    }

    @PostMapping(value = "/submitQuestion")
    public String submitQuestion(@SessionAttribute("user") User user, @ModelAttribute MCQResultDto dto, @ModelAttribute SQResultDto dto2) {
        dto.setUserSeq(user.getUserSeq());
        dto.setMcqResultScore(mcqService.setResultScore(dto, user));
        mcqService.insertResult(dto);

        dto2.setUserSeq(user.getUserSeq());
        sqService.insertResult(dto2);
        return "Main";
    }

    @GetMapping(value = "/finish")
    public String finish(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "Finish";
    }
}