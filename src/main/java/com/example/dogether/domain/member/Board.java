// Board Domain

package com.example.dogether.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    // author, loginId
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<File> fileList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}
