package com.trianglechoke.codesparring.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;
import com.trianglechoke.codesparring.room.entity.RoomMember;
import com.trianglechoke.codesparring.room.entity.RoomMemberKey;
import com.trianglechoke.codesparring.room.service.RoomServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    Room room;
    List<Room> roomList = new ArrayList<>();
    @Mock RoomRepository repository;
    @Mock QuizRepository quizRepository;

    @InjectMocks RoomServiceImpl service;

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
                        .build();
        List<RoomMember> roomMemberList = new ArrayList<>();
        roomMemberList.add(
                RoomMember.builder()
                        .id(RoomMemberKey.builder().member(member).roomNo(1L).build())
                        .build());
        room =
                Room.builder()
                        .roomNo(1L)
                        .quiz(quiz)
                        .codeShare(0)
                        .roomTitle("테스트 방")
                        .roomDt(LocalDateTime.of(2023, 12, 1, 12, 20, 0))
                        .roomMemberList(roomMemberList)
                        .roomStatus(1)
                        .build();

        roomList.add(room);
        roomList.add(
                Room.builder()
                        .roomNo(2L)
                        .quiz(quiz)
                        .codeShare(1)
                        .roomTitle("테스트 방2")
                        .roomDt(LocalDateTime.of(2023, 12, 1, 12, 0, 0))
                        .roomMemberList(roomMemberList)
                        .roomStatus(0)
                        .build());
        roomList.add(
                Room.builder()
                        .roomNo(3L)
                        .quiz(quiz)
                        .codeShare(1)
                        .roomTitle("테스트 방3")
                        .roomStatus(1)
                        .roomDt(LocalDateTime.of(2023, 12, 1, 12, 10, 0))
                        .roomMemberList(roomMemberList)
                        .build());
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
        assertThatThrownBy(() -> service.findRoomByRoomNo(10L)).isInstanceOf(MyException.class);
    }

    @Test
    @DisplayName("대기방 전체 조회")
    void findAllRoom() {
        Page<Room> mockRoomPage = new PageImpl(roomList);
        when(repository.findByOrderByRoomStatusDescRoomDtDesc(any(Pageable.class)))
                .thenReturn(mockRoomPage);

        Page<RoomDTO> roomDTOs = service.findRoomList(null, Pageable.unpaged());

        assertThat(roomDTOs.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("대기방 전체 조회 - 페이징 처리")
    void findAllRoomPaging() {
        Page<Room> mockRoomPage = new PageImpl<>(roomList, Pageable.ofSize(2), roomList.size());
        when(repository.findByOrderByRoomStatusDescRoomDtDesc(Pageable.ofSize(2)))
                .thenReturn(mockRoomPage);

        Page<RoomDTO> roomDTOs = service.findRoomList(null, Pageable.ofSize(2));

        assertThat(roomDTOs.getTotalElements()).isEqualTo(3);
        assertThat(roomDTOs.getTotalPages()).isEqualTo(2);
        assertThat(roomDTOs.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("대기방 추가")
    void createRoom() {
        Room testRoom =
                Room.builder()
                        .roomNo(10L)
                        .quiz(Quiz.builder().build())
                        .codeShare(1)
                        .roomTitle("테스트 방3")
                        .roomStatus(0)
                        .roomDt(LocalDateTime.now())
                        .build();
        when(repository.save(any())).thenReturn(testRoom);
        Quiz testQuiz = Quiz.builder().quizNo(1L).build();
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
        Long registeredRoomNo =
                service.addRoom(
                        RoomDTO.builder()
                                .roomNo(10L)
                                .quizNo(testQuiz.getQuizNo())
                                .codeShare(1)
                                .roomTitle("테스트 방3")
                                .roomStatus(0)
                                .build());

        assertThat(registeredRoomNo).isEqualTo(10L);
    }

    @Test
    @DisplayName("대기방 수정")
    void updateRoom() {
        when(repository.findById(1L)).thenReturn(Optional.of(room));

        service.modifyRoomStatusByRoomNo(1L);

        verify(repository, times(1)).updateRoom(1L);
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

        verify(repository, times(1)).deleteById(1L);
    }
}
