package com.toy.fdiary.diary.repository;

import com.toy.fdiary.diary.model.entity.Diary;
import com.toy.fdiary.diary.model.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    @Transactional
    void deleteAllByDiary(Diary diary);
}
