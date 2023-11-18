package com.fastshoppers.controller;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.common.StatusCode;
import com.fastshoppers.entity.Member;
import com.fastshoppers.model.MemberDto;
import com.fastshoppers.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseMessage registerMember(@RequestBody MemberDto memberDto) {

        Member registeredMember = memberService.registerMember(memberDto);

        return ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .statusCode(StatusCode.OK.getCode())
                .build();
    }

}
