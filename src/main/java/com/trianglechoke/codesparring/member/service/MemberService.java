package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.exception.AddException;
import com.trianglechoke.codesparring.exception.DuplicateMemberException;
import com.trianglechoke.codesparring.exception.NotFoundMemberException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.trianglechoke.codesparring.member.entity.Role.USER;

@Slf4j
@Service
@RequiredArgsConstructor // Autowired 안써도 됨. test에선 안먹힘. final이 안되서
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDTO signup(MemberDTO memberDTO) {
        if (memberRepository.findByMemberId(memberDTO.getMemberId()).isPresent()
        && memberRepository.findByMemberId(memberDTO.getMemberId()).get().getMemberStatus() == 1) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .memberId(memberDTO.getMemberId())
                .memberPwd(passwordEncoder.encode(memberDTO.getMemberPwd()))
                .memberName(memberDTO.getMemberName())
                .memberInfo(memberDTO.getMemberInfo())
                .role(memberDTO.getRole())
                .build();

        return MemberDTO.from(memberRepository.save(member));

    }
    @Transactional(readOnly = true)
    public MemberDTO getUserWithAuthorities(String memberId) {
        return MemberDTO.from(memberRepository.findByMemberId(memberId).orElse(null));
    }

    @Transactional(readOnly = true)
    public MemberDTO getMyUserWithAuthorities() {
        return MemberDTO.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findByMemberId)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
