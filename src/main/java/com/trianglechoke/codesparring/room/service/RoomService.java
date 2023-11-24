package com.trianglechoke.codesparring.room.service;

import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.room.dao.RoomRepository;
import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.entity.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired private RoomRepository repository;

    /* 대기방 생성 */
    public void addRoom(RoomDTO roomDTO) {
        repository.save(
                Room.builder()
                        .roomNo(roomDTO.getRoomNo())
                        .quiz(Quiz.builder().quizNo(roomDTO.getQuizNo()).build())
                        .roomPwd(roomDTO.getRoomPwd())
                        .codeShare(roomDTO.getCodeShare())
                        .roomTitle(roomDTO.getRoomTitle())
                        .build());
    }

    /* 대기방 삭제 */
    public void removeRoom(Long roomNo) {
        repository.deleteById(roomNo);
    }

    /* 대기방 수정 */
    public void modifyRoom() {}

    /* 대기방 상세 조회 */
    /* 대기방 목록 조회 */
    /* 대기방에 회원 추가 */
    /* 대기방 회원 조회 */
    /* 채팅 */
}
