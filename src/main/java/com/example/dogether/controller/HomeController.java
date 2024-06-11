package com.example.dogether.controller;

import com.example.dogether.controller.SessionConst;
import com.example.dogether.domain.admin.Notice;
import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.repository.MemberRepository;
import com.example.dogether.service.BoardService;
import com.example.dogether.service.NoticeService;
import com.example.dogether.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final QuestionService questionService;
    private final NoticeService noticeService;
    private final BoardService boardService;

    //index페이지
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        List<Notice> latestNotices = noticeService.getLatestNotices(5);
        model.addAttribute("latestNotices", latestNotices);

        List<Question> latestQuestions = questionService.getLatestQuestion(5);
        model.addAttribute("latestQuestions", latestQuestions);

        //model.addAttribute("url", request.getRequestURI());
        //System.out.println("url" + request.getRequestURI());

        // 리스트 조회 (실수한 게시판)
        // List<Board> latestBoards = boardService.getLatestBoards(5);
        // model.addAttribute("latestBoards", latestBoards);

        // 세션이 없으면
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "index";
        }
        // 세션에 회원 데이터가 없으면
        Object loginMember = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Object loginAdmin = session.getAttribute(SessionConst.LOGIN_ADMIN);

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);


        return "index";
    }

    @GetMapping("/admin")
    public String adminHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "index";
        }
        Object loginAdmin = session.getAttribute(SessionConst.LOGIN_ADMIN);
        if(loginAdmin == null) {
            return "index";
        }
        model.addAttribute("loginAdmin", loginAdmin);
        return "admin/adminHome";
    }
}
