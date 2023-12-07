package com.trianglechoke.codesparring.room.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.ROOM_NOT_FOUND;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired private RoomRepository repository;

    @Transactional
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

    @Transactional
    public List<RoomDTO> findRoomList(Integer status, Pageable pageable) {
        List<RoomDTO> selectedRoomList = new ArrayList<>();
        Page<Room> roomList;
        if (status == null) {
            roomList = repository.findAll(pageable);
        } else {
            roomList = repository.findAllByRoomStatus(status, pageable);
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

    @Transactional
    public Long addRoom(RoomDTO roomDTO) {
        return repository
                .save(
                        Room.builder()
                                .quiz(roomDTO.getQuiz())
                                .roomPwd(roomDTO.getRoomPwd())
                                .codeShare(roomDTO.getCodeShare())
                                .roomTitle(roomDTO.getRoomTitle())
                                .roomDt(roomDTO.getRoomDt())
                                .roomStatus(1)
                                .build())
                .getRoomNo();
    }

    @Transactional
    public void modifyRoomStatusByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);
        if (room.isPresent()) {
            repository.updateRoom(roomNo);
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }

    @Transactional
    public void removeRoomByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            repository.deleteById(roomNo);
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }
}
