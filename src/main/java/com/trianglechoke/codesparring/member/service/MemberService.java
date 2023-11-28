package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository repository;

    public void modifyPoint(Long memberNo, Integer point) {
        Optional<Member> optM=repository.findById(memberNo);
        Member memberEntity=optM.get();
        memberEntity.modifyPoint(point);
        repository.save(memberEntity);
    }
}
