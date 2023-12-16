package com.trianglechoke.codesparring.rankgame.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.service.RankMatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankmatch")
public class RankMatchController {
    @Autowired RankMatchServiceImpl service;

    /* player match */
    @GetMapping("/match/{memberNo}/{tier}")
    public RankGameDTO match(@PathVariable Long memberNo, @PathVariable String tier) {
        try {
            Long rankNo=service.findByRankTier(tier);
            if(rankNo==0L) {
                service.saveRankGame(memberNo);
                throw new MyException(ErrorCode.RANK_MATCH_LOADING);
            } else {
                service.matchingMember(rankNo, memberNo);
                RankGameDTO rank=service.findByRankNo(rankNo);
                return rank;
            }
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* player check */
    @GetMapping("/check/{rankNo}/{memberNo}")
    public RankGameDTO check(@PathVariable Long rankNo, @PathVariable Long memberNo) {
        // 1. 조회하기 SELECT member1==memberNo
        // cnt 확인 (memberNo==m1) 은 cnt==1 확인, (memberNo==m2) 는 cnt==2 확인
        try {
            RankGameDTO rankGameDTO=service.findByRankNo(rankNo);
            Integer cnt=rankGameDTO.getReadyCnt();
            Long m1=rankGameDTO.getMember1No();
            Long m2=rankGameDTO.getMember2No();
            if(memberNo==m1) {
                if(cnt==0) throw new MyException(ErrorCode.RANK_MATCH_LOADING);
                else if(cnt==1) return rankGameDTO;
                else throw new MyException(ErrorCode.RANK_MATCH_FAIL);
            } else if(memberNo==m2) {
                if(cnt==0) throw new MyException(ErrorCode.RANK_MATCH_LOADING);
                else if(cnt==1) throw new MyException(ErrorCode.RANK_MATCH_LOADING);
                else if(cnt==2) return rankGameDTO;
                else throw new MyException(ErrorCode.RANK_MATCH_FAIL);
            } else {
                throw new MyException(ErrorCode.RANK_MATCH_FAIL);
            }
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* player ready */
    @PutMapping("/ready/{rankNo}")
    public ResponseEntity<?> ready(@PathVariable Long rankNo) {
        try {
            service.memberReady(rankNo);
            return new ResponseEntity<>(rankNo, HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* random quiz */
    @PutMapping("/quiz/{rankNo}")
    public Long matchingQuiz(@PathVariable Long rankNo) {
        try {
            Long quizNo = service.modifyGameQuiz(rankNo);
            return quizNo;
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_MODIFIED);
        }
    }

    /* player out */
    @DeleteMapping("/out/{rankNo}")
    public ResponseEntity<?> out(@PathVariable Long rankNo) {
        try {
            service.removeMatching(rankNo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }
}
