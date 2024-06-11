package com.example.dogether.controller;

import com.example.dogether.domain.PetDB;
import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.PetDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CafeController {

    @GetMapping("/cafe/cafe")
    public String cafeListForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , Model model){
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        return "cafe/cafe";
    }

}
