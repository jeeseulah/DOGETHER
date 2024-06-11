package com.example.dogether.domain.admin;

import com.example.dogether.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminTest {
    @Autowired private AdminRepository adminRepository;

    @Test
    void  관리자1() {
        Admin admin = new Admin();
        admin.setLoginId("admin@admin.com");
        admin.setPassword("1234");
        admin.setName("관리자");
        this.adminRepository.save(admin);
    }
}