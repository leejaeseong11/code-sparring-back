package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(
            value =
                    "SELECT *\n"
                            + "FROM room r\n"
                            + "JOIN room_member rm\n"
                            + "ON r.room_no = rm.room_no\n"
                            + "JOIN member m\n"
                            + "ON m.member_no = rm.member_no\n"
                            + "WHERE r.room_no = :RoomNo;",
            nativeQuery = true)
    public List<Object[]> selectByRoomNo(Long RoomNo);
}
