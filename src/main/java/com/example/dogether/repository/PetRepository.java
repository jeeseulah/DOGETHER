package com.example.dogether.repository;

import com.example.dogether.domain.member.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    //멤버별로 프로필 분류
    List<Pet> findByMemberId(Long memberId);
}
