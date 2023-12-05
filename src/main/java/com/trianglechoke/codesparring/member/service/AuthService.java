package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;

public interface AuthService {
    /* 회원가입 */
    void signup(MemberDTO memberDTO);

    /* 로그인 */
    TokenDTO login(MemberDTO memberDTO);

    /* refreshToken 재발행 */
    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);
}
