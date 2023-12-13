package com.trianglechoke.codesparring.room.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.QUIZ_NOT_FOUND;
import static com.trianglechoke.codesparring.exception.ErrorCode.ROOM_NOT_FOUND;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired private RoomRepository repository;
    @Autowired private QuizRepository quizRepository;

    @Transactional
    public RoomDTO findRoomByRoomNo(Long roomNo) {
        Optional<Room> room = repository.findById(roomNo);

        if (room.isPresent()) {
            Room selectedRoom = room.get();
            Quiz selectedQuiz = selectedRoom.getQuiz();
            List<RoomMemberDTO> inputRoomMemberList = new ArrayList<>();
            selectedRoom
                    .getRoomMemberList()
                    .forEach(
                            roomMember -> {
                                Member inputMember = roomMember.getId().getMember();
                                inputRoomMemberList.add(
                                        RoomMemberDTO.builder()
                                                .memberNo(inputMember.getMemberNo())
                                                .memberName(inputMember.getMemberName())
                                                .memberProfileImg(inputMember.getMemberProfileImg())
                                                .memberLevel(inputMember.getMemberLevel())
                                                .memberTier(inputMember.getMemberTier())
                                                .hostStatus(roomMember.getHostStatus())
                                                .build());
                            });
            return RoomDTO.builder()
                    .roomNo(selectedRoom.getRoomNo())
                    .quizNo(selectedQuiz.getQuizNo())
                    .quizTitle(selectedQuiz.getQuizTitle())
                    .quizContent(selectedQuiz.getQuizContent())
                    .memberName(selectedQuiz.getMember().getMemberName())
                    .roomPwd(selectedRoom.getRoomPwd())
                    .codeShare(selectedRoom.getCodeShare())
                    .roomTitle(selectedRoom.getRoomTitle())
                    .roomStatus(selectedRoom.getRoomStatus())
                    .roomMemberList(inputRoomMemberList)
                    .build();
        } else {
            throw new MyException(ROOM_NOT_FOUND);
        }
    }

    @Transactional
    public Page<RoomDTO> findRoomList(Pageable pageable) {
        List<RoomDTO> selectedRoomList = new ArrayList<>();
        Page<Room> roomList = repository.findByOrderByRoomStatusDescRoomDtDesc(pageable);

        for (Room room : roomList) {
            Quiz selectedQuiz = room.getQuiz();
            List<RoomMemberDTO> inputRoomMemberList = new ArrayList<>();
            room.getRoomMemberList()
                    .forEach(
                            roomMember -> {
                                Member inputMember = roomMember.getId().getMember();
                                inputRoomMemberList.add(
                                        RoomMemberDTO.builder()
                                                .memberNo(inputMember.getMemberNo())
                                                .memberName(inputMember.getMemberName())
                                                .memberProfileImg(inputMember.getMemberProfileImg())
                                                .memberLevel(inputMember.getMemberLevel())
                                                .memberTier(inputMember.getMemberTier())
                                                .hostStatus(roomMember.getHostStatus())
                                                .build());
                            });
            selectedRoomList.add(
                    RoomDTO.builder()
                            .roomNo(room.getRoomNo())
                            .quizNo(selectedQuiz.getQuizNo())
                            .quizTitle(selectedQuiz.getQuizTitle())
                            .quizContent(selectedQuiz.getQuizContent())
                            .memberName(selectedQuiz.getMember().getMemberName())
                            .roomPwd(room.getRoomPwd())
                            .codeShare(room.getCodeShare())
                            .roomTitle(room.getRoomTitle())
                            .roomStatus(room.getRoomStatus())
                            .roomDt(room.getRoomDt())
                            .roomMemberList(inputRoomMemberList)
                            .build());
        }
        return new PageImpl<>(selectedRoomList, pageable, roomList.getTotalElements());
    }

    @Transactional
    public Long addRoom(RoomDTO roomDTO) {
        Optional<Quiz> selectedQuiz = quizRepository.findById(roomDTO.getQuizNo());
        if (selectedQuiz.isEmpty()) {
            throw new MyException(QUIZ_NOT_FOUND);
        }
        return repository
                .save(
                        Room.builder()
                                .quiz(selectedQuiz.get())
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
