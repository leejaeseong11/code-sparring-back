package com.trianglechoke.codesparring.rankgame.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import com.trianglechoke.codesparring.quiz.dto.PageGroup;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankGameServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankgame")
public class RankGameController {
    @Autowired private RankGameServiceImpl service;

    /* 회원의 랭크 게임 전적 목록 조회하기 */
    @GetMapping("/{currentPage}")
    public PageGroup<MyRankDTO> list(@PathVariable Integer currentPage) {
        try {
            Long memberNo= SecurityUtil.getCurrentMemberNo();
            PageGroup<MyRankDTO> list = service.findAllByMemberNo(memberNo, currentPage);
            if (list.getList().size() == 0) throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
            else return list;
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
        }
    }

    /* 랭크 정보 조회하기 */
    @GetMapping("/{rankNo}")
    public RankGameDTO rank(@PathVariable Long rankNo) {
        try {
            RankGameDTO dto = service.findByRankNo(rankNo);
            return dto;
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
        }
    }

    /* 랭크 게임 결과 업데이트하기 */
    @PutMapping("/{rankNo}")
    @Transactional
    public ResponseEntity<?> modify(
            @PathVariable Long rankNo, @RequestBody RankGameDTO rankGameDTO) {
        try {
            rankGameDTO.setRankNo(rankNo);
            service.modifyGameResult(rankGameDTO);
            String msg = "랭크게임 결과 업데이트 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_MODIFIED);
        }
    }
}
