package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.dto.AnswerForm;
import com.example.dogether.dto.BoardForm;
import com.example.dogether.dto.CommentForm;
import com.example.dogether.dto.QuestionForm;
import com.example.dogether.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
/*
    //게시판페이지
    @GetMapping("/community/board/board_list")
    public String boardListForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "community/board/board_list";
    }
*/
    //게시판페이지
    @GetMapping("/community/board/board_list")
    public String boardListForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                @SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "kWord", defaultValue = "") String kWord,
                                @RequestParam(value = "searchType", defaultValue = "") String searchType,
                                Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Page<Board> paging = this.boardService.getList(page, kWord, searchType);
        model.addAttribute("paging", paging);
        model.addAttribute("kWord", kWord);
        model.addAttribute("searchType", searchType);
        return "community/board/board_list";
    }
	
    //게시판 글쓰기 페이지
    @GetMapping("/community/board/board_create")
    public String boardCreateForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, BoardForm boardForm, Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("boardForm", boardForm);

        boardForm.setName(loginMember.getName());
        boardForm.setEmail(loginMember.getLoginId());
        return "community/board/board_create";
    }

    @PostMapping("/community/board/board_create")
    public String boardCreate(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @Valid @ModelAttribute("boardForm") BoardForm boardForm, BindingResult bindingResult, Model model){
        model.addAttribute("loginMember", loginMember);

        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);

            boardForm.setName(loginMember.getName());
            boardForm.setEmail(loginMember.getLoginId());
            return "community/board/board_create";
        }

        Board board = new Board();
        board.setMember(loginMember);
        board.setSubject(boardForm.getSubject());
        board.setContent(boardForm.getContent());
        board.setCreateDate(LocalDateTime.now());
        board.setViews(0);


        boardService.create(board);
        return "redirect:/community/board/board_list";
    }

    //게시판 글보기 페이지
    @GetMapping("/community/board/board_detail/{boardid}")
    public String boardDetailForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember,@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin,  Model model, @PathVariable("boardid") Long boardid, CommentForm commentForm) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Board board = this.boardService.getBoard(boardid);
        model.addAttribute("board",board);
        model.addAttribute("commentForm", commentForm);

        Board boardBefore = this.boardService.getBoardBefore(boardid);
        Board boardAfter = this.boardService.getBoardAfter(boardid);
        model.addAttribute("boardBefore", boardBefore);
        model.addAttribute("boardAfter", boardAfter);

        model.addAttribute("urlType","comment_create");
        model.addAttribute("actionId",boardid);

        return "community/board/board_detail";
    }

    //게시판 수정 페이지
    @GetMapping("/community/board/board_modify/{boardid}")
    public String boardModifyForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, BoardForm boardForm, Model model, @PathVariable("boardid") Long boardid) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Board board = this.boardService.getBoard(boardid);

        boardForm.setName(loginMember.getName());
        boardForm.setEmail(loginMember.getLoginId());
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());

        return "community/board/board_create";
    }

    @PostMapping("/community/board/board_modify/{boardid}")
    public String boardModify(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @Valid @ModelAttribute("boardForm") BoardForm boardForm, BindingResult bindingResult, Model model, @PathVariable("boardid") Long boardid) {
        model.addAttribute("loginMember", loginMember);

        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);

            boardForm.setName(loginMember.getName());
            boardForm.setEmail(loginMember.getLoginId());
            return "community/board/board_create";
        }

        Board findBoard = this.boardService.getBoard(boardid);

        Board board = new Board();
        board.setId(boardid);
        board.setMember(loginMember);
        board.setSubject(boardForm.getSubject());
        board.setContent(boardForm.getContent());
        board.setCreateDate(findBoard.getCreateDate());
        board.setViews(findBoard.getViews());

        boardService.create(board);
        return String.format("redirect:/community/board/board_detail/%s",boardid);
    }

    //게시판 삭제 페이지
    @GetMapping("/community/board/board_delete/{boardid}")
    public String boardDeleteForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model, @PathVariable("boardid") Long boardid) {
        model.addAttribute("loginMember", loginMember);

        Board board = this.boardService.getBoard(boardid);
        this.boardService.delete(board);

        return "redirect:/community/board/board_list";
    }
}
