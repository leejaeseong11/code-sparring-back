package com.trianglechoke.codesparring.member.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   //유효한 자격 증명 제공하지 않고 접근하려 할 때 401 UNAUTHORIZED 에러 반환하는 역할 수행
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
   }
}
