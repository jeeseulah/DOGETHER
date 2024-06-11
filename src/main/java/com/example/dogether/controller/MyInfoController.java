package com.example.dogether.controller;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Diary;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.service.BoardService;
import com.example.dogether.service.DiaryService;
import com.example.dogether.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyInfoController {

    private final DiaryService diaryService;
    private final QuestionService questionService;
    private final BoardService boardService;

    //내정보페이지
    @GetMapping("/myInfo")
    public String myInfoForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @RequestParam(value = "kWord", defaultValue = "") String kWord, @RequestParam(value = "searchType", defaultValue = "") String searchType, Model model){
        model.addAttribute("loginMember", loginMember);

        List<Diary> latestDiarys = diaryService.diaryMemberId(loginMember.getId());
        model.addAttribute("latestDiarys",latestDiarys);

        List<Question> latestQuestions = questionService.getLatestQuestion(5, loginMember);
        model.addAttribute("latestQuestions", latestQuestions);

        List<Board> latestBoards = boardService.getLatestBoard(5, loginMember);
        model.addAttribute("latestBoards", latestBoards);

        model.addAttribute("kWord", loginMember.getName());
        model.addAttribute("searchType", "name");

        return "user/myInfo";
    }
}
