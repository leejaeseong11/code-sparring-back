package com.trianglechoke.codesparring.member.service;

import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Authority;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.member.entity.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<Member> optMember = memberRepository.findByMemberId(username);
            if (optMember.isEmpty() || optMember.get().getMemberStatus() == 0) {
                throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
            }
            Member member = optMember.get();
            return new UserDetailsImpl(
                    member.getMemberNo(),
                    member.getMemberPwd(),
                    member.getMemberName(),
                    getAuthorities(member.getAuthority()));
        } catch (Exception e) {
            throw new UsernameNotFoundException(username + "사용자 정보를 가져오는 중에 오류가 발생했습니다.", e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Authority authority) {
        return Collections.singleton(new SimpleGrantedAuthority(authority.toString()));
    }

    // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority(member.getAuthority().toString());
        return new UserDetailsImpl(
                member.getMemberNo(),
                member.getMemberName(),
                member.getMemberPwd(),
                Collections.singleton(grantedAuthority));
    }
}
