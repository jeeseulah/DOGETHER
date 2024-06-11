package com.example.dogether.controller;

import com.example.dogether.domain.member.Diary;
import com.example.dogether.domain.member.Member;
import com.example.dogether.dto.DiaryForm;
import com.example.dogether.service.DiaryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    //다이어리 페이지
    @GetMapping("/diary")
    public String diaryPage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "redirect:/login";
        }

        List<Diary> diaryList = diaryService.diaryMemberId(member.getId());
        model.addAttribute("diaryList", diaryList);
        model.addAttribute("diaryForm", new DiaryForm());
        //model.addAttribute("member", member);
        model.addAttribute("loginMember", member);
        return "diary/diary";
    }

    @PostMapping("/diary")
    public String saveDiary(HttpSession session, @Valid @ModelAttribute("diaryForm") DiaryForm diaryForm, BindingResult result, Model model) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            List<Diary> diaryList = diaryService.diaryList();
            model.addAttribute("diaryList", diaryList);
            model.addAttribute("loginMember", member);
            return "diary/diary";
        }

        Diary diary = new Diary();
        diary.setSubject(diaryForm.getSubject());
        diary.setDoDate(diaryForm.getDoDate());
        diary.setContent(diaryForm.getContent());
        diary.setMeal(diaryForm.getMeal());
        diary.setNote(diaryForm.getNote());

        diary.setMember(member);
        diaryService.saveDiary(diary);
        return "redirect:/diary";
    }

    //삭제
    @GetMapping("/diary/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Diary deleteDiary = diaryService.findDiaryById(id);
        if(deleteDiary != null) {
            diaryService.deleteDiary(deleteDiary);
        }
        return "redirect:/diary";
    }
}
