package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberRequestDTO;
import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
import com.trianglechoke.codesparring.member.service.MemberService;
import com.trianglechoke.codesparring.member.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/my")
    public ResponseEntity<MemberResponseDTO> findMemberInfoByMemberNo() {
        return ResponseEntity.ok(
                memberService.findMemberInfoByMemberNo(SecurityUtil.getCurrentMemberNo()));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> findMemberInfoByMemberId(
            @PathVariable String memberId) {
        return ResponseEntity.ok(memberService.findMemberInfoByMemberId(memberId));
    }

    @PutMapping("/my/modify")
    public ResponseEntity<MemberResponseDTO> modifyMemberInfoByMemberNo(
            @RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(
                memberService.updateMemberInfo(
                        SecurityUtil.getCurrentMemberNo(), memberRequestDTO));
    }

    @PutMapping("/my/remove")
    public ResponseEntity<MemberResponseDTO> removeMember(
            @RequestBody Map<String, String> passwordMap, MemberRequestDTO memberRequestDTO) {
        if (passwordEncoder.matches(
                passwordMap.get("memberPwd"),
                memberService.findMemberPwd(SecurityUtil.getCurrentMemberNo(), memberRequestDTO))) {
            return ResponseEntity.ok(memberService.deleteMember(SecurityUtil.getCurrentMemberNo()));
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
