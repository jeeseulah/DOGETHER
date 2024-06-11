package com.example.dogether.domain.admin;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 이름, 로그인 아이디
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin author;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}