package com.example.dogether.repository;

import com.example.dogether.domain.admin.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findBySubject(String subject);
    Notice findByContent(String content);
    Notice findBySubjectAndContent(String subject, String content);

    List<Notice> findBySubjectLike(String subject);

    Page<Notice> findAll(Pageable pageable);

    Page<Notice> findAll(Specification<Notice> specN, Pageable pageable);
}
