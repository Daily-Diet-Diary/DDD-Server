package com.toy.fdiary.diary.model.entity;

import com.toy.fdiary.diary.model.type.MealTime;
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
}
