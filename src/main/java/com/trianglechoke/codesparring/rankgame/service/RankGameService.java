package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
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
    public List<RankGameDTO> findAllByMemberNo(Long memberNo) throws MyException {
        List<Object[]> list=repository.findListByMemberNo(memberNo);
        List<RankGameDTO> rankGameDTOList=new ArrayList<>();
        for(Object[] objArr:list) {
            if(objArr[5]==null) continue;
            RankGameDTO dto=RankGameDTO.builder()
                    .rankNo(Long.valueOf(String.valueOf(objArr[0])))
                    .member1No(Long.valueOf(String.valueOf(objArr[1])))
                    .member1Name(String.valueOf(objArr[2]))
                    .member2No(Long.valueOf(String.valueOf(objArr[3])))
                    .member2Name(String.valueOf(objArr[4]))
                    .gameResult(Integer.valueOf(String.valueOf(objArr[5])))
                    .build();
            rankGameDTOList.add(dto);
        }
        return rankGameDTOList;
    }
}
