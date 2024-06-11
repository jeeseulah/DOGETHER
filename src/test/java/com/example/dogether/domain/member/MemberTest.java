package com.example.dogether.domain.member;

import com.example.dogether.repository.MemberRepository;
import com.example.dogether.repository.QuestionRepository;
import com.example.dogether.service.MemberService;
import com.example.dogether.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTest {
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    void 회원1() {
        Member member = new Member();
        member.setLoginId("adc@naver.com");
        member.setPassword("1234");
        member.setName("홍길동");
        member.setTel("010-1234-5678");
        member.setAddress("경기도 성남시");
        member.setIsAgree(true);
        this.memberService.join(member);
    }

    @Test
    void 회원2() {
        Member member = new Member();
        member.setLoginId("def@naver.com");
        member.setPassword("1234");
        member.setName("테스터");
        member.setTel("010-1111-222");
        member.setAddress("경기도 용인시");
        member.setIsAgree(true);
        this.memberService.join(member);
    }
}