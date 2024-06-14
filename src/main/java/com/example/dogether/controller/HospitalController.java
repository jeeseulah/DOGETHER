package com.example.dogether.controller;

import com.example.dogether.domain.PetDB;
import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.PetDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HospitalController {

    //병원페이지
    @GetMapping("/hospital/hospital")
    public String hospitalForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        return "hospital/hospital";
    }

    //약국페이지
    @GetMapping("/hospital/drugstore")
    public String drugstoreForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        return "hospital/drugstore";
    }
}
