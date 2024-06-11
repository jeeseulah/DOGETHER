package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Comment;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.dto.AnswerForm;
import com.example.dogether.dto.CommentForm;
import com.example.dogether.service.BoardService;
import com.example.dogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {
    public final BoardService boardService;
    public final CommentService commentService;

    //댓글 저장
    @PostMapping("/community/board/comment_create/{boardid}")
    public String commentCreate(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @PathVariable("boardid") Long boardid, CommentForm commentForm, Model model){
        model.addAttribute("loginMember", loginMember);

        Board board = this.boardService.getBoard(boardid);
        model.addAttribute("board",board);

        Comment comment = new Comment();
        comment.setAuthor(loginMember);
        comment.setContent(commentForm.getContent());
        comment.setCreateDate(LocalDateTime.now());
        comment.setBoard(board);

        commentService.create(comment);
        return String.format("redirect:/community/board/board_detail/%s", boardid);
    }

    //답변 수정
    @GetMapping("/community/board/comment_modify/{id}")
    public String commentModifyForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, CommentForm commentForm, Model model, @PathVariable("id") Long id){
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("commentForm", commentForm);

        Comment comment = this.commentService.getComment(id);
        commentForm.setContent(comment.getContent());

        Board board = this.boardService.getBoard(comment.getBoard().getId());
        model.addAttribute("board", board);
        model.addAttribute("commentForm", commentForm);

        model.addAttribute("urlType","comment_modify");
        model.addAttribute("actionId",id);

        return "community/board/board_detail";
    }

    @PostMapping("/community/board/comment_modify/{id}")
    public String commentModify(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, CommentForm commentForm, @PathVariable("id") Long id, Model model){
        model.addAttribute("loginMember", loginMember);

        //아이디에 해당하는 답글 조회
        Comment findComment = this.commentService.getComment(id);

        //답글에 해당하는 글 찾아오기
        Board board = this.boardService.getBoard(findComment.getBoard().getId());
        model.addAttribute("board", board);

        Comment comment = new Comment();
        comment.setAuthor(loginMember);
        comment.setId(id);
        comment.setContent(commentForm.getContent());
        comment.setBoard(board);
        comment.setCreateDate(findComment.getBoard().getCreateDate());


        this.commentService.create(comment);

        return String.format("redirect:/community/board/board_detail/%s",findComment.getBoard().getId());
    }

    //댓글 삭제
    @GetMapping("/community/board/comment_delete/{id}")
    public String commentDeleteForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model, @PathVariable("id") Long id){
        model.addAttribute("loginMember", loginMember);

        Comment comment = this.commentService.getComment(id);
        this.commentService.delete(comment);

        return String.format("redirect:/community/board/board_detail/%s",comment.getBoard().getId());
    }
}
