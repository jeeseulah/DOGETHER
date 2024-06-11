package com.example.dogether.service;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findAdmin() {
        return adminRepository.findAll();
    }
}
