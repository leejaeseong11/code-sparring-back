package com.trianglechoke.codesparring.rankgame;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rankgame")
public class RankGameController {
    @Autowired private RankGameService service;

    /* 랭크게임 매칭 완료되어 랭크게임에 랭크정보 추가됨 */
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody RankGameDTO rankGameDTO) {
        try {
            service.addRankGame(rankGameDTO);
            String msg = "랭크 정보 추가 성공";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_NOT_SAVED);
        }
    }

    /* 랭크게임 종료되어 랭크게임에 게임결과 업데이트됨 */
    @PutMapping("/{rankNo}")
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

    @GetMapping("/{memberNo}")
    public List<RankGameDTO> list(@PathVariable Long memberNo) {
        try {
            return service.findAllByMemberNo(memberNo);
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);
        }
    }
}
