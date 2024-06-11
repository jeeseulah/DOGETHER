package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.dto.AnswerForm;
import com.example.dogether.dto.QuestionForm;
import com.example.dogether.service.AnswerService;
import com.example.dogether.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    public final QuestionService questionService;
    public final AnswerService answerService;

    //답변 저장
    @PostMapping("/community/question/answer_create/{questionid}")
    public String answerCreate(@SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin,@PathVariable("questionid") Long questionid, AnswerForm answerForm, Model model){
        model.addAttribute("loginAdmin", loginAdmin);

        Question question = this.questionService.getQuestion(questionid);
        model.addAttribute("question",question);

        Answer answer = new Answer();
        answer.setAuthor(loginAdmin);
        answer.setContent(answerForm.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);

        this.answerService.create(answer);
        return String.format("redirect:/community/question/question_detail/%s", questionid);
    }

    //답변 수정
    @GetMapping("/community/question/answer_modify/{answerid}")
    public String answerModifyForm(@SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, AnswerForm answerForm, Model model, @PathVariable("answerid") Long answerid){
        model.addAttribute("loginAdmin", loginAdmin);

        Answer answer = this.answerService.getAnswer(answerid);
        answerForm.setContent(answer.getContent());

        Question question = this.questionService.getQuestion(answer.getQuestion().getId());
        model.addAttribute("question",question);
        model.addAttribute("answerForm", answerForm);

        model.addAttribute("urlType","answer_modify");
        model.addAttribute("actionId",answerid);

        return "community/question/question_detail";
    }

    @PostMapping("/community/question/answer_modify/{id}")
    public String answerModify(@SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, AnswerForm answerForm, @PathVariable("id") Long id, Model model){
        model.addAttribute("loginAdmin", loginAdmin);

        //아이디에 해당하는 답글 조회
        Answer findAnswer = this.answerService.getAnswer(id);

        //답글에 해당하는 질문 찾아오기
        Question question = this.questionService.getQuestion(findAnswer.getQuestion().getId());
        model.addAttribute("question",question);

        Answer answer = new Answer();
        answer.setAuthor(loginAdmin);
        answer.setId(id);
        answer.setContent(answerForm.getContent());
        answer.setQuestion(question);
        answer.setCreateDate(findAnswer.getQuestion().getCreateDate());

        this.answerService.create(answer);

        return String.format("redirect:/community/question/question_detail/%s",findAnswer.getQuestion().getId());
    }

    //답변 삭제
    @GetMapping("/community/question/answer_delete/{id}")
    public String answerDeleteForm(@SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, Model model, @PathVariable("id") Long id){
        model.addAttribute("loginAdmin", loginAdmin);

        Answer answer = this.answerService.getAnswer(id);
        this.answerService.delete(answer);

        return String.format("redirect:/community/question/question_detail/%s",answer.getQuestion().getId());
    }
}
