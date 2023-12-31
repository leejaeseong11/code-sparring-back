package com.trianglechoke.codesparring.member.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.*;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dao.RefreshTokenRepository;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.dto.TokenDTO;
import com.trianglechoke.codesparring.member.dto.TokenRequestDTO;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.RefreshToken;
import com.trianglechoke.codesparring.member.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(MemberDTO memberDTO) {
        if (checkDuplicateId(memberDTO.getMemberId())) {
            throw new MyException(DUPLICATE_ID);
        }
        if (checkDuplicateName(memberDTO.getMemberName())) {
            throw new MyException(DUPLICATE_NAME);
        }
        Member member = memberDTO.toMember(passwordEncoder);
        memberRepository.save(member);
    }

    @Transactional
    public boolean checkDuplicateId(String memberId) {
        Optional<Member> existingMember = memberRepository.findByMemberId(memberId);
        // 이미 존재하면서 상태가 활성화된 경우에만 중복으로 처리
        if (existingMember.isPresent() && existingMember.get().getMemberStatus() == 1) {
            return true;
        }
        return false; // 중복된 아이디 아님
    }

    @Transactional
    public boolean checkDuplicateName(String memberName) {
        Optional<Member> existingMember = memberRepository.findByMemberName(memberName);
        // 이미 존재하면서 상태가 활성화된 경우에만 중복으로 처리
        if (existingMember.isPresent() && existingMember.get().getMemberStatus() == 1) {
            return true;
        }
        return false; // 중복된 아이디 아님
    }

    @Transactional
    public TokenDTO login(MemberDTO memberDTO) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberDTO.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken =
                RefreshToken.builder()
                        .key(authentication.getName())
                        .value(tokenDTO.getRefreshToken())
                        .build();
        System.out.println(
                "--------------------------------authentication.getName()::::::"
                        + authentication.getName());
        refreshTokenRepository.save(refreshToken);
        // 5. 토큰 발급
        return tokenDTO;
    }

    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDTO) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDTO.getRefreshToken())) {
            throw new MyException(UNAVAILABLE_REFRESH_TOKEN);
        }
        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication =
                tokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴. 로그아웃 된 사용자
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByKey(authentication.getName())
                        .orElseThrow(() -> new MyException(UNAUTHORIZED_ACCESS));
        // 4. Refresh Token 일치하는지 검사. 유저정보 일치여부
        if (!refreshToken.getValue().equals(tokenRequestDTO.getRefreshToken())) {
            throw new MyException(TOKEN_MISMATCH);
        }
        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDTO(authentication);
        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        // 토큰 발급
        return tokenDto;
    }

    public String findRefreshTokenByMemberNo(Long refreshTokenKey) {
        Optional<RefreshToken> refreshToken =
                refreshTokenRepository.findByKey(String.valueOf(refreshTokenKey));
        return refreshToken.get().getValue();
    }
}
