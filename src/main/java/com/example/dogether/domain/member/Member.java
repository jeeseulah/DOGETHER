package com.example.dogether.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String tel;

    private String address;

    // 개인처리정보 동의 / 비동의
    private Boolean isAgree;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Board> boardList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Pet> petList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}

