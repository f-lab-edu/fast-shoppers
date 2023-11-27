package com.fastshoppers.service;

import com.fastshoppers.entity.Member;
import com.fastshoppers.exception.DuplicateEmailException;
import com.fastshoppers.exception.InvalidPasswordException;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @description: 회원가입 비즈니스 로직. 이메일 중복 확인, password 유효성 검사 후 멤버 디비 저장
     * @param memberRequest
     * @return Member
     */
    public Member registerMember(MemberRequest memberRequest) {
        if (memberRepository.findByEmail(memberRequest.getEmail()) != null) {
            throw new DuplicateEmailException();
        }

        if(!isValidPassword(memberRequest.getPassword())) {
            throw new InvalidPasswordException();
        }

        Member member = convertToEntity(memberRequest);

        return memberRepository.save(member);
    }

    /**
     * @description: DTO -> Entity로 변환하는 메서드
     * @param memberRequest
     * @return Member
     */
    private Member convertToEntity(MemberRequest memberRequest) {
        Member member = new Member();
        member.setEmail(memberRequest.getEmail());
        member.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
        return member;
    }

    /**
     * @description: 패스워드 유효성 체크. 대소문자, 특수문자 포함 8~16자리 체크
     * @param password
     * @return boolean value
     */
    private boolean isValidPassword(String password) {
        String passwordPattern = "^[a-zA-Z0-9]{8,16}$";
        return Pattern.matches(passwordPattern, password);
    }


}
