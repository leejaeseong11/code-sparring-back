package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dto.PageGroup;
import com.trianglechoke.codesparring.rankgame.dao.RankGameRepository;
import com.trianglechoke.codesparring.rankgame.dao.RankRoomRepository;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RankGameServiceImpl implements RankGameService {
    @Autowired private RankGameRepository repository;

    /* Read : 랭크게임 전적 조회 */
    public PageGroup<MyRankDTO> findAllByMemberNo(Long memberNo, Integer currentPage)
            throws MyException {
        if (currentPage < 1) currentPage = 1;
        Integer cntPerPage = 9;

        Integer start;
        Integer end;
        end = currentPage * cntPerPage;
        start = (currentPage - 1) * cntPerPage + 1;

        RankGame exampleRankGame1 =
                RankGame.builder().member1(Member.builder().memberNo(memberNo).build()).build();
        ExampleMatcher exampleMatcher1 =
                ExampleMatcher.matchingAll()
                        .withMatcher("gameResult", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withIgnoreNullValues();
        Example<RankGame> example1 = Example.of(exampleRankGame1, exampleMatcher1);
        Long cnt = repository.count(example1);

        RankGame exampleRankGame2 =
                RankGame.builder().member2(Member.builder().memberNo(memberNo).build()).build();
        ExampleMatcher exampleMatcher2 =
                ExampleMatcher.matchingAll()
                        .withMatcher("gameResult", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withIgnoreNullValues();
        Example<RankGame> example2 = Example.of(exampleRankGame2, exampleMatcher2);
        cnt += repository.count(example2);
        List<MyRankDTO> rankGameDTOList = new ArrayList<>();

        if (cnt == 0) throw new MyException(ErrorCode.RANK_GAME_NOT_FOUND);

        List<Object[]> list = repository.findListByMemberNo(memberNo, start, end);
        String tier = "";
        Long point = 0L;
        Long nextPoint = 0L;
        String memberName = "";
        Long win = 0L, lose = 0L, draw = 0L;

        if (list.size() != 0) {
            if (Long.valueOf(String.valueOf(list.get(0)[2])) == memberNo) {
                tier = String.valueOf(list.get(0)[4]);
                point = Long.valueOf(String.valueOf(list.get(0)[9]));
                memberName = String.valueOf(list.get(0)[3]);
                win = Long.valueOf(String.valueOf(list.get(0)[11]));
                lose = Long.valueOf(String.valueOf(list.get(0)[12]));
                draw = Long.valueOf(String.valueOf(list.get(0)[13]));
            } else {
                tier = String.valueOf(list.get(0)[7]);
                point = Long.valueOf(String.valueOf(list.get(0)[10]));
                memberName = String.valueOf(list.get(0)[6]);
                win = Long.valueOf(String.valueOf(list.get(0)[14]));
                lose = Long.valueOf(String.valueOf(list.get(0)[15]));
                draw = Long.valueOf(String.valueOf(list.get(0)[16]));
            }
        }

        for (Object[] objArr : list) {
            if (objArr[8] == null) continue;
            Long result = Long.valueOf(String.valueOf(objArr[8]));
            Long member1No = Long.valueOf(String.valueOf(objArr[2]));
            Long member2No = Long.valueOf(String.valueOf(objArr[5]));
            MyRankDTO dto =
                    MyRankDTO.builder().rankNo(Long.valueOf(String.valueOf(objArr[1]))).build();
            if (memberNo == member1No) {
                dto.setOpposingNo(member2No);
                dto.setOpposingName(String.valueOf(objArr[6]));
                if (result == 0) dto.setGameResult("DRAW");
                else if (result == 1) dto.setGameResult("WIN");
                else if (result == 2) dto.setGameResult("LOSE");
            } else {
                dto.setOpposingNo(member1No);
                dto.setOpposingName(String.valueOf(objArr[3]));
                if (result == 0) dto.setGameResult("DRAW");
                else if (result == 1) dto.setGameResult("LOSE");
                else if (result == 2) dto.setGameResult("WIN");
            }
            rankGameDTOList.add(dto);
        }

        if (tier.equals("BRONZE")) nextPoint = 1000L;
        else if (tier.equals("SILVER")) nextPoint = 5000L;
        else if (tier.equals("GOLD")) nextPoint = 15000L;

        if (rankGameDTOList.size() == 0) {
            MyRankDTO tmpDto = new MyRankDTO();
            rankGameDTOList.add(tmpDto);
        }

        rankGameDTOList.get(0).setMyTier(tier);
        rankGameDTOList.get(0).setMyPoint(point);
        rankGameDTOList.get(0).setNextPoint(nextPoint);
        rankGameDTOList.get(0).setMemberName(memberName);
        rankGameDTOList.get(0).setWin(win);
        rankGameDTOList.get(0).setLose(lose);
        rankGameDTOList.get(0).setDraw(draw);

        PageGroup<MyRankDTO> pg = new PageGroup<>(rankGameDTOList, currentPage, cnt);
        return pg;
    }

    /* Read : 랭크번호로 랭크 조회 */
    public RankGameDTO findByRankNo(Long rankNo) throws MyException {
        Optional<RankGame> optRg = repository.findById(rankNo);
        RankGame entity = optRg.get();

        RankGameDTO dto =
                RankGameDTO.builder()
                        .member1No(entity.getMember1().getMemberNo())
                        .member2No(entity.getMember2().getMemberNo())
                        .member1Name(entity.getMember1().getMemberName())
                        .member2Name(entity.getMember2().getMemberName())
                        .quizNo(entity.getQuizNo())
                        .build();

        return dto;
    }

    @Autowired private RankRoomRepository roomRepository;

    /* Update : 랭크게임 결과 업데이트 */
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
        } else calculateRankPoint(rankGameDTO);

        roomRepository.deleteById(rankGameEntity.getRankNo());
    }

    @Autowired private MemberRepository memberRepository;

    /* Member Update : 회원 point 업데이트 */
    private void modifyPoint(Long memberNo, Integer point) {
        Optional<Member> optM = memberRepository.findById(memberNo);
        Member memberEntity = optM.get();
        memberEntity.modifyPoint(point);
        String tier = calculateTier(memberEntity.getTierPoint());
        memberEntity.modifyTier(tier);
        memberRepository.save(memberEntity);
    }

    /* Member Update : 회원 승리, 패배, 무승부 횟수 업데이트 */
    private void modifyCnt(Long memberNo, Integer gameResult) {
        Optional<Member> optM = memberRepository.findById(memberNo);
        Member memberEntity = optM.get();
        memberEntity.modifyCnt(gameResult);
        memberRepository.save(memberEntity);
    }

    /* Method : tier 계산 */
    private String calculateTier(Long point) {
        if (point >= 15000L) return "PLATINUM";
        else if (point >= 5000L) return "GOLD";
        else if (point >= 1000L) return "SILVER";
        else return "BRONZE";
    }

    /* Method : rank point 계산 */
    private void calculateRankPoint(RankGameDTO rankGameDTO) throws MyException {
        Optional<RankGame> optRG = repository.findById(rankGameDTO.getRankNo());
        RankGame rankGame = optRG.get();
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
    }
}
