package com.fastshoppers.controller;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseMessage registerMember(@RequestBody MemberRequest memberRequest) {

        memberService.registerMember(memberRequest);

        return ResponseMessage.ok();
    }

}
