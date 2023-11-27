package com.trianglechoke.codesparring.room.dao;

import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Query(
            value = "UPDATE room\n" + "SET room_status = 0\n" + "WHERE room_no = :roomNo",
            nativeQuery = true)
    public void modifyRoom(Long roomNo);

    @Query(
            value =
                    "SELECT *\n"
                            + "FROM room r\n"
                            + "JOIN room_member rm\n"
                            + "ON r.room_no = rm.room_no\n"
                            + "JOIN member m\n"
                            + "ON m.member_no = rm.member_no\n"
                            + "WHERE r.room_no = :roomNo",
            nativeQuery = true)
    public RoomDTO selectRoom(Long roomNo);

    @Query(
            value =
                    "SELECT *\n"
                            + "FROM room r\n"
                            + "JOIN room_member rm\n"
                            + "ON r.room_no = rm.room_no\n"
                            + "JOIN member m\n"
                            + "ON m.member_no = rm.member_no",
            nativeQuery = true)
    public List<RoomDTO> selectRoomList();
}
