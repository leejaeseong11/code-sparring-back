package com.trianglechoke.codesparring.member.dao;

import com.trianglechoke.codesparring.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);

    boolean existsByMemberId(String memberId);

    /* 랭킹 10위까지 : tierPoint 높은 순*/
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
                            + "    rank <= 10",
            nativeQuery = true)
    List<Object[]> findRankedMember();
}
