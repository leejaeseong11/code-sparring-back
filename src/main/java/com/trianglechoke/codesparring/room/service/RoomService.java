package com.trianglechoke.codesparring.room.service;

import com.trianglechoke.codesparring.room.dto.RoomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    /* 대기방 상세 조회 */
    RoomDTO findRoomByRoomNo(Long roomNo);

    /* 방 목록 조회 */
    Page<RoomDTO> findRoomList(Pageable pageable);

    /* 대기방 생성 */
    Long addRoom(RoomDTO roomDTO);

    /* 대기방 수정 - 게임방으로 변경 */
    void modifyRoomStatusByRoomNo(Long roomNo);

    /* 대기방 삭제 */
    void removeRoomByRoomNo(Long roomNo);
}
