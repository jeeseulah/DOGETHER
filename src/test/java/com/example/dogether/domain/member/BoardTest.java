package com.example.dogether.domain.member;

import com.example.dogether.repository.BoardRepository;
import com.example.dogether.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;


    @Test
    void 게시판대량데이터삽입() {
        Optional<Member> byLoginId = memberRepository.findById(1l);
        if(byLoginId.isPresent()){
            Member member = byLoginId.get();
            for(int i=1; i<=300; i++){
                Board board = new Board();
                board.setSubject("제목입니다. " + i);
                board.setContent("내용입니다. " + i);
                board.setCreateDate(LocalDateTime.now());
                board.setViews(0);
                board.setMember(member);
                this.boardRepository.save(board);
            }
        }
    }

}