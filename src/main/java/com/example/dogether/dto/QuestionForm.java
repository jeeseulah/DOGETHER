package com.example.dogether.dto;

import com.example.dogether.domain.member.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class QuestionForm {
    private Long id;

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max=200)
    private String subject;     // 제목

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;     // 내용
    private LocalDateTime createDate;   // 작성일

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;    // 비밀글 패스워드
    private Boolean isShow;     // 공개/비공개

    private Boolean isAgree;    // 개인정보처리 동의/비동의

    private String name;
    private String email;
    private String tel;
}
