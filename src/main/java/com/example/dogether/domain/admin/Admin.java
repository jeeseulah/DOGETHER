package com.example.dogether.domain.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.REMOVE)
    private List<Notice> noticeList;
}
