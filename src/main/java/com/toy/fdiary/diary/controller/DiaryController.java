package com.toy.fdiary.diary.controller;

import com.toy.fdiary.diary.model.dto.DiaryReadDto.Response;
import com.toy.fdiary.diary.model.dto.DiarySaveDto;
import com.toy.fdiary.diary.service.DiaryService;
import com.toy.fdiary.member.model.entity.Member;
import com.toy.fdiary.security.auth.PrincipalDetail;
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


    @GetMapping("/{date}")
    public ResponseEntity<?> diaryRead(@PathVariable String date,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        List<Response> diaries = diaryService.read(date,member);
        return ResponseEntity.ok(diaries);
    }
    @GetMapping("/{date}/{mealTime}")
    public ResponseEntity<?> selectOneDiary(@PathVariable String date, @PathVariable String mealTime,
                                            @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Response result = diaryService.selectOneRead(date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/{date}/{mealTime}/save")
    public ResponseEntity<?> saveDiary(@PathVariable String date,
                                       @PathVariable String mealTime,
                                       @RequestBody DiarySaveDto.Request request,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Map<String, String> result = diaryService.save(request,date,mealTime,member);
        return ResponseEntity.ok(result);
    }
}
