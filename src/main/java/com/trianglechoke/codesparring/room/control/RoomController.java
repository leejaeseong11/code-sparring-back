package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.FindException;
import com.trianglechoke.codesparring.room.dto.RoomDTO;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    @GetMapping
    public RoomDTO find() throws FindException {
        //        throw new FindException("한글 테스트");
        RoomDTO room = new RoomDTO();
        room.setRoomPwd("0000");
        room.setRoomTitle("테스트 방");
        return room;
    }

    @PostMapping
    public void add(@RequestBody RoomDTO room) throws AddException {
        //        System.out.println(room);
    }
}
