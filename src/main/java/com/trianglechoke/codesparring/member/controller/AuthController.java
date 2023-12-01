package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberRequestDTO;
import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(
            @RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(authService.signup(memberRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(authService.login(memberRequestDTO));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDTO));
    }
}
