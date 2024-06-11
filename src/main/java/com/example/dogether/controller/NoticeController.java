package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.admin.Notice;
import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.dto.BoardForm;
import com.example.dogether.dto.NoticeForm;
import com.example.dogether.dto.QuestionForm;
import com.example.dogether.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항페이지
    @GetMapping("/community/notice/notice_list")
    public String noticeListForm(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            ,@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @RequestParam(value="page", defaultValue = "0") int page
            , @RequestParam(value="kWord", defaultValue = "") String kWord
            , @RequestParam(value="searchType", defaultValue = "") String searchType
            , Model model) {
        Page<Notice> paging = this.noticeService.getNoticeList(page, kWord, searchType);
        model.addAttribute("paging", paging);
        model.addAttribute("kWord", kWord);
        model.addAttribute("searchType", searchType);

        if(loginMember == null && loginAdmin == null) {
            return "community/notice/notice_list";
        }
        if (loginMember != null) {
            model.addAttribute("loginMember", loginMember);
        }

        if(loginAdmin != null) {
            model.addAttribute("loginAdmin", loginAdmin);
        }

        return "community/notice/notice_list";
    }

    @GetMapping("/community/notice/notice_create")
    public String noticeCreateForm(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            ,@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            ,NoticeForm noticeForm
            , Model model) {
        // 관리자가 아니면 목록 패이지로
        if (loginAdmin == null && loginMember == null) {
            return "community/notice/notice_list";
        } else if(loginMember != null){
            model.addAttribute("loginMember", loginMember);
            return "community/notice/notice_list";
        } else {
            model.addAttribute("noticeForm", noticeForm);
            model.addAttribute("loginAdmin", loginAdmin);
        }

        return "community/notice/notice_create";
    }

    @PostMapping("/community/notice/notice_create")
    public String noticeCreate(@ModelAttribute("noticeForm") NoticeForm form
            , @SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , Model model) {

        Notice notice = new Notice();
        notice.setId(form.getId());
        notice.setLoginId(loginAdmin.getLoginId());
        notice.setSubject(form.getSubject());
        notice.setContent(form.getContent());
        notice.setCreateDate(LocalDateTime.now());
        noticeService.create(notice);

        model.addAttribute("loginAdmin", loginAdmin);

        return "redirect:/community/notice/notice_list";
    }

    //공지사항 글보기 페이지
    @GetMapping("/community/notice/notice_detail/{noticeId}")
    public String noticeDetailForm(@PathVariable("noticeId") Long noticeId
            ,@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            ,@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            ,NoticeForm noticeForm
            , Model model) {

            model.addAttribute("loginMember", loginMember);
            model.addAttribute("loginAdmin", loginAdmin);

        Notice notice = this.noticeService.getNotice(noticeId);

        model.addAttribute("noticeForm", noticeForm);
        model.addAttribute("notice", notice);

        return "community/notice/notice_detail";
    }

    //공지사항 수정 페이지
    @GetMapping("/community/notice/notice_modify/{noticeId}")
    public String noticeModifyForm(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, NoticeForm noticeForm, Model model, @PathVariable("noticeId") Long noticeId){
        model.addAttribute("loginAdmin", loginAdmin);

        Notice notice = this.noticeService.getNotice(noticeId);
        noticeForm.setSubject(notice.getSubject());
        noticeForm.setContent(notice.getContent());


        return "community/notice/notice_create";
    }

    @PostMapping("/community/notice/notice_modify/{noticeId}")
    public String noticeModify(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, NoticeForm noticeForm, Model model, @PathVariable("noticeId") Long noticeId) {
        model.addAttribute("loginAdmin", loginAdmin);

        Notice findNotice = this.noticeService.getNotice(noticeId);

        Notice notice = new Notice();
        notice.setId(noticeId);
        notice.setAdmin(loginAdmin);
        notice.setSubject(noticeForm.getSubject());
        notice.setContent(noticeForm.getContent());
        notice.setCreateDate(findNotice.getCreateDate());
        notice.setLoginId(loginAdmin.getLoginId());

        noticeService.create(notice);

        return String.format("redirect:/community/notice/notice_detail/%s",noticeId);
    }

    //공지사항 삭제 페이지
    @GetMapping("/community/notice/notice_delete/{noticeId}")
    public String noticeDeleteForm(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, Model model, @PathVariable("noticeId") Long noticeId) {
        model.addAttribute("loginAdmin", loginAdmin);

        Notice notice = this.noticeService.getNotice(noticeId);
        this.noticeService.delete(notice);

        return "redirect:/community/notice/notice_list";
    }
}