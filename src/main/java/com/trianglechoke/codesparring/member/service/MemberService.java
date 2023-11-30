package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDTO findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDTO findMemberInfoByEmail(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
