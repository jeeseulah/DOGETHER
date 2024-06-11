package com.example.dogether.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
