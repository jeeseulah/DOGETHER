package com.example.dogether.controller;

import com.example.dogether.domain.member.Member;
import com.example.dogether.dto.MemberForm;
import com.example.dogether.repository.MemberRepository;
import com.example.dogether.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "user/register";
    }

    // 아이디 중복 확인
    @PostMapping("/checkLoginId")
    public String checkLoginId(@ModelAttribute("memberForm") MemberForm form, Model model, BindingResult bindingResult) {
        boolean isDuplicate = memberService.checkByLoginId(form.getLoginId());
        model.addAttribute("isDuplicate", isDuplicate);
        model.addAttribute("memberForm", form); // 폼 데이터 유지

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        return "user/register";
    }

    @PostMapping("/add")
    public String create(@Validated @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user/register";
        }

        if (!form.getPassword().equals(form.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword", "password", "비밀번호를 다시 확인해주세요.");
            return "user/register";
        }

        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setTel(form.getTel());
        member.setAddress(form.getAddress());
        member.setIsAgree(form.getIsAgree());

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("loginId", "duplicate", "사용 중인 아이디입니다.");
            return "user/register";
        }

        return "redirect:/";
    }

    //회원목록
    @GetMapping("/members")
    public String list(@SessionAttribute(name = com.example.dogether.controller.SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){
        List<Member> members = memberService.findMember();
        model.addAttribute("members",members);
        model.addAttribute("loginMember", loginMember);
        return "admin/memberList";
    }

    //회원수정페이지
    @GetMapping("/members/{memberid}/edit")
    public String updateMemberForm(@SessionAttribute(name = com.example.dogether.controller.SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @PathVariable("memberid") Long memberid, Model model){

        Optional<Member> findMember = memberService.findOne(memberid);

        if(findMember.isPresent()){
            Member findMemberGet = findMember.get();

            MemberForm memberForm = new MemberForm();
            memberForm.setId(findMemberGet.getId());
            memberForm.setLoginId(findMemberGet.getLoginId());
            memberForm.setPassword(findMemberGet.getPassword());
            memberForm.setName(findMemberGet.getName());
            memberForm.setTel(findMemberGet.getTel());
            memberForm.setAddress(findMemberGet.getAddress());
            memberForm.setIsAgree(findMemberGet.getIsAgree());

            System.out.println(findMemberGet.getPassword());
            model.addAttribute("memberForm", memberForm);
            model.addAttribute("loginMember", loginMember);
            return "admin/updateMemberForm";
        }
        model.addAttribute("loginMember", loginMember);
        return "admin/memberList";
    }

    @PostMapping("/members/{memberid}/edit")
    public String updateMember(@Validated @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult
            , @SessionAttribute(name = com.example.dogether.controller.SessionConst.LOGIN_MEMBER
            , required = false) Member loginMember, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("loginMember", loginMember);
            return "admin/updateMemberForm";
        }

        Member member = new Member();
        member.setId(form.getId());
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setTel(form.getTel());
        member.setAddress(form.getAddress());
        member.setIsAgree(form.getIsAgree());
        memberService.save(member);
        model.addAttribute("loginMember", loginMember);
        return "redirect:/members";
    }

    //삭제
    @GetMapping("/members/{memberid}/delete")
    public String delete(@PathVariable("memberid") Long memberid){
        Optional<Member> findMember = memberService.findOne(memberid);

        if(findMember.isPresent()){
            Member deleteMember = findMember.get();
            memberService.delete(deleteMember);
        }
        return "redirect:/members";
    }
}

