package com.todayyum.auth.filter;

import com.todayyum.member.domain.Role;
import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.auth.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("token null");
            filterChain.doFilter(request, response);

            return;
        }
        log.info("authorization now");

        String accessToken = authorization.split(" ")[1];

        if (jwtUtil.isExpired(accessToken)) {

            log.info("aceessToken expired");

            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtUtil.getEmail(accessToken);

        String role = jwtUtil.getRole(accessToken);

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .email(email)
                .role(Role.valueOf(role))
                .password("temppassword")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
                null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
