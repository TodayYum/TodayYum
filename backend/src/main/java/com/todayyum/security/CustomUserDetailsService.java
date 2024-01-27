package com.todayyum.security;

import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException("email이 없어요");
        }

        return CustomUserDetails.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }
}
