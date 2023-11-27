package com.fastshoppers.service;

import com.fastshoppers.entity.Member;
import com.fastshoppers.exception.DuplicateEmailException;
import com.fastshoppers.exception.InvalidPasswordException;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void whenRegisterWithExistingEmail_thenThrowDuplicationEmailException() {
        // Given
        MemberRequest memberRequest = new MemberRequest("test1234@google.com", "test1234");
        // 새 멤버 객체로 반환되어 메일이 존재함
        when(memberRepository.findByEmail(memberRequest.getEmail())).thenReturn(new Member());

        assertThrows(DuplicateEmailException.class, () -> {
            memberService.registerMember(memberRequest);
        });
    }

    @Test
    void whenRegisterWithInvalidPassword_thenThrowInvalidPasswordException() {
        // Given
        MemberRequest memberRequest = new MemberRequest("test2@google.com","1234");

        assertThrows(InvalidPasswordException.class, () -> {
            memberService.registerMember(memberRequest);
        });
    }

    @Test
    void whenRegisterWithValidEmailAndPassword_thenSucceed() {
        // Given
        MemberRequest memberDto = new MemberRequest("newuser@example.com", "Password1234");
        Member expectedMember = new Member();
        expectedMember.setEmail(memberDto.getEmail());
        expectedMember.setPassword("encodedPassword");

        when(memberRepository.findByEmail(memberDto.getEmail())).thenReturn(null);
        when(memberRepository.save(any(Member.class))).thenReturn(expectedMember);

        // When
        Member registeredMember = memberService.registerMember(memberDto);

        assertNotNull(registeredMember);
        assertEquals(registeredMember.getEmail(), memberDto.getEmail());
        assertEquals("encodedPassword", registeredMember.getPassword());

    }

}
