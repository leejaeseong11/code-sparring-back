package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.member.util.SecurityUtil;
import com.trianglechoke.codesparring.room.dto.RoomMemberDTO;
import com.trianglechoke.codesparring.room.service.RoomMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room-member")
public class RoomMemberController {
    @Autowired private RoomMemberService service;

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

    @GetMapping
    public Boolean isHost() {
        return service.isRoomMemberHost(SecurityUtil.getCurrentMemberNo());
    }
}
