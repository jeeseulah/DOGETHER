package com.example.dogether.controller;

import com.example.dogether.dto.FindIdForm;
import com.example.dogether.dto.FindPwForm;
import com.example.dogether.service.FindService;
import com.example.dogether.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FindController {

    private final MemberService memberService;
    private final FindService findService;

    @GetMapping("/user/findIdForm")
    public String showFindIdForm(Model model) {
        model.addAttribute("findIdForm", new FindIdForm());
        return "user/findIdForm";
    }

    @GetMapping("/user/findIdResult")
    public String showFindIdResult(Model model) {
        return "user/findIdResult";
    }

    @PostMapping("/user/findIdForm")
    public String findId(@ModelAttribute("findIdForm") FindIdForm findIdForm, Model model) {
        String loginId = findService.findLoginId(findIdForm.getName(), findIdForm.getTel());
        if (loginId == null) {
            model.addAttribute("message", "아이디를 찾지 못했습니다.");
        } else {
            model.addAttribute("loginId", loginId);
        }
        return "user/findIdResult";
    }

    @GetMapping("/user/findPwForm")
    public String showFindPwForm(Model model) {
        model.addAttribute("findPwForm", new FindPwForm());
        return "user/findPwForm";
    }

    @GetMapping("/user/findPwResult")
    public String showFindPwResult() {
        return "user/findPwResult";
    }

    @PostMapping("/user/findPwForm")
    public String findPw(@ModelAttribute("findPwForm") FindPwForm findPwForm, Model model) {
        String password = findService.findPassword(findPwForm.getName(), findPwForm.getLoginId());
        if (password == null) {
            model.addAttribute("message", "비밀번호를 찾지 못했습니다.");
        } else {
            model.addAttribute("password", password);
        }
        return "user/findPwResult";
    }
}
