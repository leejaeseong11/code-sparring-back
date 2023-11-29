package com.trianglechoke.codesparring.room.control;

import com.trianglechoke.codesparring.room.service.RoomMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room-member")
public class RoomMemberController {
    @Autowired private RoomMemberService service;

    @GetMapping
    public boolean findMember(Long memberNo) {
        service.isMemberInRoom(1L);
        return false;
    }
}
