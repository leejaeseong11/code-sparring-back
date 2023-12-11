package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.service.AuthServiceImpl;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
