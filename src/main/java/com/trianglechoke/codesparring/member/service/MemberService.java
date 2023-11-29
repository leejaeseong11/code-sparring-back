package com.trianglechoke.codesparring.member.service;

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
                .memberProfileImg(memberDTO.getMemberProfileImg() != null ? memberDTO.getMemberProfileImg() : 0)
                .memberLevel(memberDTO.getMemberLevel() != null ? memberDTO.getMemberLevel() : 1L)
                .memberExp(memberDTO.getMemberExp() != null ? memberDTO.getMemberExp() : 0)
                .memberTier(memberDTO.getMemberTier() != null ? memberDTO.getMemberTier() : "BRONZE")
                .tierPoint(memberDTO.getTierPoint() != null ? memberDTO.getTierPoint() : 0L)
                .winCnt(memberDTO.getWinCnt() != null ? memberDTO.getWinCnt() : 0L)
                .loseCnt(memberDTO.getLoseCnt() != null ? memberDTO.getLoseCnt() : 0L)
                .drawCnt(memberDTO.getDrawCnt() != null ? memberDTO.getDrawCnt() : 0L)
                .memberStatus(memberDTO.getMemberStatus() != null ? memberDTO.getMemberStatus() : 1)
                .role(memberDTO.getRole() != null ? memberDTO.getRole() : USER)
                .build();

        return MemberDTO.memberEntityToDTO(memberRepository.save(member));

    }
    // 특정 memberId에 해당하는 사용자의 정보와 권한을 조회
    @Transactional(readOnly = true)
    public MemberDTO getUserWithAuthorities(String memberId) {
        return MemberDTO.memberEntityToDTO(memberRepository.findByMemberId(memberId).orElse(null));
    }

    // 현재 로그인한 사용자의 정보와 권한을 조회
    @Transactional(readOnly = true)
    public MemberDTO getLoginedMemberWithAuthorities() {
        return MemberDTO.memberEntityToDTO(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findByMemberId)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional
    public MemberDTO deleteMember(long memberNo){
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        Member member = optionalMember.get();
        member.setMemberStatus(0);
        memberRepository.save(member);
        return MemberDTO.memberEntityToDTO(member);
    }

    @Transactional
    public MemberDTO updateMember(Long memberNo, MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        Member member = optionalMember.get();
        if(memberDTO.getMemberPwd() != null) {
            String encryptedPassword = passwordEncoder.encode(memberDTO.getMemberPwd());
            member.setMemberPwd(encryptedPassword);
        } else if (memberDTO.getMemberName() != null) {
            member.setMemberName(memberDTO.getMemberName());
        } else if (memberDTO.getMemberInfo() != null) {
            member.setMemberInfo(memberDTO.getMemberInfo());
        }
        memberRepository.save(member);
        return MemberDTO.memberEntityToDTO(member);
    }
}
