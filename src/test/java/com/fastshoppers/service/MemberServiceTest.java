package com.fastshoppers.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshoppers.entity.Member;
import com.fastshoppers.exception.DuplicateEmailException;
import com.fastshoppers.exception.InvalidPasswordException;
import com.fastshoppers.exception.LoginFailException;
import com.fastshoppers.exception.MemberNotFoundException;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.model.TokenResponse;
import com.fastshoppers.repository.MemberRepository;
import com.fastshoppers.util.JwtUtil;
import com.fastshoppers.util.PasswordEncryptionUtil;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private AuthTokenRedisService authTokenRedisService;

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
		MemberRequest memberRequest = new MemberRequest("test2@google.com", "1234");

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

	@Test
	void login_Failure_MemberNotFound() {
		MemberRequest memberRequest = new MemberRequest("user@google.com", "Password1234");
		when(memberRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(MemberNotFoundException.class, () -> {
			memberService.login(memberRequest);
		});
	}

	@Test
	void login_Failure_IncorrectPassword() {
		MemberRequest memberRequest = new MemberRequest("user@google.com", "notmatchedPassword123");
		Member member = new Member();
		member.setEmail("user@google.com");
		member.setPassword(PasswordEncryptionUtil.encryptPassword("Password1234"));

		when(memberRepository.findByEmail(anyString())).thenReturn(member);

		assertThrows(LoginFailException.class, () -> {
			memberService.login(memberRequest);
		});
	}

	@Test
	void login_Successful() {
		MemberRequest memberRequest = new MemberRequest("user@google.com", "Password1234");
		Member member = new Member();
		member.setEmail("user@google.com");
		member.setPassword(PasswordEncryptionUtil.encryptPassword("Password1234"));

		String expectedAccessToken = "access.token.string";
		String expectedRefreshToken = "refresh.token.string";

		when(memberRepository.findByEmail(anyString())).thenReturn(member);
		when(jwtUtil.generateAccessToken(anyString())).thenReturn(expectedAccessToken);
		when(jwtUtil.generateRefreshToken(anyString())).thenReturn(expectedRefreshToken);

		TokenResponse tokenResponse = memberService.login(memberRequest);

		assertNotNull(tokenResponse);
		assertEquals(expectedAccessToken, tokenResponse.getAccessToken());
		assertEquals(expectedRefreshToken, tokenResponse.getRefreshToken());
	}

	@Test
	void logout_Successful() {
		MemberRequest memberRequest = new MemberRequest("user@google.com", "Password1234");

		memberService.logout(memberRequest);

		then(authTokenRedisService).should().deleteRefreshToken(memberRequest.getEmail());
	}

}
