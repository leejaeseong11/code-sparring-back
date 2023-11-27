package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
public class CustomMemberDetailsService implements UserDetailsService {
   private final MemberRepository memberRepository;

   public CustomMemberDetailsService(MemberRepository memberRepository) {
      this.memberRepository = memberRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String memberId) {
      return memberRepository.findByMemberId(memberId)
              .map(user -> createUser(memberId, user))
              .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   private org.springframework.security.core.userdetails.User createUser(String username, Member member) {
      if (!member.isActivated()) {
         throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
      }

      List<GrantedAuthority> grantedAuthorities = Collections.singletonList(
              new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
      );

      return new org.springframework.security.core.userdetails.User(
              member.getMemberId(),
              member.getMemberPwd(),
              grantedAuthorities
      );
   }
}
