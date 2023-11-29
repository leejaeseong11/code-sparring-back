package com.trianglechoke.codesparring.member.controller;


import com.trianglechoke.codesparring.member.dto.LoginDTO;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.jwt.JwtFilter;
import com.trianglechoke.codesparring.member.jwt.TokenProvider;
import com.trianglechoke.codesparring.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> signup(
            @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.signup(memberDTO));
    }

    //현재 로그인한 사용자의 정보를 반환
    @GetMapping("/myInfo")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> getMyInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getLoginedMemberWithAuthorities());
    }

    // 특정 사용자의 정보를 반환
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO> getUserInfo(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(memberId));
    }

    @PutMapping("/myInfo/{memberNo}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> ModifyMemberInfo(@PathVariable long memberNo,
                                                      @RequestBody MemberDTO memberDTO) {
        memberDTO.setMemberNo(memberNo);
        return ResponseEntity.ok(memberService.updateMember(memberNo, memberDTO));
    }

    @PutMapping("/myInfo/remove/{memberNo}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> removeMember(@PathVariable long memberNo,
                                                  @RequestBody Map<String, String> passwordMap) {
        if (!passwordEncoder.matches(passwordMap.get("password"), memberService.getLoginedMemberWithAuthorities().getMemberPwd())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        MemberDTO updatedMember = memberService.deleteMember(memberNo);
        return ResponseEntity.ok(updatedMember);
    }
}
