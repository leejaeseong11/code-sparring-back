package com.trianglechoke.codesparring.rankgame;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rankgame")
public class RankGameController {
    @Autowired
    private RankGameService service;

    /* 랭크게임 매칭 완료되어 랭크게임에 랭크정보 추가됨 */
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody RankGameDTO rankGameDTO) {
        try {
            service.addRankGame(rankGameDTO);
            String msg="랭크 정보 추가 완료";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            throw new MyException(ErrorCode.RANK_NOT_SAVED);
        }
    }
}
