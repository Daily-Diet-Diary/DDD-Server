package com.toy.fdiary.member.service;

import com.toy.fdiary.error.exception.AuthException;
import com.toy.fdiary.member.model.dto.*;
import com.toy.fdiary.member.model.entity.Member;
import com.toy.fdiary.member.repository.MemberRepository;
import com.toy.fdiary.security.auth.jwt.JwtExpirationEnums;
import com.toy.fdiary.security.auth.redis.LogoutAccessToken;
import com.toy.fdiary.security.auth.redis.LogoutAccessTokenRedisRepository;
import com.toy.fdiary.security.auth.redis.RefreshToken;
import com.toy.fdiary.security.auth.redis.RefreshTokenRedisRepository;
import com.toy.fdiary.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.toy.fdiary.error.type.AuthErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional( isolation = Isolation.SERIALIZABLE)
    public SignDto.Response signUser(SignDto.Request signDto) {
        Optional<Member> member = memberRepository.findByEmail(signDto.getEmail());
        if(member.isEmpty()){
            throw new AuthException(MemberNotFound);
        }
        return null;
    }
    @Transactional
    public LoginDto.Response login(String email) {
        Member member = memberRepository.findByEmail(email).
                orElseThrow(() -> new AuthException(MemberNotFound));
        String accessToken = jwtTokenUtil.generateAccessToken(email);
        RefreshToken refreshToken = jwtTokenUtil.saveRefreshToken(email);
        return LoginDto.Response.of(member.getMemberId(), email, member.getNickname(), member.getImgUrl(), accessToken,refreshToken.getRefreshToken());
    }


    public Map<String, String> logout(TokenDto tokenDto, String username) {
        String accessToken = resolveToken(tokenDto.getAccessToken());
        long remainMilliSeconds = jwtTokenUtil.getRemainMilliSeconds(accessToken);
        refreshTokenRedisRepository.deleteById(username);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken, username, remainMilliSeconds));
        return successMessage("로그아웃");
    }


    public TokenDto reissue(String refreshToken) {
        refreshToken = resolveToken(refreshToken);
        String username = getCurrentUsernameInRefreshToken(refreshToken);
        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(username).orElseThrow(
                () -> new AuthException(AccessTokenAlreadyExpired));
        if (refreshToken.equals(redisRefreshToken.getRefreshToken())) {
            return reissueRefreshToken(refreshToken, username);
        }
        throw new AuthException(TokenNotMatch);
    }

    private TokenDto reissueRefreshToken(String refreshToken, String username) {
        if (lessThanReissueExpirationTimesLeft(refreshToken)) {
            String accessToken = jwtTokenUtil.generateAccessToken(username);
            return TokenDto.of(accessToken, jwtTokenUtil.saveRefreshToken(username).getRefreshToken());
        }
        return TokenDto.of(jwtTokenUtil.generateAccessToken(username), refreshToken);
    }

    public InfoDto.Response info(Member member) {
        return InfoDto.Response.builder()
                .id(member.getMemberId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .imgUrl(member.getImgUrl())
                .name(member.getName())
                .build();
    }

    public PatchInfo.Request patchInfo(Member member, PatchInfo.Request request) {
        if (request.getNickname() != null) {
            member.setNickname(request.getNickname());
        }

        if (request.getImgUrl() != null) {
            member.setImgUrl(request.getImgUrl());
        }
        memberRepository.save(member);
        return request;
    }
    public OAuthDto.Response oauthInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(MemberNotFound));

        RefreshToken refreshToken = jwtTokenUtil.saveRefreshToken(email);

        return OAuthDto.Response.builder()
                .id(member.getMemberId())
                .nickname(member.getNickname())
                .imgUrl(member.getImgUrl())
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtTokenUtil.getRemainMilliSeconds(refreshToken) < JwtExpirationEnums.REISSUE_EXPIRATION_TIME.getValue();
    }
    public Map<String, String> successMessage(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        return result;
    }
    public String resolveToken(String token) {
        return token.substring(7);
    }
    private String getCurrentUsernameInRefreshToken(String refreshToken) {
        if (jwtTokenUtil.isTokenExpired(refreshToken)) {
            throw new AuthException(AccessTokenAlreadyExpired);
        }

        return jwtTokenUtil.getUsername(refreshToken);
    }
}
