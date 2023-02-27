package com.toy.fdiary.error.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode {
    DiaryAlreadyExist(HttpStatus.BAD_REQUEST,"이미 해당 시간대의 다이어리가 존재합니다."),
    DiaryMealTimeNotMatched(HttpStatus.BAD_REQUEST, "식사 시간대 정보가 올바르지 않습니다.");


    private final HttpStatus statusCode;
    private final String errorMessage;
}
