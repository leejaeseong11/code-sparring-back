package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dao.RefreshTokenRepository;
import com.trianglechoke.codesparring.member.dto.MemberRequestDTO;
import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
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

import static com.trianglechoke.codesparring.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberResponseDTO signup(MemberRequestDTO memberRequestDTO) {
        if (memberRepository.existsByMemberId(memberRequestDTO.getMemberId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDTO.toMember(passwordEncoder);
        return MemberResponseDTO.of(memberRepository.save(member));
    }
    @Transactional
    public TokenDTO login(MemberRequestDTO memberRequestDTO) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDTO.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDTO.getRefreshToken())
                .build();

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
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴. 로그아웃 된 사용자
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
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
}
