package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Authority;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(username)
                .map(member -> new UserDetailsImpl(
                        member.getMemberNo(),
                        member.getMemberPwd(),
                        member.getMemberName(),
                        getAuthorities(member.getAuthority())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Authority authority) {
        return Collections.singleton(new SimpleGrantedAuthority(authority.toString()));
    }

    // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());
        return new UserDetailsImpl(
                member.getMemberNo(),
                member.getMemberName(),
                member.getMemberPwd(),
                Collections.singleton(grantedAuthority)
        );
    }
}
