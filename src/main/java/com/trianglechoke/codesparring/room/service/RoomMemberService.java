package com.trianglechoke.codesparring.room.service;

import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;

public interface RoomMemberService {
    /* 회원이 속한 방 조회 */
    boolean isMemberInRoom(Long memberNo);

    /* 대기방에 회원 추가 */
    void addRoomMember(RoomMemberDTO roomMemberDTO);

    /* 대기방에 회원 삭제 */
    void removeMember(RoomMemberDTO roomMemberDTO);
}
