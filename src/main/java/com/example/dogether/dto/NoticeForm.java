package com.example.dogether.dto;

import com.example.dogether.domain.admin.Admin;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NoticeForm {
    private Long id;
    // 로그인 아이디
    private Admin admin;

    private String subject;

    private String content;

    private LocalDateTime createDate;
}
