package com.trianglechoke.codesparring.rankgame.repository;

import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RankGameRepository extends JpaRepository<RankGame, Long> {
    @Modifying
    @Query(
            value =
                    "INSERT INTO rank_game (rank_no, member1_no, member2_no)\n"
                            + "VALUES (rank_no_seq.NEXTVAL, :member1No, :member2No)",
            nativeQuery = true)
    @Transactional
    public void saveRankGame(Long member1No, Long member2No);
}
