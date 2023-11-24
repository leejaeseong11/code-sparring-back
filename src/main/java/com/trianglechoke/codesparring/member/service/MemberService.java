package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // Autowired 안써도 됨. test에선 안먹힘. final이 안되서
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicationMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicationMember(Member member) {
        Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
        if(findMember.isPresent() && findMember.get().getMemberStatus() == 1) {
            System.out.println(findMember.get().getMemberName());
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
