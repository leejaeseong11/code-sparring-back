package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dto.MemberRequestDTO;
import com.trianglechoke.codesparring.member.dto.MemberResponseDTO;
import com.trianglechoke.codesparring.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDTO findMemberInfoByMemberNo(Long memberNo) {
        return memberRepository.findById(memberNo)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDTO findMemberInfoByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    // 회원정보 수정
    @Transactional
    public MemberResponseDTO updateMemberInfo(Long memberNo, MemberRequestDTO memberRequestDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        Member member = optionalMember.get();

        if (memberRequestDTO.getMemberPwd() != null) {
            String encryptedPassword = passwordEncoder.encode(memberRequestDTO.getMemberPwd());
            member.setMemberPwd(encryptedPassword);
        }

        if (memberRequestDTO.getMemberName() != null) {
            member.setMemberName(memberRequestDTO.getMemberName());
        }

        if (memberRequestDTO.getMemberInfo() != null) {
            member.setMemberInfo(memberRequestDTO.getMemberInfo());
        }

        memberRepository.save(member);
        return MemberResponseDTO.of(member);

    }

    @Transactional
    public MemberResponseDTO deleteMember(long memberNo){
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        Member member = optionalMember.get();
        member.setMemberStatus(0);
        memberRepository.save(member);
        return MemberResponseDTO.of(member);
    }

    public String findMemberPwd(Long memberNo, MemberRequestDTO memberRequestDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        return optionalMember.get().getMemberPwd();
    }
}

