package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    /** 유저정보 상세 조회(회원 번호) */
    MemberDTO findMemberInfoByMemberNo(Long memberNo);

    /** 회원정보 수정 */
    void modifyMemberInfo(MemberDTO memberDTO);

    /** 회원 삭제 */
    void deleteMember(MemberDTO memberDTO);

    /** 회원의 비밀번호 불러오기(검증용) */
    String findMemberPwd(Long memberNo, MemberDTO memberDTO);

    /** 랭킹 5위 목록 조회 */
    List<MemberDTO> rankedMember();

    /** 회원 경험치 추가 */
    void updateMemberExp(Long memberNo, int roomSize);
}
