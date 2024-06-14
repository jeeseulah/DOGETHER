package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class PuppyshopController {
    @GetMapping("/puppyshop/puppyshop")
    public String puppyshopForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        return "puppyshop/puppyshop";
    }
}
