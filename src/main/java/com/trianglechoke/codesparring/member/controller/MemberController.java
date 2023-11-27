package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> signup(
            @Valid @RequestBody MemberDTO memberDTO
    ) {
        return ResponseEntity.ok(memberService.signup(memberDTO));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO> getUserInfo(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(memberId));
    }
}
