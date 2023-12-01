package com.trianglechoke.codesparring.room.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.*;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.room.dao.RoomMemberRepository;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;
import com.trianglechoke.codesparring.room.entity.Room;
import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomMemberServiceImpl implements RoomMemberService {
    @Autowired private RoomMemberRepository repository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private RoomRepository roomRepository;

    private boolean isMemberInRoom(Long memberNo) {
        return repository.findByIdMemberMemberNo(memberNo).isPresent();
    }

    private Member findMember(Long memberNo) {
        Optional<Member> selectedMember = memberRepository.findById(memberNo);
        if (selectedMember.isPresent()) {
            return selectedMember.get();
        } else {
            throw new MyException(MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    public Long addRoomMember(String roomPwd, RoomMemberDTO roomMemberDTO) {
        Optional<Room> selectedRoom = roomRepository.findById(roomMemberDTO.getRoomNo());
        if (selectedRoom.isEmpty()) {
            throw new MyException(ROOM_NOT_FOUND);
        }
        if (selectedRoom.get().getRoomStatus() == 0) {
            throw new MyException(ALREADY_STARTED_ROOM);
        }
        if (selectedRoom.get().getRoomPwd() != null
                && !selectedRoom.get().getRoomPwd().equals(roomPwd)) {
            throw new MyException(WRONG_ROOM_PASSWORD);
        }

        if (!this.isMemberInRoom(roomMemberDTO.getMemberNo())) {
            return repository
                    .save(
                            RoomMember.builder()
                                    .id(
                                            RoomMemberKey.builder()
                                                    .roomNo(roomMemberDTO.getRoomNo())
                                                    .member(findMember(roomMemberDTO.getMemberNo()))
                                                    .build())
                                    .hostStatus(roomMemberDTO.getHostStatus())
                                    .build())
                    .getId()
                    .getRoomNo();
        } else {
            throw new MyException(ALREADY_MEMBER_IN_ROOM);
        }
    }

    @Transactional
    public void removeMember(Long memberNo) {
        Member selectedMember = findMember(memberNo);
        if (this.isMemberInRoom(selectedMember.getMemberNo())) {
            repository.deleteByIdMemberMemberNo(memberNo);
        } else {
            throw new MyException(MEMBER_NOT_IN_ROOM);
        }
    }
}
