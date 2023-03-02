package com.toy.fdiary.diary.service;

import com.toy.fdiary.diary.model.dto.DiaryReadDto.Response;
import com.toy.fdiary.diary.model.dto.DiarySaveDto;
import com.toy.fdiary.diary.model.dto.DiaryUpdateDto;
import com.toy.fdiary.diary.model.entity.Diary;
import com.toy.fdiary.diary.model.entity.Food;
import com.toy.fdiary.diary.model.type.MealTime;
import com.toy.fdiary.diary.repository.DiaryRepository;
import com.toy.fdiary.diary.repository.FoodRepository;
import com.toy.fdiary.error.exception.DiaryException;
import com.toy.fdiary.error.type.DiaryErrorCode;
import com.toy.fdiary.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository repository;
    private final FoodRepository foodRepository;


    public List<Response> read(String date, Member member) {
        List<Diary> diaries = repository.findAllByWriteDateAndMember(Date.valueOf(date),member);
        List<Response> result = new ArrayList<>();
        for (Diary diary : diaries) {
            Response dto = Diary.toResponse(diary);
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
                .mealTime(time)
                .build();
        List<Food> foods = new ArrayList<>();
        if(request.getFoods().size() >0){
            foods = request.getFoods().stream().map(food -> Food.builder()
                    .diary(diary).calories(food.getCalories()).name(food.getName())
                    .weight(food.getWeight()).build()).collect(Collectors.toList());
            diary.getFoods().addAll(foods);

        }
        repository.save(diary);
        if(foods.size()>1) {
            foodRepository.saveAll(foods);
        }
        return successMessage("다이어리 등록 성공");
    }

    public Map<String, String> successMessage(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        return result;
    }

    public Response selectOneRead(String date, String mealTime, Member member) {
        Optional<Diary> optional= repository.findByMemberAndWriteDateAndMealTime(
                member,Date.valueOf(date),MealTime.valueOf(mealTime));
        if(optional.isEmpty()){
           throw new DiaryException(DiaryErrorCode.DiaryNotFound);
        }
        Diary diary = optional.get();
        return Diary.toResponse(diary);
    }
    //특정 다이어리 삭제 메소드
    public Map<String, String> delete(String date, String mealTime, Member member) {
        Optional<Diary> optional = repository.findByMemberAndWriteDateAndMealTime(member,Date.valueOf(date),MealTime.valueOf(mealTime));
        if(optional.isEmpty()){
            throw new DiaryException(DiaryErrorCode.DiaryNotFound);
        }
        Diary diary = optional.get();
        repository.delete(diary);
        return successMessage("삭제에 성공했습니다.");
    }

    public Map<String, String> update(DiaryUpdateDto dto, String date, String mealTime, Member member) {
        Optional<Diary> optional = repository.findByMemberAndWriteDateAndMealTime(member,
                Date.valueOf(date),MealTime.valueOf(mealTime));
        if(optional.isEmpty()){
            throw new DiaryException(DiaryErrorCode.DiaryNotFound);
        }
        Diary diary = optional.get();
        if(!diary.getContent().equals(dto.getContent())){
            diary.setContent(dto.getContent());
        }
        if(!diary.getIconUrl().equals(dto.getIconUrl())){
            diary.setIconUrl(dto.getIconUrl());
        }
        if(!diary.getFoodUrl().equals(dto.getFoodUrl())){
            diary.setFoodUrl(dto.getFoodUrl());
            foodRepository.deleteAllByDiary(diary);
            List<Food> foods = dto.getFoods().stream().map(food -> Food.builder()
                    .diary(diary).calories(food.getCalories()).name(food.getName())
                    .weight(food.getWeight()).build()).collect(Collectors.toList());
            diary.setFoods(foods);
            foodRepository.saveAll(foods);
            repository.save(diary);
        }
        return successMessage("성공");
    }
}
