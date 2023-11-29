package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    public Page<Room> findAll(Pageable pageable);

    public Page<Room> findAllByRoomStatus(Integer roomStatus, Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE room\n" + "SET room_status = 0\n" + "WHERE room_no = :roomNo",
            nativeQuery = true)
    public void updateRoom(@Param("roomNo") Long roomNo);
}
