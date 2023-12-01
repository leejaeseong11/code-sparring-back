package com.trianglechoke.codesparring.room;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.room.dao.RoomMemberRepository;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;
import com.trianglechoke.codesparring.room.entity.Room;
import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;
import com.trianglechoke.codesparring.room.service.RoomMemberServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomMemberServiceTest {
    @Mock RoomMemberRepository repository;
    @Mock MemberRepository memberRepository;
    @Mock RoomRepository roomRepository;

    @InjectMocks RoomMemberServiceImpl service;

    @Test
    @DisplayName("방회원 추가")
    void addRoomMember() {
        Room testRoom = Room.builder().roomNo(1L).roomStatus(1).build();
        when(roomRepository.findById(any())).thenReturn(Optional.of(testRoom));
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder()
                        .id(new RoomMemberKey(testRoom.getRoomNo(), testMember))
                        .hostStatus(0)
                        .build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();
        Long roomNumber = service.addRoomMember(null, roomMemberDTO);

        assertThat(roomNumber).isEqualTo(roomMemberDTO.getRoomNo());
    }

    @Test
    @DisplayName("방회원 추가 - 비밀방")
    void addRoomMember2() {
        Room testRoom = Room.builder().roomNo(1L).roomStatus(1).roomPwd("1111").build();
        when(roomRepository.findById(any())).thenReturn(Optional.of(testRoom));
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder()
                        .id(new RoomMemberKey(testRoom.getRoomNo(), testMember))
                        .hostStatus(0)
                        .build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();
        Long roomNumber = service.addRoomMember("1111", roomMemberDTO);

        assertThat(roomNumber).isEqualTo(roomMemberDTO.getRoomNo());
    }

    @Test
    @DisplayName("방회원 추가 - 방이 없는 경우")
    void addRoomMemberException1() {
        when(roomRepository.findById(any())).thenReturn(Optional.<Room>empty());
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder().id(new RoomMemberKey(1L, testMember)).hostStatus(0).build();
        lenient().when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        lenient().when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();

        assertThatThrownBy(() -> service.addRoomMember(null, roomMemberDTO))
                .isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("방회원 추가 - 이미 게임이 시작된 방인 경우")
    void addRoomMemberException2() {
        Room testRoom = Room.builder().roomNo(1L).roomStatus(0).build();
        when(roomRepository.findById(any())).thenReturn(Optional.of(testRoom));
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder()
                        .id(new RoomMemberKey(testRoom.getRoomNo(), testMember))
                        .hostStatus(0)
                        .build();
        lenient().when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        lenient().when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();

        assertThatThrownBy(() -> service.addRoomMember(null, roomMemberDTO))
                .isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("방회원 추가 - 비밀방 암호가 잘못된 경우")
    void addRoomMemberException3() {
        Room testRoom = Room.builder().roomNo(1L).roomStatus(1).roomPwd("1111").build();
        when(roomRepository.findById(any())).thenReturn(Optional.of(testRoom));
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder()
                        .id(new RoomMemberKey(testRoom.getRoomNo(), testMember))
                        .hostStatus(0)
                        .build();
        lenient().when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        lenient().when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();

        assertThatThrownBy(() -> service.addRoomMember("0000", roomMemberDTO))
                .isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("방회원 추가 - 이미 참여한 방이 있는 경우")
    void addRoomMemberException4() {
        Room testRoom = Room.builder().roomNo(1L).roomStatus(1).build();
        when(roomRepository.findById(any())).thenReturn(Optional.of(testRoom));
        Member testMember = Member.builder().memberNo(1L).build();
        RoomMember roomMember =
                RoomMember.builder().id(new RoomMemberKey(1L, testMember)).hostStatus(0).build();
        when(repository.findByIdMemberMemberNo(any())).thenReturn(Optional.of(roomMember));
        lenient().when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        lenient().when(repository.save(any())).thenReturn(roomMember);

        RoomMemberDTO roomMemberDTO =
                RoomMemberDTO.builder().roomNo(1L).memberNo(1L).hostStatus(0).build();

        assertThatThrownBy(() -> service.addRoomMember(null, roomMemberDTO))
                .isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("방회원 삭제")
    void removeMember() {
        Member testMember = Member.builder().memberNo(1L).build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        RoomMember roomMember =
                RoomMember.builder().id(new RoomMemberKey(1L, testMember)).hostStatus(0).build();
        when(repository.findByIdMemberMemberNo(any())).thenReturn(Optional.of(roomMember));

        service.removeMember(1L);

        verify(repository, times(1)).deleteByIdMemberMemberNo(1L);
    }

    @Test
    @DisplayName("방회원 삭제 - 회원이 방에 없는 경우")
    void removeMemberException() {
        Member testMember = Member.builder().memberNo(1L).build();
        when(memberRepository.findById(any())).thenReturn(Optional.of(testMember));
        when(repository.findByIdMemberMemberNo(any())).thenReturn(Optional.<RoomMember>empty());

        assertThatThrownBy(() -> service.removeMember(1L)).isInstanceOf(MyException.class);
    }
}
