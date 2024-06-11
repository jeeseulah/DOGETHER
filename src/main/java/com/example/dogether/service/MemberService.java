package com.example.dogether.service;

import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    //가입
    public void join(Member member) {
        validateDuplicateMember(member);
        this.memberRepository.save(member);
    }


    //중복체크
    public boolean checkByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }


    //중복회원
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
        if(findMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    //수정
    public Long save(Member member){
        this.memberRepository.save(member);
        return member.getId();
    }


    //삭제
    public void delete(Member member){
        this.memberRepository.delete(member);
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }


    //전체회원조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }


    //단건조회
    public Optional<Member>findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}


