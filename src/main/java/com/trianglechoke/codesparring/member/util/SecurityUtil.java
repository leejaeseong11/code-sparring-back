package com.trianglechoke.codesparring.member.util;

import com.trianglechoke.codesparring.member.entity.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
    }
    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberNo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return Long.parseLong(principal.getUsername());
    }

    public static String getCurrentMemberName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        return ((UserDetailsImpl) authentication.getPrincipal()).getMemberName();
    }


}