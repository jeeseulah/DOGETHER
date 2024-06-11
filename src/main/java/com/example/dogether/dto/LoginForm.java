package com.example.dogether.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {
    @NotEmpty(message = "회원 ID는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
}
