package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired private RoomService roomService;

    @GetMapping("/{roomNo}")
    public RoomDTO find(@PathVariable Long roomNo) {
        return roomService.findRoomByRoomNo(roomNo);
    }

    @GetMapping
    public List<RoomDTO> findAll(
            @RequestParam(name = "status", required = false) Integer status, Pageable pageable) {
        return roomService.findRoomList(status, pageable);
    }

    @PostMapping
    public Long add(@RequestBody RoomDTO roomDTO) {
        return roomService.addRoom(roomDTO);
    }

    @PutMapping("/{roomNo}")
    public void modify(@PathVariable Long roomNo) {
        roomService.modifyRoomStatusByRoomNo(roomNo);
    }

    @DeleteMapping("/{roomNo}")
    public void remove(@PathVariable Long roomNo) {
        roomService.removeRoomByRoomNo(roomNo);
    }
}
