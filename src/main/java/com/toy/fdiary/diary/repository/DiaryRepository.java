package com.toy.fdiary.diary.repository;

import com.toy.fdiary.diary.model.entity.Diary;
import com.toy.fdiary.diary.model.type.MealTime;
import com.toy.fdiary.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long> {
    List<Diary> findAllByWriteDateAndMember(Date writeDate, Member member);
    Optional<Diary> findByMemberAndWriteDateAndMealTime(Member member, Date writeDate, MealTime mealTime);
}
