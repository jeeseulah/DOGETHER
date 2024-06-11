package com.example.dogether.domain.member;

import com.example.dogether.domain.admin.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qusetion_id")
    private Long id;

    //private String tel;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private String password;

    // 공개 / 비공개
    private Boolean isShow;

    // 개인처리 동의 / 비동의
    private Boolean isAgree;

    // name, loginId
    @ManyToOne
    @JoinColumn(name = "memberName")
    private Member author;


    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

}
