package com.todayyum.member;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockMemberSecurityContextFactory.class)
public @interface WithMockMember {
    String password() default "testtest";
    String email() default "test@test.com";
    String memberId() default "c3f1843f-1304-4526-ba5b-ffedc85f0d55";
    String nickname() default "test";
}
