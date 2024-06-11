package com.example.dogether.service;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.AdminRepository;
import com.example.dogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public Member login(String loginId, String password){
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        if(findMemberOptional.isPresent()){
            Member member = findMemberOptional.get();
            if(member.getPassword().equals(password)){
                return member;
            }
            else {
                return null;
            }
        }
        return null;
    }

    public Admin loginAdmin(String loginId, String password) {
        Optional<Admin> findAdminOptional = adminRepository.findByLoginId(loginId);
        if(findAdminOptional.isPresent()){
            Admin admin = findAdminOptional.get();
            if(admin.getPassword().equals(password)){
                return admin;
            }
            else {
                return null;
            }
        }
        return null;
    }

    // 오버라이딩으로 가능
    public Optional<Member> findOne(String memberLoginId) {
        return memberRepository.findByLoginId(memberLoginId);
    }
}