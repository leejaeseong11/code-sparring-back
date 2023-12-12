package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.service.AuthServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    //    @PostMapping("/login")
    //    public ResponseEntity<TokenDTO> login(@RequestBody MemberDTO memberDTO) {
    //        return ResponseEntity.ok(authServiceImpl.login(memberDTO));
    //    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberDTO memberDTO) {
        TokenDTO tokenDTO = authServiceImpl.login(memberDTO);
        // Set the access token in the response body
        // Access Token은 응답 본문(body)에 포함되어 반환됨
        ResponseEntity<TokenDTO> responseEntity = ResponseEntity.ok().body(tokenDTO);

        // Set the refresh token in a cookie
        ResponseCookie cookie = authServiceImpl.putTokenInCookie(tokenDTO);

        // Create a new HttpHeaders object and set the refresh token cookie
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        // Combine the existing headers of responseEntity and the new headers
        HttpHeaders combinedHeaders = new HttpHeaders();
        combinedHeaders.addAll(responseEntity.getHeaders());
        combinedHeaders.addAll(headers);

        // Create a new ResponseEntity with the updated headers
        return new ResponseEntity<>(
                responseEntity.getBody(), combinedHeaders, responseEntity.getStatusCode());
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return ResponseEntity.ok(authServiceImpl.reissue(tokenRequestDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 클라이언트의 Access Token을 무효화하는 로직
        SecurityContextHolder.clearContext();

        // 클라이언트의 Refresh Token을 삭제하는 로직
        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", "")
                        .maxAge(0)
                        .path("/")
                        .sameSite("None")
                        .secure(true)
                        .httpOnly(true)
                        .build();

        response.addHeader("Set-Cookie", cookie.toString());

        // 여기에서는 간단히 로그아웃 성공 시 200 OK 응답을 반환합니다.
        return ResponseEntity.ok().build();
    }
}
