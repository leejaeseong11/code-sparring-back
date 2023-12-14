package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.room.dto.RoomDTO;
import com.trianglechoke.codesparring.room.service.RoomService;

import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired private RoomService service;

    @GetMapping("/{roomNo}")
    public RoomDTO find(@PathVariable Long roomNo) {
        return service.findRoomByRoomNo(roomNo);
    }

    @GetMapping
    public Page<RoomDTO> findAll(@PathParam("searchNo") Long searchNo, Pageable pageable) {
        return service.findRoomList(searchNo, pageable);
    }

    @PostMapping
    public Long add(@RequestBody RoomDTO roomDTO) {
        return service.addRoom(roomDTO);
    }

    @PutMapping("/{roomNo}")
    public void modify(@PathVariable Long roomNo) {
        service.modifyRoomStatusByRoomNo(roomNo);
    }

    @DeleteMapping("/{roomNo}")
    public void remove(@PathVariable Long roomNo) {
        service.removeRoomByRoomNo(roomNo);
    }
}
