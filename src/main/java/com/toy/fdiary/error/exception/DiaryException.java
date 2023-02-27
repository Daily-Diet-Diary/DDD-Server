package com.toy.fdiary.error.exception;


import com.toy.fdiary.error.type.AuthErrorCode;
import com.toy.fdiary.error.type.DiaryErrorCode;
import lombok.Getter;

@Getter
public class DiaryException extends RuntimeException{
    private final DiaryErrorCode errorCode;

    public DiaryException(DiaryErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
