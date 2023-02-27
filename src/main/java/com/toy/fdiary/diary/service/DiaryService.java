package com.toy.fdiary.diary.service;

import com.toy.fdiary.diary.model.dto.DiaryReadDto.Response;
import com.toy.fdiary.diary.model.dto.DiarySaveDto;
import com.toy.fdiary.diary.model.entity.Diary;
import com.toy.fdiary.diary.model.entity.Food;
import com.toy.fdiary.diary.model.type.MealTime;
import com.toy.fdiary.diary.repository.DiaryRepository;
import com.toy.fdiary.error.exception.DiaryException;
import com.toy.fdiary.error.type.DiaryErrorCode;
import com.toy.fdiary.member.model.entity.Member;
import com.toy.fdiary.util.MealTimeUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class DiaryService {
    private DiaryRepository repository;
    private MealTimeUtil util;


    public List<Response> read(String date, Member member) {
        List<Diary> diaries = repository.findAllByWriteDateAndMember(Date.valueOf(date),member);
        List<Response> result = new ArrayList<>();
        for (int i = 0; i < diaries.size(); i++) {
            Diary diary = diaries.get(i);
            Response dto = Response.builder()
                    .writeDate(diary.getWriteDate())
                    .foodUrl(diary.getFoodUrl())
                    .content(diary.getContent())
                    .iconUrl(diary.getIconUrl())
                    .mealTime(util.getMealTime(diary.getMealTime()))
                    .foods(diary.getFoods())
                    .build();
            result.add(dto);
        }
        return result;
    }

    public Map<String, String> save(DiarySaveDto.Request request, String date, String mealTime, Member member) {
        Date writeDate = Date.valueOf(date);
        MealTime time = MealTime.valueOf(mealTime);
        Optional<Diary> optional = repository.findByMemberAndWriteDateAndMealTime(member,
                writeDate, time);
        if(optional.isPresent()){
            throw new DiaryException(DiaryErrorCode.DiaryAlreadyExist);
        }
        Diary diary = Diary.builder()
                .member(member)
                .writeDate(writeDate)
                .foodUrl(request.getFoodImgUrl())
                .content(request.getContent())
                .iconUrl(request.getIconUrl())
                .mealTime(util.setMealTime(request.getMealTime()))
                .foods(makeFoodEntity(request.getFoods()))
                .build();
        repository.save(diary);
        return successMessage("다이어리 등록 성공");
    }

    public Map<String, String> successMessage(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        return result;
    }
    public List<Food> makeFoodEntity(List<DiarySaveDto.Food> foods){
        List<Food> result = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            DiarySaveDto.Food request = foods.get(i);
            Food food = new Food(request.getName(),request.getCalories(),request.getWeight());
            result.add(food);
        }
        return result;
    }
}
