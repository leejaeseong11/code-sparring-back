package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
import com.trianglechoke.codesparring.member.service.MemberService;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> findMemberInfoById() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberNo()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDTO> findMemberInfoByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
    }
}
