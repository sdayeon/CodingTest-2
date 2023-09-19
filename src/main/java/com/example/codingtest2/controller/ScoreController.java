package com.example.codingtest2.controller;

import com.example.codingtest2.dto.ScoreDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.service.ScoreService;
import com.example.codingtest2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScoreController {
    private final UserService userService;
    private final ScoreService scoreService;

    @GetMapping(value = "/score")
    public String score(Model model){
        List<UserDto> scoreList = scoreService.findUserDtoAll();
        model.addAttribute("scoreList", scoreList);
        return "Score";
    }

    @GetMapping(value = "/score/{id}")
    public String scoreDetail(@PathVariable(value = "id") String id, Model model){
        model.addAttribute("id", id);
        User user = userService.findByUserId(id);

        model.addAttribute("pQResult", scoreService.getPQResult(user));
        model.addAttribute("sQResult", scoreService.getSQResult(user));
        model.addAttribute("mcQResult", scoreService.getMCQResult(user));

        return "ScoreDetail";
    }

    @ResponseBody
    @PostMapping(value = "/insertScorePq")
    public String insertScorePq(@ModelAttribute ScoreDto dto){
        log.info("ddyy - pq: "+dto.getScorePq());
        return "";
    }

    @ResponseBody
    @PostMapping(value = "/insertScoreSq")
    public String insertScoreSq(@ModelAttribute ScoreDto dto){
        log.info("ddyy - sq: "+dto.getScoreSq());
        return "";
    }
}