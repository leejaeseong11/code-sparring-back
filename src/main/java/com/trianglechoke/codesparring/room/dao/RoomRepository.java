package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByOrderByRoomStatusDescRoomDtDesc(Pageable pageable);

    @Query(
            value =
                    "SELECT * FROM room WHERE room_no LIKE CONCAT(:roomNo, '%') ORDER BY"
                            + " room_status DESC, room_dt DESC",
            nativeQuery = true)
    Page<Room> findByRoomNoStartsWithOrderByRoomStatusDescRoomDtDesc(
            @Param("roomNo") Long roomNo, Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE room\n" + "SET room_status = 0\n" + "WHERE room_no = :roomNo",
            nativeQuery = true)
    void updateRoom(@Param("roomNo") Long roomNo);

    void deleteById(Long id);
}
