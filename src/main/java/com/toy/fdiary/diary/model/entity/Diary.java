package com.toy.fdiary.diary.model.entity;

import com.toy.fdiary.diary.model.dto.DiaryReadDto;
import com.toy.fdiary.diary.model.type.MealTime;
import com.toy.fdiary.error.exception.DiaryException;
import com.toy.fdiary.error.type.DiaryErrorCode;
import com.toy.fdiary.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="diary")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue()
    private Long diaryId;
    private Date writeDate;
    private String foodUrl;
    private String content;
    private String iconUrl;

    @Enumerated(EnumType.STRING)
    private MealTime mealTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

    public static DiaryReadDto.Response toResponse(Diary diary) {
        return DiaryReadDto.Response.builder()
                .writeDate(diary.getWriteDate())
                .foodUrl(diary.getFoodUrl())
                .content(diary.getContent())
                .iconUrl(diary.getIconUrl())
                .mealTime(diary.getMealTime())
                .foods(diary.getFoods())
                .build();
    }
    public String getMealTime() {
        switch (this.mealTime){
            case Breakfast:
                return "아침";
            case Lunch:
                return "점심";
            case Dinner:
                return "저녁";
            case Midnight:
                return "야식";
        }
        throw new DiaryException(DiaryErrorCode.DiaryMealTimeNotMatched);
    }
}
