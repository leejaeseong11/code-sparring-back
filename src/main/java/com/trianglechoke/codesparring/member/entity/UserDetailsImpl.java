package com.trianglechoke.codesparring.member.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long memberNo;
    private final String memberPwd;
    private final String memberName;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return memberPwd;
    }

    @Override
    public String getUsername() {
        return String.valueOf(memberNo);
    }

    public String getMemberName() {
        return memberName;
    }

    // 계정의 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정의 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 자격증명 만료여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
