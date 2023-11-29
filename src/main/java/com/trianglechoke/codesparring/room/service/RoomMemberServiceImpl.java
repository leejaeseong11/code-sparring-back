package com.trianglechoke.codesparring.room.service;

import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;

import org.springframework.stereotype.Service;

@Service
public class RoomMemberServiceImpl implements RoomMemberService {
    @Override
    public boolean isMemberInRoom(Long memberNo) {
        return false;
    }

    public void addRoomMember(RoomMemberDTO roomMemberDTO) {}

    public void removeMember(RoomMemberDTO roomMemberDTO) {}
}
