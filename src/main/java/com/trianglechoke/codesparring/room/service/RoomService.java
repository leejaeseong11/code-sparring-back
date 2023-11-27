package com.trianglechoke.codesparring.room.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.ROOM_NOT_FOUND;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired private RoomRepository repository;

    /* 대기방 생성 */
    public void addRoom(RoomDTO roomDTO) {
        repository.save(
                Room.builder()
                        .roomNo(roomDTO.getRoomNo())
                        .quiz(Quiz.builder().quizNo(roomDTO.getQuiz().getQuizNo()).build())
                        .roomPwd(roomDTO.getRoomPwd())
                        .codeShare(roomDTO.getCodeShare())
                        .roomTitle(roomDTO.getRoomTitle())
                        .build());
    }

    /* 대기방 삭제 */
    public void removeRoomByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            repository.deleteById(roomNo);
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }

    /* 대기방 수정 - 게임방으로 변경 */
    public void modifyRoomStatusByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            repository.save(
                    Room.builder()
                            .roomNo(room.get().getRoomNo())
                            .quiz(room.get().getQuiz())
                            .roomPwd(room.get().getRoomPwd())
                            .codeShare(room.get().getCodeShare())
                            .roomTitle(room.get().getRoomTitle())
                            .roomStatus(0)
                            .roomMemberList(room.get().getRoomMemberList())
                            .build());
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }

    /* 대기방 상세 조회 */
    public RoomDTO findRoomByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            return RoomDTO.builder()
                    .roomNo(room.get().getRoomNo())
                    .quiz(room.get().getQuiz())
                    .roomPwd(room.get().getRoomPwd())
                    .codeShare(room.get().getCodeShare())
                    .roomTitle(room.get().getRoomTitle())
                    .roomStatus(room.get().getRoomStatus())
                    .roommemberList(room.get().getRoomMemberList())
                    .build();
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }
    /* 대기방 목록 조회 */
    /* 대기방에 회원 추가 */
    /* 대기방 회원 조회 */
    /* 채팅 */
}
