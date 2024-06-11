package com.example.dogether.service;

import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindService {
    private final MemberRepository memberRepository;

    public String findLoginId(String name, String tel) {
       Optional<Member> memberOptional = memberRepository.findByNameAndTel(name, tel);

       if (memberOptional.isPresent()) {
           Member member = memberOptional.get();
           return member.getLoginId();
       } else {
           return null;
       }

    }

    public String findPassword(String name, String loginId) {
        Optional<Member> memberOpt = memberRepository.findByNameAndLoginId(name, loginId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            return member.getPassword();
        } else {
            return null;
        }
    }
}
