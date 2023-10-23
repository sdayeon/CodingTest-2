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

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScoreController {
    private final UserService userService;
    private final ScoreService scoreService;

    @GetMapping(value = "/score")
    public String score(Model model) {
        List<UserDto> scoreList = scoreService.findUserDtoAll();
        model.addAttribute("scoreList", scoreList);
        return "Score";
    }

    @GetMapping(value = "/score/{id}")
    public String scoreDetail(@PathVariable(value = "id") String id, Model model) {
        User user = userService.findByUserId(id);
        scoreService.insertUserScore(user);

        model.addAttribute("user", user);
        model.addAttribute("score", scoreService.findUserScore(user));
        model.addAttribute("pQResult", scoreService.getPQResult(user));
        model.addAttribute("sQResult", scoreService.getSQResult(user));
        model.addAttribute("mcQResult", scoreService.getMCQResult(user));
        model.addAttribute("mcQResultCount", scoreService.getMCQResultCount(user));

        return "ScoreDetail";
    }

    @ResponseBody
    @PostMapping(value = "/insertScorePq")
    public String insertScorePq(@ModelAttribute ScoreDto dto) {
        scoreService.updatePQScore(dto);
        log.info("ddyy - pq: " + dto.getScorePq());
        return "";
    }

    @ResponseBody
    @PostMapping(value = "/insertScoreSq")
    public String insertScoreSq(@ModelAttribute ScoreDto dto) {
        scoreService.updateSQScore(dto);
        return "";
    }

    @ResponseBody
    @PostMapping(value = "/insertScoreMcq")
    public String insertScoreMcq(@ModelAttribute ScoreDto dto) {
        scoreService.updateMCQScore(dto);
        return "";
    }

    @ResponseBody
    @PostMapping(value = "/registerScore")
    public String registerScore(@ModelAttribute ScoreDto dto) {
        scoreService.registerScore(dto);
        return "";
    }

    @GetMapping(value = "/dev")
    public String dev(Model model) {
        return "Dev";
    }
}