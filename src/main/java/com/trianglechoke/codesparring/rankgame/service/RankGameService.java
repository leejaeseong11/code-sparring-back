package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.repository.MemberRepository;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import com.trianglechoke.codesparring.rankgame.dao.RankGameRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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

        if (rankGameDTO.getGameResult() == 0) {
            modifyCnt(rankGameEntity.getMember1().getMemberNo(), 0);
            modifyCnt(rankGameEntity.getMember2().getMemberNo(), 0);
            return;
        } else calculateRankPoint(rankGameDTO);
    }

    /* read: 랭크게임 전적 조회 */
    public List<MyRankDTO> findAllByMemberNo(Long memberNo) throws MyException {
        List<Object[]> list = repository.findListByMemberNo(memberNo);
        List<MyRankDTO> rankGameDTOList = new ArrayList<>();
        for (Object[] objArr : list) {
            if (objArr[5] == null) continue;
            Long result = Long.valueOf(String.valueOf(objArr[5]));
            Long member1No = Long.valueOf(String.valueOf(objArr[1]));
            Long member2No = Long.valueOf(String.valueOf(objArr[3]));
            MyRankDTO dto =
                    MyRankDTO.builder().rankNo(Long.valueOf(String.valueOf(objArr[0]))).build();
            if (memberNo == member1No) {
                dto.setOpposingNo(member2No);
                dto.setOpposingName(String.valueOf(objArr[4]));
                if (result == 0) dto.setGameResult("DRAW");
                else if (result == 1) dto.setGameResult("WIN");
                else if (result == 2) dto.setGameResult("LOSE");
            } else {
                dto.setOpposingNo(member1No);
                dto.setOpposingName(String.valueOf(objArr[2]));
                if (result == 0) dto.setGameResult("DRAW");
                else if (result == 1) dto.setGameResult("LOSE");
                else if (result == 2) dto.setGameResult("WIN");
            }
            rankGameDTOList.add(dto);
        }
        return rankGameDTOList;
    }

    @Autowired private MemberRepository memberRepository;

    /* RankGame - member point modify */
    private void modifyPoint(Long memberNo, Integer point) {
        Optional<Member> optM = memberRepository.findById(memberNo);
        Member memberEntity = optM.get();
        memberEntity.modifyPoint(point);
        String tier = calculateTier(memberEntity.getTierPoint());
        memberEntity.modifyTier(tier);
        memberRepository.save(memberEntity);
    }

    /* RankGame - member cnt modify : winCnt, loseCnt, drawCnt */
    private void modifyCnt(Long memberNo, Integer gameResult) {
        Optional<Member> optM = memberRepository.findById(memberNo);
        Member memberEntity = optM.get();
        memberEntity.modifyCnt(gameResult);
        memberRepository.save(memberEntity);
    }

    /* RankGame - member tier calculate */
    private String calculateTier(Long point) {
        if (point >= 15000L) return "PLATINUM";
        else if (point >= 5000L) return "GOLD";
        else if (point >= 1000L) return "SILVER";
        else return "BRONZE";
    }

    /* point 적용 메소드 */
    private void calculateRankPoint(RankGameDTO rankGameDTO) throws MyException {
        Optional<RankGame> optRG = repository.findById(rankGameDTO.getRankNo());
        RankGame rankGame = optRG.get();
        // member 간 tier 계산
        String tier1 = rankGame.getMember1().getMemberTier();
        String tier2 = rankGame.getMember2().getMemberTier();

        if (tier1.equals(tier2)) {
            // 동일한 tier : 100만큼 증가하거나 감소
            if (rankGameDTO.getGameResult() == 1) {
                modifyPoint(rankGame.getMember1().getMemberNo(), 100);
                modifyPoint(rankGame.getMember2().getMemberNo(), -100);
                modifyCnt(rankGame.getMember1().getMemberNo(), 1);
                modifyCnt(rankGame.getMember2().getMemberNo(), -1);
            } else if (rankGameDTO.getGameResult() == 2) {
                modifyPoint(rankGame.getMember1().getMemberNo(), -100);
                modifyPoint(rankGame.getMember2().getMemberNo(), 100);
                modifyCnt(rankGame.getMember1().getMemberNo(), -1);
                modifyCnt(rankGame.getMember2().getMemberNo(), 1);
            }
        } else {
            // 다른 tier : 100*(tier 차이+1)만큼 증가하거나 감소
            // 더 높은 tier 가 이기거나 더 낮은 tier 가 지는 경우 : 50 증가/감소로 고정
            String[] tiers = {"BRONZE", "SILVER", "GOLD", "PLATINUM"};
            int idx1 = -1, idx2 = -1;
            for (int i = 0; i < tiers.length; i++) {
                if (tier1.equals(tiers[i])) idx1 = i;
                if (tier2.equals(tiers[i])) idx2 = i;
            }

            if (rankGameDTO.getGameResult() == 1) {
                if (idx1 > idx2) {
                    modifyPoint(rankGame.getMember1().getMemberNo(), 50);
                    modifyPoint(rankGame.getMember2().getMemberNo(), -50);
                } else {
                    modifyPoint(rankGame.getMember1().getMemberNo(), 100 * (idx2 - idx1 + 1));
                    modifyPoint(rankGame.getMember2().getMemberNo(), -100 * (idx2 - idx1 + 1));
                }
                modifyCnt(rankGame.getMember1().getMemberNo(), 1);
                modifyCnt(rankGame.getMember2().getMemberNo(), -1);
            } else if (rankGameDTO.getGameResult() == 2) {
                if (idx1 > idx2) {
                    modifyPoint(rankGame.getMember1().getMemberNo(), -100 * (idx1 - idx2 + 1));
                    modifyPoint(rankGame.getMember2().getMemberNo(), 100 * (idx1 - idx2 + 1));
                } else {
                    modifyPoint(rankGame.getMember1().getMemberNo(), -50);
                    modifyPoint(rankGame.getMember2().getMemberNo(), 50);
                }
                modifyCnt(rankGame.getMember1().getMemberNo(), -1);
                modifyCnt(rankGame.getMember2().getMemberNo(), 1);
            }
        }
    }
}
