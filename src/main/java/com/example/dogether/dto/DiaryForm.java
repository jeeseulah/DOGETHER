package com.example.dogether.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @Setter
public class DiaryForm {
    private Long id;

    private Long memberId;

    private String subject;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date doDate;

    private String content;

    private String meal;

    private String note;
}
