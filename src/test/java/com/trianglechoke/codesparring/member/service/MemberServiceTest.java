//package com.trianglechoke.codesparring.member.service;
//
//import com.trianglechoke.codesparring.member.dto.MemberDTO;
//import com.trianglechoke.codesparring.member.entity.Member;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private MemberService memberService;
//    public Member createMember(){
//        MemberDTO dto = MemberDTO.builder()
//                .memberInfo("반갑습니다")
//                .memberId("test1")
//                .memberPwd("1111")
//                .memberName("홍길동")
//                .build();
//        Member member = Member.memberDTOToEntity(dto, passwordEncoder);
//        return member;
//    }
//
//    @Test
//    void saveMemberTest() {
//        Member member = createMember();
//        System.out.println(member);
////        Member member1 = memberService.saveMember(member);
////        System.out.println(member1);
//    }
//
//    @Test
//    @DisplayName("중복 회원 에외 발생 테스트")
//    void saveMemberTest2() {
//        Member member1 = createMember();
//        Member member2 = createMember();
////        System.out.println(memberService.saveMember(member1));
////        System.out.println(memberService.saveMember(member2));
//    }
//
//    @Test
//    @DisplayName("중복 회원 에외 발생 테스트2")
//    void saveMemberTest3() {
//        Member member1 = createMember();
//        Member member2 = createMember();
////        memberService.saveMember(member1);
////        Throwable e = Assertions.assertThrows(IllegalStateException.class, () ->{
////            memberService.saveMember(member2);
////        });
////        Assertions.assertEquals("이미 존재하는 회원입니다.", e.getMessage()); // 기대값, 실제값
//    }
//}