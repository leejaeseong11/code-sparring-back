package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.service.AuthServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberDTO memberDTO) {
        authServiceImpl.signup(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/chkDupId")
    public ResponseEntity<Boolean> chkDupId(@RequestParam String memberId) {
        authServiceImpl.checkDuplicateId(memberId);
        return ResponseEntity.ok(authServiceImpl.checkDuplicateId(memberId));
    }

    @PostMapping("/chkDupName")
    public ResponseEntity<Boolean> chkDupName(@RequestParam String memberName) {
        authServiceImpl.checkDuplicateName(memberName);
        return ResponseEntity.ok(authServiceImpl.checkDuplicateName(memberName));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(authServiceImpl.login(memberDTO));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return ResponseEntity.ok(authServiceImpl.reissue(tokenRequestDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 클라이언트의 Access Token을 무효화하는 로직
        SecurityContextHolder.clearContext();
        // 여기에서는 간단히 로그아웃 성공 시 200 OK 응답을 반환합니다.
        return ResponseEntity.ok().build();
    }
}
