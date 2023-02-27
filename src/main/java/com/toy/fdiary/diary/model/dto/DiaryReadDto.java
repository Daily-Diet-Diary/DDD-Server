package com.toy.fdiary.diary.model.dto;

import com.toy.fdiary.diary.model.entity.Food;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryReadDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value="특정 요일 다이어리 가져오기")
    public static class Response{
        private Date writeDate;
        private String foodUrl;
        private String content;
        private String iconUrl;
        private String mealTime;
        private List<Food> foods = new ArrayList<>();

    }

}
