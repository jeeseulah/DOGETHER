package com.example.dogether.domain.member;

import com.example.dogether.repository.MemberRepository;
import com.example.dogether.repository.QuestionRepository;
import com.example.dogether.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionTest {
    @Autowired private QuestionRepository questionRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private QuestionService questionService;

    @Test
    void 질문1() {
        Question question1 = new Question();

        //question1.setAuthor("테스터1");
        //question1.setLoginId("tester1");
        //question1.setTel("010-1234-5678");
        question1.setSubject("질문1 제목 입니다.");
        question1.setContent("질문1 내용 입니다.");
        question1.setCreateDate(LocalDateTime.now());
        question1.setIsShow(false); //비공개
        question1.setIsAgree(true); //동의
        this.questionRepository.save(question1);
    }

    @Test
    void 질문2() {
        Question question2 = new Question();

        //question2.setAuthor("테스터2");
        //question2.setLoginId("tester2");
        //question2.setTel("010-4567-1234");
        question2.setSubject("질문2 제목 입니다.");
        question2.setContent("질문2 내용 입니다.");
        question2.setCreateDate(LocalDateTime.now());
        question2.setIsShow(true); //비공개
        question2.setIsAgree(true); //동의
        this.questionRepository.save(question2);
    }


    @Test
    void 모든질문데이터조회(){
        List<Question> questionList = this.questionRepository.findAll();
        assertEquals(2, questionList.size());

        Question question1 = questionList.get(0);
        //assertEquals("테스터1", question1.getAuthor());
        //assertEquals("tester1", question1.getLoginId());
        assertEquals("질문1 제목 입니다.", question1.getSubject());
        assertEquals("질문1 내용 입니다.", question1.getContent());

        Question question2 = questionList.get(1);
        //assertEquals("테스터2", question2.getAuthor());
        //assertEquals("tester2", question2.getLoginId());
        assertEquals("질문2 제목 입니다.", question2.getSubject());
        assertEquals("질문2 내용 입니다.", question2.getContent());
    }

    @Test
    void 대량데이터삽입() {
        Optional<Member> byLoginId = memberRepository.findById(1l);
        if(byLoginId.isPresent()){
            Member member = byLoginId.get();
            for(int i=1; i<=300; i++){
                Question question = new Question();
                question.setSubject("제목입니다. " + i);
                question.setContent("내용입니다. " + i);
                question.setCreateDate(LocalDateTime.now());
                question.setIsShow(true); //비공개
                question.setPassword("1111");
                question.setIsAgree(true); //동의
                question.setAuthor(member);
                this.questionRepository.save(question);
            }
        }
    }

    //아이디가 1번인것 찾기
    /*@Test
    void 작성자로찾기() {
        Optional<Question> findQuestion = this.questionRepository.findByAuthor("테스터1");
        if(findQuestion.isPresent()){
            Question question = findQuestion.get();
            //assertEquals(1L, question.getId());
            assertEquals("질문1 내용 입니다.", question.getContent());
        }
    }*/

//    //제목으로 찾기
//    @Test
//    void 제목으로찾기() {
//        Optional<Question> findQuestion = this.questionRepository.findBySubject("질문2 제목 입니다.");
//        if(findQuestion.isPresent()) {
//            Question question = findQuestion.get();
//            assertEquals(2L, question.getId());
//            assertEquals("질문2 내용 입니다.", question.getContent());
//        }
//    }
}