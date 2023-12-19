package com.trianglechoke.codesparring.rankgame.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;

import com.trianglechoke.codesparring.rankgame.dto.RankRoomDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankRoom;
import com.trianglechoke.codesparring.rankgame.service.RankRoomServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankroom")
public class RankRoomController {
    @Autowired
    RankRoomServiceImpl service;

    /* player match */
    @GetMapping("/match/{tier}")
    public RankRoomDTO match(@PathVariable String tier) {
        try {
            Long memberNo = SecurityUtil.getCurrentMemberNo();
            RankRoom rankRoom = service.findByRankTier(tier);

            if (rankRoom == null) {
                Long rankNo = service.saveRankGame(memberNo);
                return service.findByRoomNo(rankNo);
            } else {
                if (rankRoom.getMember1().getMemberNo() == memberNo) {
                    if (rankRoom.getMember2No() == null) {
                        service.removeMatching(rankRoom.getRankRoomNo());
                        Long rankNo = service.saveRankGame(memberNo);
                        return service.findByRoomNo(rankNo);
                    } else throw new MyException(ErrorCode.RANK_MATCH_FAIL);
                }
                service.matchingMember(rankRoom.getRankRoomNo(), memberNo);
                RankRoomDTO rank = service.findByRoomNo(rankRoom.getRankRoomNo());
                return rank;
            }
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* player check */
    @GetMapping("/check/{roomNo}")
    public RankRoomDTO check(@PathVariable Long roomNo) {
        try {
            Long memberNo=SecurityUtil.getCurrentMemberNo();
            RankRoomDTO rankRoomDTO = service.findByRoomNo(roomNo);
            Integer cnt = rankRoomDTO.getReadyCnt();
            return rankRoomDTO;
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* player ready */
    @PutMapping("/ready/{roomNo}")
    @Transactional
    public ResponseEntity<?> ready(@PathVariable Long roomNo) {
        try {
            service.memberReady(roomNo);
            return new ResponseEntity<>(roomNo, HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }

    /* random quiz */
    @PutMapping("/quiz/{roomNo}")
    @Transactional
    public RankRoomDTO matchingQuiz(@PathVariable Long roomNo) {
        try {
            service.modifyGameQuiz(roomNo);
            RankRoomDTO dto= service.findByRoomNo(roomNo);
            return dto;
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_MODIFIED);
        }
    }

    /* player out */
    @DeleteMapping("/out/{roomNo}")
    public ResponseEntity<?> out(@PathVariable Long roomNo) {
        try {
            service.removeMatching(roomNo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(ErrorCode.RANK_MATCH_FAIL);
        }
    }
}
