package com.toy.fdiary.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {
    AccessTokenAlreadyExpired(HttpStatus.FORBIDDEN, "AccessToken이 이미 만료되었습니다."),
    MemberNotFound(HttpStatus.NOT_FOUND, "가입된 유저가 아닙니다."),
    OAuthProviderMissMatch(HttpStatus.BAD_REQUEST, "기존에 회원가입한 소셜 타입과 일치하지 않습니다."),
    TokenNotMatch(HttpStatus.BAD_REQUEST, "토큰이 일치하지 않습니다.");


    private final HttpStatus statusCode;
    private final String errorMessage;
}
