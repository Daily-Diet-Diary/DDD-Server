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
    @ApiModel(value="���̾�� ���� ���� ����")
    public static class Food{
        private String name;
        private Double calories;
        private Double weight;
    }
}
