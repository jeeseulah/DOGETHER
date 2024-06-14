package com.example.dogether.controller;

import com.example.dogether.controller.SessionConst;
import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Member;
import com.example.dogether.dto.MemberForm;
import com.example.dogether.service.AdminService;
import com.example.dogether.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;



    //회원목록
    @GetMapping("/admin/memberList")
    public String list(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, Model model){
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);
        model.addAttribute("loginAdmin", loginAdmin);
        return "admin/memberList";
    }

    // 회원 수정 페이지
    @GetMapping("/admin/memberList/{memberid}/edit")
    public String updateMemberForm(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, @PathVariable("memberid") Long memberid, Model model){

        Optional<Member> findMember = memberService.findOne(memberid);

        if(findMember.isPresent()){
            Member findMemberGet =  findMember.get();

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
            model.addAttribute("loginAdmin", loginAdmin);
            return "admin/memberUpdate";
        }
        model.addAttribute("loginAdmin", loginAdmin);
        return "admin/memberList";
    }

    @PostMapping("/admin/memberList/{memberid}/edit")
    public String updateMember(@Validated @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult
            , @SessionAttribute(name = SessionConst.LOGIN_ADMIN
            , required = false) Admin loginAdmin, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("loginAdmin", loginAdmin);
            return "admin/memberUpdate";
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
        model.addAttribute("loginAdmin", loginAdmin);
        return "redirect:/admin";
    }

    //삭제
    @GetMapping("/admin/memberList/{memberid}/delete")
    public String delete(@PathVariable("memberid") Long memberid){
        Optional<Member> findMember = memberService.findOne(memberid);

        if(findMember.isPresent()){
            Member deleteMember = findMember.get();
            memberService.delete(deleteMember);
        }
        return "redirect:/admin";
    }
}
