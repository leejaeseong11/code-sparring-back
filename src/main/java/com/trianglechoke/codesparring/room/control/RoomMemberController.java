package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;
import com.trianglechoke.codesparring.room.service.RoomMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room-member")
public class RoomMemberController {
    @Autowired private RoomMemberService service;

    @GetMapping("/{memberNo}")
    public boolean findMember(@PathVariable Long memberNo) {
        return service.isMemberInRoom(memberNo);
    }

    @PostMapping
    public Long add(
            @RequestParam(name = "roomPwd", required = false) String roomPwd,
            @RequestBody RoomMemberDTO roomMember) {
        return service.addRoomMember(roomPwd, roomMember);
    }

    @DeleteMapping("/{memberNo}")
    public void remove(@PathVariable Long memberNo) {
        service.removeMember(memberNo);
    }

    @GetMapping("/isHost/{memberNo}")
    public Boolean isHost(@PathVariable Long memberNo) {
        return service.isRoomMemberHost(memberNo);
    }
}
