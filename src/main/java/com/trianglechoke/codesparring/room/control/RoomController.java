package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.room.dto.RoomDTO;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    @GetMapping
    public RoomDTO find() {

        RoomDTO room = new RoomDTO();
        room.setRoomPwd("0000");
        room.setRoomTitle("테스트 방");
        return room;
    }

    @PostMapping
    public void add(@RequestBody RoomDTO room) {
        throw new MyException(ErrorCode.ALREADY_STARTED_ROOM);
    }
}
