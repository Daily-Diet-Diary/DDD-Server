package com.toy.fdiary.util;

import com.toy.fdiary.diary.model.type.MealTime;
import com.toy.fdiary.error.exception.DiaryException;
import com.toy.fdiary.error.type.DiaryErrorCode;

public class MealTimeUtil {
    public String getMealTime(MealTime mealTime) {
        switch (mealTime){
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
    public MealTime setMealTime(String mealTime){
        switch (mealTime){
            case "아침":
                return MealTime.Breakfast;
            case "점심":
                return MealTime.Lunch;
            case "저녁":
                return MealTime.Dinner;
            case "새벽":
                return MealTime.Midnight;
        }
        throw new DiaryException(DiaryErrorCode.DiaryMealTimeNotMatched);
    }
}
