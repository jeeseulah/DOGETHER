package com.example.dogether.repository;

import com.example.dogether.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNameAndTel(String name, String tel);

    Optional<Member> findByNameAndLoginId(String loginId, String name);

    boolean existsByLoginId(String loginId);
}
