package com.fastshoppers.repository;

import com.fastshoppers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    /**
     * description : 이메일 조회
     * @param email
     * @return Member
     */
    Member findByEmail(String email);
}
