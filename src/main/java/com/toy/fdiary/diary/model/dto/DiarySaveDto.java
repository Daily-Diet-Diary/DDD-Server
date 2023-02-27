package com.toy.fdiary.diary.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DiarySaveDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "다이어리 저장 Request")
    public static class Request{
        private String iconUrl;
        private String date;
        private String mealTime;
        private String content;
        private String foodImgUrl;
        private List<Food> foods;
    }

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
