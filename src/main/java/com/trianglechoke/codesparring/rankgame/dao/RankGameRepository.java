package com.trianglechoke.codesparring.rankgame.dao;

import com.trianglechoke.codesparring.rankgame.entity.RankGame;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankGameRepository extends JpaRepository<RankGame, Long> {
    /* 회원의 랭크 게임 목록 조회 */
    @Query(
            value =
                    "SELECT *\n"
                        + "FROM (\n"
                        + "    SELECT ROWNUM AS rn, q.*\n"
                        + "    FROM (\n"
                        + "        SELECT\n"
                        + "            r.rank_no,\n"
                        + "            r.member1_no,\n"
                        + "            m1.member_name AS member1_name,\n"
                        + "            m1.member_tier AS member1_tier,\n"
                        + "            r.member2_no,\n"
                        + "            m2.member_name AS member2_name,\n"
                        + "            m2.member_tier AS member2_tier,\n"
                        + "            r.game_result,\n"
                        + "            m1.tier_point AS member1_point,\n"
                        + "            m2.tier_point AS member2_point,\n"
                        + "            m1.win_cnt AS member1_win, m1.lose_cnt AS member1_lose,"
                        + " m1.draw_cnt AS member1_draw,\n"
                        + "            m2.win_cnt, m2.lose_cnt, m2.draw_cnt\n"
                        + "        FROM rank_game r\n"
                        + "        JOIN \"MEMBER\" m1 ON r.member1_no = m1.member_no\n"
                        + "        JOIN \"MEMBER\" m2 ON r.member2_no = m2.member_no\n"
                        + "        WHERE :memberNo IN (r.member1_no, r.member2_no)\n"
                        + "        ORDER BY rank_no\n"
                        + "    ) q\n"
                        + ")\n"
                        + "WHERE rn BETWEEN :start AND :end",
            nativeQuery = true)
    public List<Object[]> findListByMemberNo(
            @Param("memberNo") Long memberNo,
            @Param("start") Integer start,
            @Param("end") Integer end);

    /* 랭크 게임 정보 추가 */
    @Modifying
    @Query(
            value =
                    "INSERT INTO rank_game (rank_no, member1_no, member2_no)\n"
                            + "VALUES (rank_no_seq.NEXTVAL, :member1No, :member2No)",
            nativeQuery = true)
    @Transactional
    public void saveRankGame(
            @Param("member1No") Long member1No, @Param("member2No") Long member2No);
}
