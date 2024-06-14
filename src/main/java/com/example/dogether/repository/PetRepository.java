package com.example.dogether.repository;

import com.example.dogether.domain.member.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByMemberId(Long memberId);
}
