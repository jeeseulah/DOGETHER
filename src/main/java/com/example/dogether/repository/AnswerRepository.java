package com.example.dogether.repository;

import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
