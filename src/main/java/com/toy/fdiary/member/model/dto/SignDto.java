package com.toy.fdiary.member.model.dto;

import lombok.*;

public class SignDto {
    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String email;
        private String name;
        private String nickName;
        private String image;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String name;
        private String nickName;
        private String image;
    }
}
