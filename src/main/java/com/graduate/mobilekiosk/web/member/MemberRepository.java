package com.graduate.mobilekiosk.web.member;


import com.graduate.mobilekiosk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserId(String userId);

    Member findByStoreName(String storeName);

    void deleteByUserId(String userName);
}
