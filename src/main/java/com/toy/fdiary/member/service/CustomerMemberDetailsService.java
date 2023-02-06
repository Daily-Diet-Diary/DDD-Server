package com.toy.fdiary.member.service;

import com.toy.fdiary.error.exception.AuthException;
import com.toy.fdiary.error.type.AuthErrorCode;
import com.toy.fdiary.member.model.entity.Member;
import com.toy.fdiary.member.repository.MemberRepository;
import com.toy.fdiary.security.auth.PrincipalDetail;
import com.toy.fdiary.security.auth.redis.CacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Cacheable(value = CacheKey.USER, key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new AuthException(AuthErrorCode.MemberNotFound));
        return PrincipalDetail.of(member);
    }
}