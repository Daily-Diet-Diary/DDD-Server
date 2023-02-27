package com.toy.fdiary.diary.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="food")
@Getter @Setter
public class Food {
    @Id
    @GeneratedValue
    private Long foodId;

    private String name;
    private Double calories;
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="diaryId")
    private Diary diary;

    public Food(String name, Double calories, Double weight) {
        this.name = name;
        this.calories = calories;
        this.weight = weight;
    }

    public Food() {

    }
}
