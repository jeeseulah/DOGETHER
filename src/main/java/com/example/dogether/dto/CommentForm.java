package com.example.dogether.dto;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentForm {

    private Long id;

    @NotEmpty(message = "댓글을 적어주세요.")
    private String content;

    private LocalDateTime createDate;

}
