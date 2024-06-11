package com.example.dogether.controller;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.dto.AnswerForm;
import com.example.dogether.dto.QuestionForm;
import com.example.dogether.service.AnswerService;
import com.example.dogether.service.MemberService;
import com.example.dogether.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    //Q&A페이지
    @GetMapping("/community/question/question_list")
    public String questionListForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            , @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin
            , @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "kWord", defaultValue = "") String kWord
            , @RequestParam(value = "searchType", defaultValue = "") String searchType
            , Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Page<Question> paging = this.questionService.getList(page, kWord, searchType);
        model.addAttribute("paging", paging);
        model.addAttribute("kWord", kWord);
        model.addAttribute("searchType", searchType);
        return "community/question/question_list";
    }

    //Q&A 글쓰기 페이지
    @GetMapping("/community/question/question_create")
    public String questionCreateForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, QuestionForm questionForm, Model model) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("questionForm", questionForm);

        questionForm.setName(loginMember.getName());
        questionForm.setEmail(loginMember.getLoginId());
        questionForm.setTel(loginMember.getTel());
        return "community/question/question_create";
    }

    @PostMapping("/community/question/question_create")
    public String questionCreate(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @Valid @ModelAttribute("questionForm") QuestionForm questionForm, BindingResult bindingResult, Model model){
        model.addAttribute("loginMember", loginMember);

        if((bindingResult.hasErrors() && questionForm.getIsShow()) || !questionForm.getIsAgree()) {
            model.addAttribute("loginMember", loginMember);

            questionForm.setName(loginMember.getName());
            questionForm.setEmail(loginMember.getLoginId());
            questionForm.setTel(loginMember.getTel());
            return "community/question/question_create";
        }

        Question question = new Question();
        question.setAuthor(loginMember);
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setCreateDate(LocalDateTime.now());
        question.setPassword(questionForm.getPassword());
        question.setIsShow(questionForm.getIsShow());
        question.setIsAgree(questionForm.getIsAgree());


        questionService.create(question);
        return "redirect:/community/question/question_list";
    }

    //Q&A 글보기 페이지
    @GetMapping("/community/question/question_detail/{questionid}")
    public String questionDetailForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @SessionAttribute(name=SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, Model model, @PathVariable("questionid") Long questionid, AnswerForm answerForm) {
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("loginAdmin", loginAdmin);

        Question question = this.questionService.getQuestion(questionid);
        model.addAttribute("question",question);
        model.addAttribute("answerForm", answerForm);

        model.addAttribute("urlType","answer_create");
        model.addAttribute("actionId",questionid);

        //Answer answer = this.answerService.getAnswer(questionid);
        //model.addAttribute("answer", answer);

        return "community/question/question_detail";
    }

    //Q&A 수정 페이지
    @GetMapping("/community/question/question_modify/{questionid}")
    public String questionModifyForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, QuestionForm questionForm, Model model, @PathVariable("questionid") Long questionid){
        model.addAttribute("loginMember", loginMember);

        Question question = this.questionService.getQuestion(questionid);

        questionForm.setName(loginMember.getName());
        questionForm.setEmail(loginMember.getLoginId());
        questionForm.setTel(loginMember.getTel());
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());


        return "community/question/question_create";
    }

    @PostMapping("/community/question/question_modify/{questionid}")
    public String questionModify(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @Valid @ModelAttribute("questionForm") QuestionForm questionForm, BindingResult bindingResult, Model model, @PathVariable("questionid") Long questionid){
        model.addAttribute("loginMember", loginMember);

        if((bindingResult.hasErrors() && questionForm.getIsShow()) || !questionForm.getIsAgree()) {
            model.addAttribute("loginMember", loginMember);

            questionForm.setName(loginMember.getName());
            questionForm.setEmail(loginMember.getLoginId());
            questionForm.setTel(loginMember.getTel());
            return "community/question/question_create";
        }

        Question findQuestion = this.questionService.getQuestion(questionid);

        Question question = new Question();
        question.setId(questionid);
        question.setAuthor(loginMember);
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setCreateDate(findQuestion.getCreateDate());
        question.setPassword(questionForm.getPassword());
        question.setIsShow(questionForm.getIsShow());
        question.setIsAgree(questionForm.getIsAgree());


        questionService.create(question);
        return String.format("redirect:/community/question/question_detail/%s",questionid);
    }

    //Q&A 삭제 페이지
    @GetMapping("/community/question/question_delete/{questionid}")
    public String questionDeleteForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model, @PathVariable("questionid") Long questionid){
        model.addAttribute("loginMember", loginMember);

        Question question = this.questionService.getQuestion(questionid);
        this.questionService.delete(question);

        return "redirect:/community/question/question_list";
    }

    //Q&A 비번체크 페이지
    @GetMapping("/community/question/question_pwd/{questionid}")
    public String questionPwdForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember,  QuestionForm questionForm, Model model, @PathVariable("questionid") Long questionid){
        model.addAttribute("loginMember", loginMember);


        Question question = this.questionService.getQuestion(questionid);
        model.addAttribute("question",question);
        model.addAttribute("questionForm", questionForm);

        //Question question = this.questionService.getQuestion(questionid);
        //model.addAttribute("question",question);

        return "community/question/question_pwd";
    }

    @PostMapping("/community/question/question_pwd/{questionid}")
    public String questionPwd(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, @PathVariable("questionid") Long questionid, QuestionForm questionForm, Model model){
        model.addAttribute("loginMember", loginMember);
        Question findquestion = this.questionService.getQuestion(questionid);

        System.out.println(questionForm.getPassword());
        System.out.println(findquestion.getPassword());
        System.out.println(!questionForm.getPassword().equals(findquestion.getPassword()));

        if(!questionForm.getPassword().equals(findquestion.getPassword())) {
            model.addAttribute("loginMember", loginMember);
            String message = "비밀번호가 맞지 않습니다.";
            model.addAttribute("message", message);
            return "community/question/question_pwd";
        }

        return String.format("redirect:/community/question/question_detail/%s",questionid);
    }
}
