package com.fastshoppers.controller;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.model.TokenResponse;
import com.fastshoppers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseMessage registerMember(@RequestBody MemberRequest memberRequest) {

        memberService.registerMember(memberRequest);

        return ResponseMessage.ok();
    }


    @PostMapping("/login")
    public ResponseMessage login(@RequestBody MemberRequest memberRequest) {
        TokenResponse tokenResponse = memberService.login(memberRequest);

        return ResponseMessage.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseMessage logout(@RequestBody MemberRequest memberRequest) {

        memberService.logout(memberRequest);

        return ResponseMessage.ok();
    }

}
