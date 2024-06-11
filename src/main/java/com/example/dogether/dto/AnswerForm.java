package com.example.dogether.dto;

import com.example.dogether.domain.admin.Admin;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AnswerForm {
    private Long id;

    private String content;

    private LocalDateTime createDate;

}
