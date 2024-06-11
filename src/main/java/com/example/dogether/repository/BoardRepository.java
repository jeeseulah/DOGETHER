package com.example.dogether.repository;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findBySubject(String subject);

    Board findBySubjectAndContent(String subject, String content);

    List<Board> findByContentLike(String content);
    //작성자로 조회하는 기능
    //Optional<Board> findByAuthor(Member author);

    Page<Board> findAll(Pageable pageable);
    Page<Board> findByMember(Member member, Pageable pageable);

    Page<Board> findAll(Specification<Board> specB, Pageable pageable);

}
