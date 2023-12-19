package com.trianglechoke.codesparring.rankgame.dao;

import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import com.trianglechoke.codesparring.rankgame.entity.RankRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRoomRepository extends JpaRepository<RankRoom, Long> {}
