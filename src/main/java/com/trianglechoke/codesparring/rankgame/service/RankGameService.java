package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import com.trianglechoke.codesparring.rankgame.repository.RankGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RankGameService {
    @Autowired private RankGameRepository repository;

    /* save : 랭크게임 정보 저장 */
    public void addRankGame(RankGameDTO rankGameDTO) throws MyException {
        repository.saveRankGame(rankGameDTO.getMember1No(), rankGameDTO.getMember2No());
    }

    /* modify : 랭크게임 결과 업데이트 */
    public void modifyGameResult(RankGameDTO rankGameDTO) throws MyException {
        Optional<RankGame> optRG=repository.findById(rankGameDTO.getRankNo());
        RankGame rankGameEntity=optRG.get();
        rankGameEntity.modifyGameResult(rankGameDTO.getGameResult());
        repository.save(rankGameEntity);
    }
}
