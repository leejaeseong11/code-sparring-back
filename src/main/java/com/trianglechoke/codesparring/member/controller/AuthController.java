package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.service.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody MemberDTO memberDTO) {
        authServiceImpl.signup(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
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
