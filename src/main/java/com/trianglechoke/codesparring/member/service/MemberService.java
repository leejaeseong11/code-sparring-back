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

    /* RankGame - member point modify */
    public void modifyPoint(Long memberNo, Integer point) {
        Optional<Member> optM=repository.findById(memberNo);
        Member memberEntity=optM.get();
        memberEntity.modifyPoint(point);
        String tier=calculateTier(memberEntity.getTierPoint());
        memberEntity.modifyTier(tier);
        repository.save(memberEntity);
    }

    /* RankGame - member cnt modify : winCnt, loseCnt, drawCnt */
    public void modifyCnt(Long memberNo, Integer gameResult) {
        Optional<Member> optM=repository.findById(memberNo);
        Member memberEntity=optM.get();
        memberEntity.modifyCnt(gameResult);
        repository.save(memberEntity);
    }

    /* RankGame - member tier calculate */
    private String calculateTier(Long point) {
        if(point>15000L) return "PLATINUM";
        else if(point>5000L) return "GOLD";
        else if(point>1000L) return "SILVER";
        else return "BRONZE";
    }
}
