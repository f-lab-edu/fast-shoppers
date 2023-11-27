package com.fastshoppers.repository;

import com.fastshoppers.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        member.setDeleteYn("N");
        // When
        Member savedMember = memberRepository.save(member);

        // Then
        assertNotNull(savedMember.getId());
        assertEquals("test1234@google.com", savedMember.getEmail());
    }
}
