package com.fastshoppers.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fastshoppers.entity.Member;

@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void testSaveMember() {
		// Given
		Member member = new Member();
		member.setEmail("test1234@google.com");
		member.setPassword("test123456");
		member.setSalt("someRandomlyGeneratedSalt");
		member.setDeleteYn(false);
		// When
		Member savedMember = memberRepository.save(member);

		// Then
		assertNotNull(savedMember.getId());
		assertEquals("test1234@google.com", savedMember.getEmail());
		assertEquals("someRandomlyGeneratedSalt", savedMember.getSalt());
	}
}
