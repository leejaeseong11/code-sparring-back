package com.trianglechoke.codesparring.member.service;

import static com.trianglechoke.codesparring.exception.ErrorCode.*;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.entity.Member;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authServiceImpl;

    public MemberDTO findMemberInfoByMemberNo(Long memberNo) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        if (optionalMember.isEmpty()) {
            throw new MyException(MEMBER_NOT_FOUND);
        }
        Member member = optionalMember.get();
        MemberDTO memberDTO =
                MemberDTO.builder()
                        .memberNo(member.getMemberNo())
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .memberInfo(member.getMemberInfo())
                        .memberProfileImg(member.getMemberProfileImg())
                        .memberLevel(member.getMemberLevel())
                        .memberExp(member.getMemberExp())
                        .memberTier(member.getMemberTier())
                        .tierPoint(member.getTierPoint())
                        .winCnt(member.getWinCnt())
                        .loseCnt(member.getLoseCnt())
                        .drawCnt(member.getDrawCnt())
                        .authority(member.getAuthority())
                        .build();
        return memberDTO;
    }

    @Transactional
    public void modifyMemberInfo(MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberDTO.getMemberNo());
        if (optionalMember.isEmpty()) {
            throw new MyException(MEMBER_NOT_FOUND);
        }
        Member member = optionalMember.get();

        if (memberDTO.getMemberPwd() != null) {
            String encryptedPassword = passwordEncoder.encode(memberDTO.getMemberPwd());
            member.modifyMemberPwd(encryptedPassword);
        }

        if (memberDTO.getMemberName() != null) {
            if (authServiceImpl.checkDuplicateName(memberDTO.getMemberName())) {
                throw new MyException(DUPLICATE_NAME);
            }
            member.modifyMemberName(memberDTO.getMemberName());
        }

        if (memberDTO.getMemberInfo() != null) {
            member.modifyMemberInfo(memberDTO.getMemberInfo());
        }

        if (memberDTO.getMemberProfileImg() != null) {
            member.modifyMemberProfileImg(memberDTO.getMemberProfileImg());
        }
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberDTO.getMemberNo());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.removeMember(0);
            memberRepository.save(member);
        } else {
            throw new MyException(FAIL_WITHDRAW);
        }
    }

    public String findMemberPwd(Long memberNo, MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        return optionalMember.get().getMemberPwd();
    }

    /* 랭킹 10위 목록 조회*/
    public List<MemberDTO> rankedMember() {
        List<MemberDTO> memberResponseDTOList = new ArrayList<>();
        List<Object[]> rankedList = memberRepository.findRankedMember();
        if (rankedList.isEmpty()) {
            throw new MyException(MEMBER_RANK_NOT_FOUND);
        }
        for (Object[] objArr : rankedList) {
            MemberDTO dto =
                    MemberDTO.builder()
                            .memberNo((Long) objArr[0])
                            .memberName((String) objArr[1])
                            .memberTier((String) objArr[2])
                            .rank(((Number) objArr[3]).intValue())
                            .build();
            memberResponseDTOList.add(dto);
        }
        return memberResponseDTOList;
    }

    @Transactional
    @Override
    public void updateMemberExp(Long memberNo, int roomSize) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);
        if (optionalMember.isPresent()) {
            memberRepository.updateMemberExp(memberNo, roomSize);
        } else {
            throw new MyException(MEMBER_NOT_FOUND);
        }
    }
}
