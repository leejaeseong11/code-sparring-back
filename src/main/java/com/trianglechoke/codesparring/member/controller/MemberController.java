package com.trianglechoke.codesparring.member.controller;

import static com.trianglechoke.codesparring.exception.ErrorCode.MISMATCH_PASSWORD;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.service.MemberServiceImpl;
import com.trianglechoke.codesparring.member.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/my")
    public ResponseEntity<?> findMemberInfoByCurrentMemberNo() {
        return ResponseEntity.ok(
                memberServiceImpl.findMemberInfoByMemberNo(SecurityUtil.getCurrentMemberNo()));
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<?> findMemberInfoByMemberNo(@PathVariable Long memberNo) {
        return ResponseEntity.ok(memberServiceImpl.findMemberInfoByMemberNo(memberNo));
    }

    @PutMapping("/my")
    public ResponseEntity<?> modifyMemberInfoByMemberNo(@RequestBody MemberDTO memberDTO) {
        memberDTO.setMemberNo(SecurityUtil.getCurrentMemberNo());
        memberServiceImpl.modifyMemberInfo(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/my/withdraw")
    public ResponseEntity<?> removeMember(
            @RequestBody Map<String, String> passwordMap, MemberDTO memberDTO) {
        if (passwordEncoder.matches(
                passwordMap.get("memberPwd"),
                memberServiceImpl.findMemberPwd(SecurityUtil.getCurrentMemberNo(), memberDTO))) {
            memberDTO.setMemberNo(SecurityUtil.getCurrentMemberNo());
            memberServiceImpl.deleteMember(memberDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new MyException(MISMATCH_PASSWORD);
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> rankedMember() {
        return ResponseEntity.ok(memberServiceImpl.rankedMember());
    }
}
