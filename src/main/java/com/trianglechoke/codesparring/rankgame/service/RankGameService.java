package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.MyRankDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;

import java.util.List;

public interface RankGameService {
    /**
     * 회원의 랭크 게임 전적을 조회한다.
     * @param memberNo 회원 번호
     * @param start 시작
     * @param end 끝
     * @return 회원의 랭크 게임 전적 목록
     * @throws MyException
     */
    public List<MyRankDTO> findAllByMemberNo(Long memberNo, Integer start, Integer end) throws MyException;

    /**
     * 랭크 게임 정보를 저장한다.
     * @param rankGameDTO 랭크 게임에 참여하는 회원 2명의 정보를 담은 객체
     * @throws MyException
     */
    public void addRankGame(RankGameDTO rankGameDTO) throws MyException;

    /**
     * 랭크 게임 결과를 업데이트한다.
     * @param rankGameDTO 랭크 게임 결과를 담은 객체
     * @throws MyException
     */
    public void modifyGameResult(RankGameDTO rankGameDTO) throws MyException;
}
