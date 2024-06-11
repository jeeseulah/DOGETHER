package com.example.dogether.repository;

import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
