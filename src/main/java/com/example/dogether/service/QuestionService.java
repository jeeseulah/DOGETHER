package com.example.dogether.service;

import com.example.dogether.domain.member.Member;
import com.example.dogether.exception.DataNotFindException;
import com.example.dogether.domain.member.Question;
import com.example.dogether.repository.QuestionRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    // 질문 등록하기 (create)
    public void create(Question question) {
        this.questionRepository.save(question);
    }

    // 전체 질문 list 조회하기 (read)
    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }


    // 단건 질문 조회하기 (read)
    public Question getQuestion(Long questionid) {
        Optional<Question> findQuestionId = questionRepository.findById(questionid);
        if(findQuestionId.isPresent()) {
            return findQuestionId.get();
        }
        else {
            throw new DataNotFindException("질문을 찾을 수 없습니다.");
        }
    }

    // 질문 삭제하기 (delete)
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public Specification<Question> search(String kWord, String searchType) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                String likePattern = "%" + kWord + "%";
                switch (searchType) {
                    default:
                    case "member":
                        return cb.like(q.get("author").get("name"), likePattern);
                    case "subject":
                        return cb.like(q.get("subject"), likePattern);

                }
            }
        };
    }

    public Page<Question> searchQuestion(String kWord, String searchType, Pageable pageable) {
        return questionRepository.findAll(search(kWord, searchType), pageable);
    }

    public Page<Question> getList(int page, String kWord, String searchType) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        if (kWord == null || kWord.isEmpty()) {
            return questionRepository.findAll(pageable);
        } else {
            Specification<Question> specQ = search(kWord, searchType);
            return questionRepository.findAll(specQ, pageable);
        }
    }

    // index 페이지에서 사용(HomeController)
    public List<Question> getLatestQuestion(int count) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0, count, Sort.by(sorts));

        return questionRepository.findAll(pageable).getContent();
    }

    public List<Question> getLatestQuestion(int count, Member author) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0, count, Sort.by(sorts));

        return questionRepository.findByAuthor(author, pageable).getContent();
    }
}
