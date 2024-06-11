package com.example.dogether.repository;

import com.example.dogether.domain.member.Diary;
import com.example.dogether.domain.member.Pet;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long > {
    List<Diary> findByMemberId(Long memberId);
}
