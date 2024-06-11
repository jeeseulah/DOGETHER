package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import com.example.dogether.dto.LoginForm;
import com.example.dogether.service.LoginService;
import com.example.dogether.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, Model model){
        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.isEmpty()) {
            request.getSession().setAttribute("prevPage", referrer);
        }

        model.addAttribute("loginForm", new LoginForm());
        return "user/loginForm";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        // 입력 검증에서 에러가 발생한 경우
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }

        HttpSession session = request.getSession(true);

        // 관리자 로그인 처리
        if (loginForm.getLoginId().equals("admin@admin.com")) {
            Admin loginAdmin = loginService.loginAdmin(loginForm.getLoginId(), loginForm.getPassword());

            if (loginAdmin == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
                return "user/loginForm";
            }

            session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);
            return "redirect:/admin";
        }

        // 일반 사용자 로그인 처리
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("로그인한 멤버 {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "user/loginForm";
        }

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            return "redirect:/";
   }

   //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //현재 요청에 대해 세션의 존재 여부 확인
        HttpSession session = request.getSession(false);

        //invalidate 메서드로 세션 소멸
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }
}
