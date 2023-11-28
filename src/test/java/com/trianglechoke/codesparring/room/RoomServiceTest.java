package com.trianglechoke.codesparring.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;
import com.trianglechoke.codesparring.room.service.RoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    Room room;
    List<Room> roomList = new ArrayList<>();
    @Mock RoomRepository repository;

    @InjectMocks RoomService service;

    @BeforeEach
    void init() {
        Member member =
                Member.builder()
                        .memberNo(1L)
                        .memberId("tester")
                        .memberPwd("123123")
                        .memberName("테스터")
                        .memberProfileImg(1)
                        .memberLevel(1L)
                        .memberExp(10)
                        .memberTier("SILVER")
                        .tierPoint(20L)
                        .memberStatus(1)
                        .loseCnt(1L)
                        .winCnt(1L)
                        .drawCnt(1L)
                        .adminStatus(1)
                        .build();
        Quiz quiz =
                Quiz.builder()
                        .quizNo(1L)
                        .member(member)
                        .quizTitle("문제 제목")
                        .quizContent("문제 내용")
                        .quizTier("SILVER")
                        .quizSubmitCnt(0)
                        .quizSuccessCnt(0)
                        .outputType("int")
                        .build();

        room =
                Room.builder()
                        .roomNo(1L)
                        .quiz(quiz)
                        .codeShare(0)
                        .roomTitle("테스트 방")
                        .roomStatus(1)
                        .build();

        roomList.add(room);
    }

    @Test
    @DisplayName("대기방 상세 조회")
    void findRoom() {
        when(repository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO roomDTO = service.findRoomByRoomNo(1L);

        assertThat(roomDTO.getRoomTitle()).isEqualTo("테스트 방");
    }

    @Test
    @DisplayName("대기방 상세 조회 - 조회할 방이 없는 경우")
    void findRoomException() {
        assertThatThrownBy(() -> service.findRoomByRoomNo(2L)).isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("대기방 전체 조회")
    void findAllRoom() {
        when(repository.findAll()).thenReturn(roomList);

        List<RoomDTO> roomDTOs = service.findRoomList(null);

        assertThat(roomDTOs.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("대기방 전체 조회 - 대기 중인 방")
    void findAllRoomInGame() {
        when(repository.findAllByRoomStatus(1)).thenReturn(roomList);

        List<RoomDTO> roomDTOs = service.findRoomList(1);

        assertThat(roomDTOs.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("대기방 추가")
    void createRoom() {
        Room testRoom =
                Room.builder()
                        .roomNo(2L)
                        .quiz(Quiz.builder().build())
                        .codeShare(1)
                        .roomTitle("테스트 방2")
                        .roomStatus(0)
                        .build();
        when(repository.save(any())).thenReturn(testRoom);

        Long registeredRoomNo =
                service.addRoom(
                        RoomDTO.builder()
                                .roomNo(2L)
                                .quiz(Quiz.builder().build())
                                .codeShare(1)
                                .roomTitle("테스트 방2")
                                .roomStatus(0)
                                .build());

        assertThat(registeredRoomNo).isEqualTo(2L);
    }

    @Test
    @DisplayName("대기방 수정")
    void updateRoom() {
        when(repository.findById(1L)).thenReturn(Optional.of(room));

        service.modifyRoomStatusByRoomNo(1L);

        Mockito.verify(repository, Mockito.times(1)).updateRoom(1L);
    }

    @Test
    @DisplayName("대기방 수정 - 수정할 방이 없을 경우")
    void updateRoomException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.modifyRoomStatusByRoomNo(1L))
                .isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("대기방 삭제")
    void deleteRoom() {
        when(repository.findById(1L)).thenReturn(Optional.of(room));

        service.removeRoomByRoomNo(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("대기방 삭제 - 삭제할 방이 없을 경우")
    void deleteRoomException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.removeRoomByRoomNo(1L)).isInstanceOf(MyException.class);
    }
}
