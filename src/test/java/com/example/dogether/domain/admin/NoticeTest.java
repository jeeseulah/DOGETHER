package com.example.dogether.domain.admin;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.AdminRepository;
import com.example.dogether.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeTest {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired private AdminRepository adminRepository;

    @Test
    void 공지사항대량데이터삽입() {
        Optional<Admin> byAdminId = adminRepository.findById(1l);
        if(byAdminId.isPresent()){
            Admin admin = byAdminId.get();
            for(int i=1; i<=300; i++){
                Notice notice = new Notice();
                notice.setSubject("제목입니다. " + i);
                notice.setContent("내용입니다. " + i);
                notice.setCreateDate(LocalDateTime.now());
                this.noticeRepository.save(notice);
            }
        }
    }
}