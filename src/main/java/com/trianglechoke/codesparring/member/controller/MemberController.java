package com.trianglechoke.codesparring.member.controller;

import com.trianglechoke.codesparring.member.dto.MemberDTO;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/member/new")
    public String memberForm() {    // 회원가입으로..
        return "member/memberForm";
    }

//    @PostMapping("/member/new")
//    public String insertMemeber(MemberDTO memberDTO, Model model) {
//        Member member = Member.createMember(MemberDTO memberDTO, passwordEncoder)
//        Member m = memberService.saveMember(member);
//        return "";
//    }


}
