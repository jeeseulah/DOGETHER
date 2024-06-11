package com.example.dogether.repository;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    //제목으로 조회하는 기능
    Question findBySubject(String subject);

    //작성자로 조회하는 기능
    //Question findByAuthor(Member author);

    //목록조회
    Page<Question> findAll(Pageable pageable);

    Page<Question> findByAuthor(Member author, Pageable pageable);

    Page<Question> findAll(Specification<Question> specQ, Pageable pageable);

}
