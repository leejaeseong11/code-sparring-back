package com.trianglechoke.codesparring.room.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.ROOM_NOT_FOUND;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired private RoomRepository repository;

    /* 대기방 상세 조회 */
    public RoomDTO findRoomByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            Room selectedRoom = room.get();
            Quiz selectedQuiz = selectedRoom.getQuiz();
            return RoomDTO.builder()
                    .roomNo(selectedRoom.getRoomNo())
                    .quiz(selectedQuiz)
                    .roomPwd(selectedRoom.getRoomPwd())
                    .codeShare(selectedRoom.getCodeShare())
                    .roomTitle(selectedRoom.getRoomTitle())
                    .roomStatus(selectedRoom.getRoomStatus())
                    .roomMemberList(selectedRoom.getRoomMemberList())
                    .build();
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }

    /* 대기방 목록 조회 */
    public List<RoomDTO> findRoomList(Integer status) {
        List<RoomDTO> selectedRoomList = new ArrayList<>();
        List<Room> roomList;
        if (status == null) {
            roomList = repository.findAll();
        } else {
            roomList = repository.findAllByRoomStatus(status);
        }

        for (Room room : roomList) {
            Quiz selectedQuiz = room.getQuiz();
            selectedRoomList.add(
                    RoomDTO.builder()
                            .roomNo(room.getRoomNo())
                            .quiz(selectedQuiz)
                            .roomPwd(room.getRoomPwd())
                            .codeShare(room.getCodeShare())
                            .roomTitle(room.getRoomTitle())
                            .roomStatus(room.getRoomStatus())
                            .roomMemberList(room.getRoomMemberList())
                            .build());
        }

        return selectedRoomList;
    }

    /* 대기방 생성 */
    public Long addRoom(RoomDTO roomDTO) {
        return repository
                .save(
                        Room.builder()
                                .quiz(roomDTO.getQuiz())
                                .roomPwd(roomDTO.getRoomPwd())
                                .codeShare(roomDTO.getCodeShare())
                                .roomTitle(roomDTO.getRoomTitle())
                                .roomStatus(1)
                                .build())
                .getRoomNo();
    }

    /* 대기방 수정 - 게임방으로 변경 */
    public void modifyRoomStatusByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);
        if (room.isPresent()) {
            repository.updateRoom(roomNo);
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
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
}
