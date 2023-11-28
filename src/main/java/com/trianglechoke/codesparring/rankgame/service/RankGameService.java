package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import com.trianglechoke.codesparring.rankgame.repository.RankGameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RankGameService {
    @Autowired private RankGameRepository repository;

    /* save : 랭크게임 정보 저장 */
    public void addRankGame(RankGameDTO rankGameDTO) throws MyException {
        if (rankGameDTO.getMember1No() == rankGameDTO.getMember2No()) {
            throw new MyException(ErrorCode.RANK_NOT_SAVED);
        }
        repository.saveRankGame(rankGameDTO.getMember1No(), rankGameDTO.getMember2No());
    }

    /* modify : 랭크게임 결과 업데이트 */
    public void modifyGameResult(RankGameDTO rankGameDTO) throws MyException {
        Integer gr = rankGameDTO.getGameResult();
        if (gr != 1 && gr != 2 && gr != 0) {
            throw new MyException(ErrorCode.RANK_GAME_NOT_MODIFIED);
        }
        Optional<RankGame> optRG = repository.findById(rankGameDTO.getRankNo());
        RankGame rankGameEntity = optRG.get();
        rankGameEntity.modifyGameResult(rankGameDTO.getGameResult());
        repository.save(rankGameEntity);
    }

    /* read: 랭크게임 전적 조회 */
    public List<MyRankDTO> findAllByMemberNo(Long memberNo) throws MyException {
        List<Object[]> list = repository.findListByMemberNo(memberNo);
        List<MyRankDTO> rankGameDTOList = new ArrayList<>();
        for (Object[] objArr : list) {
            if(objArr[5]==null) continue;
            Long result=Long.valueOf(String.valueOf(objArr[5]));
            Long member1No=Long.valueOf(String.valueOf(objArr[1]));
            Long member2No=Long.valueOf(String.valueOf(objArr[3]));
            MyRankDTO dto= MyRankDTO.builder().rankNo(Long.valueOf(String.valueOf(objArr[0]))).build();
            if(memberNo==member1No) {
                dto.setOpposingNo(member2No);
                dto.setOpposingName(String.valueOf(objArr[4]));
                if(result==0) dto.setGameResult("DRAW");
                else if(result==1) dto.setGameResult("WIN");
                else if(result==2) dto.setGameResult("LOSE");
            } else {
                dto.setOpposingNo(member1No);
                dto.setOpposingName(String.valueOf(objArr[2]));
                if(result==0) dto.setGameResult("DRAW");
                else if(result==1) dto.setGameResult("LOSE");
                else if(result==2) dto.setGameResult("WIN");
            }
            rankGameDTOList.add(dto);
        }
        return rankGameDTOList;
    }
}
