package com.toy.fdiary.diary.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryUpdateDto {
    private String content;
    private String foodUrl;
    private String iconUrl;
    private List<Food> foods;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value="다이어리의 음식 정보 저장")
    public static class Food{
        private String name;
        private Double calories;
        private Double weight;
    }
}
