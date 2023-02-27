package com.toy.fdiary.member.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class OAuthDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "소셜 로그인 Response Body")
    public static class Response {
        private Long id;
        private String nickname;
        private String imgUrl;
        private String refreshToken;
    }
}
