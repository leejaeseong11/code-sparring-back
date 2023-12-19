package com.trianglechoke.codesparring.room.service;

import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;

public interface RoomMemberService {
    /* 방 회원 검색 */
    boolean isMemberInRoom(Long memberNo);

    /* 대기방에 회원 추가 */
    Long addRoomMember(String roomPwd, RoomMemberDTO roomMemberDTO);

    /* 대기방에 회원 삭제 */
    void removeMember(Long memberNo);

    /* 로그인한 회원이 방장인지 확인 */
    Boolean isRoomMemberHost(Long memberNo);
}
