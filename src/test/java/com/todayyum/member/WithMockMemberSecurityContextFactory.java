package com.todayyum.member;

import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.member.domain.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WithMockMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember member) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.name()));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .memberId(UUID.fromString(member.memberId()))
                .email(member.email())
                .password(member.password())
                .role(Role.USER)
                .nickname(member.nickname())
                .build();

//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                member.email(), member.password(), grantedAuthorities);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, member.password(), grantedAuthorities);

        authentication.setDetails(customUserDetails);
        context.setAuthentication(authentication);
        return context;
    }
}
