package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;

public interface RankMatchService {
    /**
     * 랭크 번호로 랭크를 조회한다
     * @param rankNo 랭크 번호
     * @return 랭크
     * @throws MyException
     */
    public RankGameDTO findByRankNo(Long rankNo) throws MyException;

    /**
     * 티어에 해당하면서 플레이어2가 존재하지 않는 랭크를 조회한다
     * @param tier 티어
     * @return 랭크
     * @throws MyException
     */
    public Long findByRankTier(String tier) throws MyException;

    /**
     * 랭크를 저장한다
     * @param memberNo 회원 번호
     * @throws MyException
     */
    public void saveRankGame(Long memberNo) throws MyException;

    /**
     * 랭크에 플레이어2를 업데이트한다
     * @param rankNo 랭크 번호
     * @param memberNo 회원 번호
     * @throws MyException
     */
    public void matchingMember(Long rankNo, Long memberNo) throws MyException;

    /**
     * 랭크에 준비된 플레이어 수를 업데이트한다
     * @param rankNo 랭크 번호
     * @throws MyException
     */
    public void memberReady(Long rankNo) throws MyException;

    /**
     * 랭크 문제를 랜덤 매칭한다
     * @param rankNo 랭크 번호
     * @return 문제 번호
     * @throws MyException
     */
    public Long modifyGameQuiz(Long rankNo) throws MyException;

    /**
     * 랭크를 삭제한다
     * @param rankNo 랭크 번호
     * @throws MyException
     */
    public void removeMatching(Long rankNo) throws MyException;
}
