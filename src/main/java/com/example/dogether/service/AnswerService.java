package com.example.dogether.service;

import com.example.dogether.domain.admin.Answer;
import com.example.dogether.exception.DataNotFindException;
import com.example.dogether.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    //답변 등록
    public void create(Answer answer){
        this.answerRepository.save(answer);
    }

    //답변 조회
    public Answer getAnswer(Long answerId){
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        if(findAnswer.isPresent()) {
            return findAnswer.get();
        }
        else {
            throw new DataNotFindException("답글을 찾을 수 없습니다.");
        }
    }

    //답변 삭제
    public void delete(Answer answer){
        this.answerRepository.delete(answer);
    }
}
