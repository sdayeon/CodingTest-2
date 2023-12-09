package com.example.codingtest2.controller;

import com.example.codingtest2.dto.ScoreDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.service.ScoreService;
import com.example.codingtest2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScoreController {
    private final UserService userService;
    private final ScoreService scoreService;

    @GetMapping(value = "/score")
    public String score(Model model) {
        List<UserDto> scoreList = scoreService.findUserDtoAll("all");
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

        int mcqCount = 4;
        if("3".equals(user.getUserLevel())) mcqCount = 2;
        model.addAttribute("mcQCount", mcqCount);

        return "ScoreDetail";
    }

    @ResponseBody
    @PostMapping(value = "/insertScorePq")
    public String insertScorePq(@ModelAttribute ScoreDto dto) {
        scoreService.updatePQScore(dto);
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

    @GetMapping(value = "/downloadExcel")
    public ResponseEntity downloadExcel(HttpServletResponse response) {
        return ResponseEntity.ok(scoreService.excelDownload(response));
    }

    /*@GetMapping(value = "/dev")
    public String dev(Model model) {
        model.addAttribute("question", scoreService.getPQuestionAll());
        model.addAttribute("sQuestion", scoreService.getSQuestionAll());
        model.addAttribute("result", scoreService.getPQResultAll());
        return "Dev";
    }*/

    @PostMapping(value = "/schUserInfo")
    public String insertUserInfo(@RequestParam("excel") MultipartFile file, HttpServletRequest request) throws IOException {
        if(!file.isEmpty())
            userService.schUserInfo(file);
        return "redirect:"+ request.getHeader("Referer");
    }
}