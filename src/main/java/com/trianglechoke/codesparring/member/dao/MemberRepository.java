package com.trianglechoke.codesparring.member.dao;

import com.trianglechoke.codesparring.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByMemberName(String memberName);

    boolean existsByMemberId(String memberId);

    boolean existsByMemberName(String memberName);

    /** 랭킹 10위까지 : tierPoint 높은 순 */
    @Query(
            value =
                    "SELECT member_no, member_name, member_tier, rank\n"
                            + "FROM (\n"
                            + "    SELECT\n"
                            + "        m.member_no,\n"
                            + "        m.member_name, \n"
                            + "        m.member_tier,\n"
                            + "        RANK() OVER (ORDER BY m.tier_point DESC) AS rank \n"
                            + "    FROM \n"
                            + "        member m \n"
                            + "    WHERE \n"
                            + "        m.member_status = 1\n"
                            + ") \n"
                            + "WHERE \n"
                            + "    rank <= 5",
            nativeQuery = true)
    List<Object[]> findRankedMember();

    /** 일반모드 승리 시 경험치 추가 */
    @Modifying
    @Query(
            value =
                    "UPDATE Member m\n"
                            + "SET m.member_exp = CASE\n"
                            + "                   WHEN m.member_exp + :roomSize >= 100 THEN"
                            + " m.member_exp + :roomSize - 100\n"
                            + "                   ELSE m.member_exp + :roomSize\n"
                            + "                END,\n"
                            + "    m.member_level = CASE\n"
                            + "                     WHEN m.member_exp + :roomSize >= 100 THEN"
                            + " m.member_level + 1\n"
                            + "                     ELSE m.member_level\n"
                            + "                  END\n"
                            + "WHERE m.member_no = :memberNo\n",
            nativeQuery = true)
    void updateMemberExp(@Param("memberNo") Long memberNo, @Param("roomSize") int roomSize);
}
