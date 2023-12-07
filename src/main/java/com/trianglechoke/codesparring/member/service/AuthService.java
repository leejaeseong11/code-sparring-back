package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;

public interface AuthService {
    /* 회원가입 */
    void signup(MemberDTO memberDTO);

    /* 아이디 중복체크 */
    boolean checkDuplicateId(String memberId);

    /* 닉네임 중복체크 */
    boolean checkDuplicateName(String memberName);

    /* 로그인 */
    TokenDTO login(MemberDTO memberDTO);

    /* refreshToken 재발행 */
    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);

}
