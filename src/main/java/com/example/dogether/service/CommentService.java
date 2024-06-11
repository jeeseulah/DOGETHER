package com.example.dogether.service;

import com.example.dogether.domain.admin.Answer;
import com.example.dogether.domain.member.Comment;
import com.example.dogether.exception.DataNotFindException;
import com.example.dogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //답변 등록
    public void create(Comment comment){
        this.commentRepository.save(comment);
    }

    //답변 조회
    public Comment getComment(Long id){
        Optional<Comment> findComment = commentRepository.findById(id);
        if(findComment.isPresent()) {
            return findComment.get();
        }
        else {
            throw new DataNotFindException("답글을 찾을 수 없습니다.");
        }
    }

    //답변 삭제
    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }
}
