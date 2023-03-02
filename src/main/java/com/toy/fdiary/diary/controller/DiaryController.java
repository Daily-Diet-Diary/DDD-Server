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

    @ApiOperation(value = "�ϱ� ��ȸ", notes = "�ش� ������ ����� �ϱ� �б�")
    @GetMapping("/{date}")
    public ResponseEntity<?> diaryRead(@PathVariable String date,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        List<Response> diaries = diaryService.read(date,member);
        return ResponseEntity.ok(diaries);
    }
    @ApiOperation(value = "Ư�� �ϱ� ��ȸ", notes = "�ش��ϴ� Ư�� �ϱ⸸ �б�")
    @GetMapping("/{date}/{mealTime}")
    public ResponseEntity<?> selectOneDiary(@PathVariable String date, @PathVariable String mealTime,
                                            @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Response result = diaryService.selectOneRead(date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "�б� �ۼ�", notes = "�ϱ⸦ ����մϴ�.")
    @PostMapping("/{date}/{mealTime}/save")
    public ResponseEntity<?> saveDiary(@PathVariable String date,
                                       @PathVariable String mealTime,
                                       @RequestBody DiarySaveDto.Request request,
                                       @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Map<String, String> result = diaryService.save(request,date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "Ư�� �ϱ� ����", notes = "�ϱ� ����")
    @DeleteMapping("/{date}/{mealTime}/delete")
    public ResponseEntity<?> deleteDiary(@PathVariable String date, @PathVariable String mealTime,
                                         @AuthenticationPrincipal PrincipalDetail principalDetail){
        Member member = principalDetail.getMember();
        Map<String,String> result = diaryService.delete(date,mealTime,member);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value="Ư�� �ϱ� ����", notes="Ư�� �ϱ��� ���� �� ������ �����Ѵ�.")
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
