package com.example.dogether.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String imageUrl;

    private String name;

    private String kinds;

    private String gender;

    private Date birth;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 이용할 멤버 정보 갖고 오기
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.REMOVE)
    private List<Diary> diaryList;
}
