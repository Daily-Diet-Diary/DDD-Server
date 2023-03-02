package com.toy.fdiary.diary.controller;

import com.toy.fdiary.diary.model.dto.DiaryReadDto.Response;
import com.toy.fdiary.diary.model.dto.DiarySaveDto;
import com.toy.fdiary.diary.model.dto.DiaryUpdateDto;
import com.toy.fdiary.diary.service.DiaryService;
import com.toy.fdiary.member.model.entity.Member;
import com.toy.fdiary.security.auth.PrincipalDetail;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation(value = "일기 조회", notes = "해당 요일의 사용자 일기 읽기")
    @GetMapping("/{date}")
    public ResponseEntity<?> diaryRead(@PathVariable String date,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        List<Response> diaries = diaryService.read(date,member);
        return ResponseEntity.ok(diaries);
    }
    @ApiOperation(value = "특정 일기 조회", notes = "해당하는 특정 일기만 읽기")
    @GetMapping("/{date}/{mealTime}")
    public ResponseEntity<?> selectOneDiary(@PathVariable String date, @PathVariable String mealTime,
                                            @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Response result = diaryService.selectOneRead(date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "읽기 작성", notes = "일기를 등록합니다.")
    @PostMapping("/{date}/{mealTime}/save")
    public ResponseEntity<?> saveDiary(@PathVariable String date,
                                       @PathVariable String mealTime,
                                       @RequestBody DiarySaveDto.Request request,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Map<String, String> result = diaryService.save(request,date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "특정 일기 삭제", notes = "일기 삭제")
    @DeleteMapping("/{date}/{mealTime}/delete")
    public ResponseEntity<?> deleteDiary(@PathVariable String date, @PathVariable String mealTime,
                                         @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Map<String,String> result = diaryService.delete(date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value="특정 일기 수정", notes="특정 일기의 내용 및 사진을 수정한다.")
    @PostMapping("/{date}/{mealTime}/update")
    public ResponseEntity<?> updateDiary(@PathVariable String date,
                                         @PathVariable String mealTime,
                                         @RequestBody DiaryUpdateDto dto,
                                         @AuthenticationPrincipal PrincipalDetail principalDetail){

        Member member = principalDetail.getMember();
        Map<String,String> result = diaryService.update(dto,date,mealTime,member);
        return ResponseEntity.ok(result);
    }
}
