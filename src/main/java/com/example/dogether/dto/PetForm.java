package com.example.dogether.dto;

import com.example.dogether.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter @Setter
public class PetForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String name;

    private String kinds;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String content;

    private MultipartFile image;

    private String imageUrl;
}
