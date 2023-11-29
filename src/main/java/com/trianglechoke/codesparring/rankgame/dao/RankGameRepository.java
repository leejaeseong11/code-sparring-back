package com.trianglechoke.codesparring.rankgame.dao;

import com.trianglechoke.codesparring.rankgame.entity.RankGame;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankGameRepository extends JpaRepository<RankGame, Long> {
    /* 랭크 게임 정보 추가 */
    @Modifying
    @Query(
            value =
                    "INSERT INTO rank_game (rank_no, member1_no, member2_no)\n"
                            + "VALUES (rank_no_seq.NEXTVAL, :member1No, :member2No)",
            nativeQuery = true)
    @Transactional
    public void saveRankGame(Long member1No, Long member2No);

    /* 회원의 랭크 게임 목록 조회 */
    @Query(
            value =
                    "SELECT r.rank_no, r.member1_no, m1.member_name, r.member2_no, m2.member_name,"
                            + " game_result \n"
                            + "FROM rank_game r\n"
                            + "JOIN \"MEMBER\" m1 ON r.member1_no=m1.member_no\n"
                            + "JOIN \"MEMBER\" m2 ON r.member2_no=m2.member_no\n"
                            + "WHERE member1_no=:memberNo OR member2_no=:memberNo ORDER BY rank_no",
            nativeQuery = true)
    public List<Object[]> findListByMemberNo(Long memberNo);
}
