package com.example.codingtest2.controller;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.dto.PQResultDto;
import com.example.codingtest2.dto.SQResultDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.service.MCQService;
import com.example.codingtest2.service.PQService;
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
import java.util.ArrayList;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final MCQService mcqService;
    private final SQService sqService;
    private final PQService pqService;
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
        HttpSession session = request.getSession();
        switch (resultSeq) {
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
            case -3 -> {
                log.info("[Login Fail] Already submit : {}/{}", dto.getUserId(), dto.getUserPassword());
                model.addAttribute("error", "이미 응시한 시헙입니다.");
                return "Login";
            }
            case -4 -> {
                log.info("[Login Success - ADMIN] : {}/{}", dto.getUserId(), dto.getUserPassword());
                model.addAttribute("admin", "admin");
                session.setAttribute("user", "admin");
                return "Login";
            }
            default -> {
                log.info("[Login Success] : {}", dto.getUserId());
                User user = userService.findBySeq(resultSeq).get();
                session.setAttribute("user", user);
                return "Notice";
            }
        }
    }

    @GetMapping(value = "/notice")
    public String onNotice(@SessionAttribute("user") User user) {
        return "redirect:main";
    }

    @GetMapping(value = "/main")
    public String main(Model model, @SessionAttribute("user") User user) {
        userService.setLoginDt(user.getUserSeq());
        model.addAttribute("userInfo", user);
        model.addAttribute("timer", user.getUserTestEnd().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        model.addAttribute("question", mcqService.findByLevel(user.getUserLevel()));
        model.addAttribute("sQuestion", sqService.findByLevel(user.getUserLevel()));
        model.addAttribute("pQuestion", pqService.findByLevel(user));
        return "Main";
    }

    @PostMapping(value = "/savePQ_{seq}/{id}")
    public String savePQ(@PathVariable("seq") String seq, @PathVariable("id") String id, @ModelAttribute PQResultDto dto) {
        User uu = userService.findByUserId(id);
        String result = pqService.saveResult(dto, uu);

        if("error".equals(result))
            log.info("[Save programming result ERROR] : {}", uu.getUserId());

        return "Main";
    }

    @PostMapping(value = "/submitQuestion")
    public String submitQuestion(@SessionAttribute("user") User user
            , @ModelAttribute MCQResultDto dto
            , @ModelAttribute SQResultDto dto2
            , @ModelAttribute PQResultDto dto3) {
        dto.setUserSeq(user.getUserSeq());
        dto.setMcqResultScore(mcqService.setResultScore(dto, user));
        mcqService.insertResult(dto);

        dto2.setUserSeq(user.getUserSeq());
        sqService.insertResult(dto2);

        PQResultDto pqrDto = new PQResultDto();
        String[] pqIndex = dto3.getPqResult().split(",,");
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < pqIndex.length; i++) {
            int index = pqIndex[i].indexOf(":");
            pqrDto.setPqSeq(Integer.valueOf(pqIndex[i].substring(0, index)));
            pqrDto.setPqResult(pqIndex[i].substring(index + 1, pqIndex[i].length()));
            result.add(pqService.saveResult(pqrDto, user));
        }

        for(int i=0; i < result.size(); i++){
            if("error".equals(result.get(i)))
                log.info("[Save programming result ERROR] : {}", user.getUserId());
        }

        log.info("[Submit] : {}", user.getUserId());
        return "Main";
    }

    @ResponseBody
    @PostMapping(value = "/checkSubmitDt")
    public boolean checkSubmitDt(@SessionAttribute("user") User user){
        return userService.checkSubmitDt(user);
    }

    @GetMapping(value = "/finish")
    public String finish(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "Finish";
    }

    @GetMapping(value = "/error")
    public String error() {
        return "Error";
    }

    @GetMapping(value = "/sessionConsole")
    public String sessionConsole(){
        return "Main";
    }
}