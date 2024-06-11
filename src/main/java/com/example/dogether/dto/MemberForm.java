package com.example.dogether.dto;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Question;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MemberForm {

    private Long id;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Size (max = 200)
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String checkPassword;

    @NotEmpty(message = "이름은 필수입니다")
    private String name;

    private String tel;

    private String address;

    @AssertTrue(message = "개인정보처리에 동의해주세요")
    private Boolean isAgree;
}
