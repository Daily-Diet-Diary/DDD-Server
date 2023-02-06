package com.toy.fdiary.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    @ApiOperation(value = "회원 가입", notes = "입력한 정보로 회원가입한다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp() {

        return ResponseEntity.ok(null);
    }

}
