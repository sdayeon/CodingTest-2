package com.example.codingtest2.service;

import com.example.codingtest2.dto.ScoreDto;
import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.*;
import com.example.codingtest2.repository.ScoreRepository;
import com.google.gson.Gson;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.codingtest2.entity.QMCQResult.mCQResult;
import static com.example.codingtest2.entity.QMCQuestion.mCQuestion;
import static com.example.codingtest2.entity.QPQResult.pQResult;
import static com.example.codingtest2.entity.QPQuestion.pQuestion;
import static com.example.codingtest2.entity.QSQResult.sQResult;
import static com.example.codingtest2.entity.QSQuestion.sQuestion;
import static com.example.codingtest2.entity.QScore.score;
import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScoreService {
    private final JPAQueryFactory queryFactory;
    private final ScoreRepository scoreRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<UserDto> findUserDtoAll(String status) {
        List<UserDto> dtoList = new ArrayList<>();
        List<UserDto> dtoListScored = new ArrayList<>();
        List<User> rawList = queryFactory
                .selectFrom(user)
                .where(user.userSubmitDt.isNotNull())
                .fetch();

        for (int i = 0; i < rawList.size(); i++) {
            Score scoreAll = queryFactory.selectFrom(score)
                    .where(score.user.userSeq.eq(rawList.get(i).getUserSeq()))
                    .fetchOne();

            String setScoreAll = "";
            boolean check = false;
            if (scoreAll == null || scoreAll.getScoreAll() == null) {
                setScoreAll = "미채점";
            } else {
                setScoreAll = scoreAll.getScoreAll().toString();
                check = true;
            }

            UserDto dto = UserDto.builder()
                    .userSeq(rawList.get(i).getUserSeq())
                    .userId(rawList.get(i).getUserId())
                    .userPassword(rawList.get(i).getUserPassword())
                    .userName(rawList.get(i).getUserName())
                    .userMajor(rawList.get(i).getUserMajor())
                    .userLevel(rawList.get(i).getUserLevel())
                    .userLoginDt(rawList.get(i).getUserLoginDt().format(formatter))
                    .userSubmitDt(rawList.get(i).getUserSubmitDt().format(formatter))
                    .userTestStart(rawList.get(i).getUserTestStart().format(formatter))
                    .userTestEnd(rawList.get(i).getUserTestEnd().format(formatter))
                    .userScoreAll(setScoreAll)
                    .build();

            dtoList.add(dto);
            if (check) dtoListScored.add(dto);
        }

        if ("scored".equals(status)) return dtoListScored;
        return dtoList;
    }

    public Score findUserScore(User user) {
        return queryFactory
                .selectFrom(score)
                .where(score.user.eq(user))
                .fetchOne();
    }

    public void insertUserScore(User user) {
        Score s = Score.builder()
                .user(user)
                .build();

        if (findUserScore(user) == null) {
            scoreRepository.save(s);
        }
    }

    public List<PQResult> getPQResult(User user) {
        return queryFactory
                .selectFrom(pQResult)
                .where(pQResult.user.eq(user))
                .fetch();
    }

    public void updatePQScore(ScoreDto dto) {
        Score score = scoreRepository.findById(dto.getScoreSeq()).get();
        score.setScorePq(dto.getScorePq());
    }

    public Map<String, Object> getSQResult(User user) {
        SQResult sqResultOne = queryFactory
                .selectFrom(sQResult)
                .where(sQResult.user.eq(user))
                .fetchOne();

        Gson gson = new Gson();
        Map<String, Object> rawResult = gson.fromJson(sqResultOne.getSqResult(), Map.class);
        Map<String, Object> result = new HashMap<>();

        for (String qSeq : rawResult.keySet()) {
            result.put(getSQuestion(Integer.valueOf(qSeq)).getSqQuestion(), rawResult.get(qSeq));
        }

        return result;
    }

    private SQuestion getSQuestion(Integer sqSeq) {
        return queryFactory.selectFrom(sQuestion)
                .where(sQuestion.sqSeq.eq(sqSeq))
                .fetchOne();
    }

    public void updateSQScore(ScoreDto dto) {
        Score score = scoreRepository.findById(dto.getScoreSeq()).get();
        score.setScoreSq(dto.getScoreSq());
    }

    public Map<String, Object> getMCQResult(User user) {
        MCQResult mcqResultOne = queryFactory
                .selectFrom(mCQResult)
                .where(mCQResult.user.eq(user))
                .fetchOne();

        Gson gson = new Gson();
        Map<String, Object> rawResult = gson.fromJson(mcqResultOne.getMcqResult(), Map.class);
        Map<String, Object> result = new HashMap<>();

        for (String qSeq : rawResult.keySet()) {
            result.put(getMCQuestion(Integer.valueOf(qSeq)).getMcqQuestion(), rawResult.get(qSeq));
        }

        return result;
    }

    private MCQuestion getMCQuestion(Integer mcqSeq) {
        return queryFactory.selectFrom(mCQuestion)
                .where(mCQuestion.mcqSeq.eq(mcqSeq))
                .fetchOne();
    }

    public int getMCQResultCount(User user) {
        MCQResult result = queryFactory.selectFrom(mCQResult).where(mCQResult.user.eq(user)).fetchOne();
        return result.getMcqResultScore();
    }

    public void updateMCQScore(ScoreDto dto) {
        Score score = scoreRepository.findById(dto.getScoreSeq()).get();
        score.setScoreMcq(dto.getScoreMcq());
    }

    public void registerScore(ScoreDto dto) {
        Score score = scoreRepository.findById(dto.getScoreSeq()).get();
        score.setScoreMcq(dto.getScoreMcq());
        score.setScoreSq(dto.getScoreSq());
        score.setScorePq(dto.getScorePq());
        score.setScoreAll(dto.getScoreAll());

    }

    public Object excelDownload(HttpServletResponse response) {
        List<UserDto> list = findUserDtoAll("scored");

        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("채점결과");
            final String fileName = "2023-2 채점결과";

            final String[] header = {"학번", "이름", "전공학과", "레벨", "채점결과", "시험완료"};
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            for (int i = 0; i < list.size(); i++) {
                UserDto uu = list.get(i);
                row = sheet.createRow(i + 1);
                Cell cell = null;

                cell = row.createCell(0);
                cell.setCellValue(uu.getUserId());
                cell = row.createCell(1);
                cell.setCellValue(uu.getUserName());
                cell = row.createCell(2);
                cell.setCellValue(uu.getUserMajor());
                cell = row.createCell(3);
                cell.setCellValue(uu.getUserLevel());
                cell = row.createCell(4);
                cell.setCellValue(uu.getUserScoreAll());
                cell = row.createCell(5);
                cell.setCellValue(uu.getUserSubmitDt());
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

            OutputStream tempFile = response.getOutputStream();
            workbook.write(tempFile);
            tempFile.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();
            ((SXSSFWorkbook) workbook).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<PQuestion> getPQuestionAll() {
        return queryFactory
                .selectFrom(pQuestion)
                .fetch();
    }

    public List<PQResult> getPQResultAll() {
        return queryFactory
                .selectFrom(pQResult)
                .fetch();
    }

    public List<SQuestion> getSQuestionAll(){
        return queryFactory
                .selectFrom(sQuestion)
                .fetch();
    }
}
