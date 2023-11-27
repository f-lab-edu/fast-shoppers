package com.fastshoppers.controller;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.common.StatusCode;
import com.fastshoppers.entity.Member;
import com.fastshoppers.model.MemberDto;
import com.fastshoppers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseMessage registerMember(@RequestBody MemberDto memberDto) {

        memberService.registerMember(memberDto);

        return ResponseMessage.ok();
    }

}
