package com.toy.fdiary.diary.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="food")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    private String name;
    private Double calories;
    private Double weight;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="diaryId")
    private Diary diary;

}
