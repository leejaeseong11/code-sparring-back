package com.trianglechoke.codesparring.rankgame.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankGameServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rankgame")
public class RankGameController {
    @Autowired private RankGameServiceImpl service;

    /* 회원의 랭크 게임 전적 목록 조회하기 */
    @GetMapping("/{memberNo}/{currentPage}")
    public List<MyRankDTO> list(@PathVariable Long memberNo, @PathVariable Integer currentPage) {
        try {
            List<MyRankDTO> list =
                    service.findAllByMemberNo(
                            memberNo, (currentPage - 1) * 10 + 1, currentPage * 10);
            if (list.size() == 0) throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
            else return list;
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
        }
    }

    /* 랭크 게임 정보 추가하기 */
    @PostMapping()
    @Transactional
    public ResponseEntity<?> add(@RequestBody RankGameDTO rankGameDTO) {
        try {
            service.addRankGame(rankGameDTO);
            String msg = "랭크 정보 추가 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_NOT_SAVED);
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
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_MODIFIED);
        }
    }
}
