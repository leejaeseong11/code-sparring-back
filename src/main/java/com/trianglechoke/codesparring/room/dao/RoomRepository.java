package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    public List<Room> findAllByRoomStatus(Integer roomStatus, Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE room\n" + "SET room_status = 0\n" + "WHERE room_no = :roomNo",
            nativeQuery = true)
    public void updateRoom(@Param("roomNo") Long roomNo);
}
