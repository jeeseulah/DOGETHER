package com.example.dogether.dto;

import com.example.dogether.domain.member.Comment;
import com.example.dogether.domain.member.File;
import com.example.dogether.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardForm {

    private Long id;

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private String views;   //조회수

    private LocalDateTime createDate;

    private String name;
    private String email;

}
