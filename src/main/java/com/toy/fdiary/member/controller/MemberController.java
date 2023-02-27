package com.toy.fdiary.member.controller;

import com.toy.fdiary.member.model.dto.OAuthDto;
import com.toy.fdiary.member.model.dto.SignDto;
import com.toy.fdiary.member.model.dto.TokenDto;
import com.toy.fdiary.member.service.MemberService;
import com.toy.fdiary.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signUp")
    public ResponseEntity<SignDto.Response> login(@RequestBody SignDto.Request request){
        SignDto.Response result =  memberService.signUser(request);
        return ResponseEntity.ok(result);
    }


    /**
     * 로그아웃
     **/
    @ApiOperation(value = "로그아웃", notes = "사용자의 로그아웃")
    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String,String>> logout(@RequestHeader("Authorization") String accessToken) {
        String username = jwtTokenUtil.getUsername(resolveToken(accessToken));
        Map<String, String> result = memberService.logout(TokenDto.of(accessToken), username);
        return ResponseEntity.ok(result);
    }

    /**
     * Token reissue
     **/
    @ApiOperation(value = "토큰 재발급", notes = "유효기간이 지난 토큰을 재발급합니다.")
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<TokenDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
        return ResponseEntity.ok(memberService.reissue(refreshToken));
    }

    @ApiOperation(value = "OAuth2 회원 정보 요청", notes = "소셜 로그인 시, 필요한 회원 정보를 전달합니다.")
    @PostMapping("/oauth2/info")
    public ResponseEntity<OAuthDto.Response> oauth2Info(@RequestBody Map<String, String> email) {
        OAuthDto.Response response = memberService.oauthInfo(email.get("email"));
        return ResponseEntity.ok(response);
    }

    /**
     * Bearer 부분 빼는 method
     **/
    private String resolveToken(String accessToken) {
        return accessToken.substring(7);
    }


}
