package com.example.dogether.service;

import com.example.dogether.domain.admin.Admin;
import com.example.dogether.domain.admin.Notice;
import com.example.dogether.exception.DataNotFindException;
import com.example.dogether.repository.NoticeRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
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
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void create(Notice notice) {
        this.noticeRepository.save(notice);
    }

    public void delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }

    public Specification<Notice> search(String searchWord, String searchType) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Notice> notice, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                String likePattern = "%" + searchWord + "%";

                switch (searchType) {
                    case "subject":
                        return cb.like(notice.get("subject"), likePattern);
                    case "content":
                        return cb.like(notice.get("content"), likePattern);
                    case "subjectAndContent":
                    default:
                        return cb.or(cb.like(notice.get("subject"), likePattern)
                                ,cb.like(notice.get("content"), likePattern));
                }
            }
        };
    }

    public Page<Notice> searchNotices(String searchWord, String searchType, Pageable pageable) {
        return noticeRepository.findAll(search(searchWord, searchType), pageable);
    }

    public Page<Notice> getNoticeList(int page, String searchWord, String searchType ) {
        List<Sort.Order> sortList = new ArrayList<>();
        // 일단 id로 정렬함. 후에 수정할 거면 수정
        sortList.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortList));

        if (searchWord == null || searchWord.isEmpty()) {
            return noticeRepository.findAll(pageable);
        } else {
            Specification<Notice> specN = search(searchWord, searchType);
            return noticeRepository.findAll(specN, pageable);
        }
    }

    public Notice getNotice(Long id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if(notice.isPresent()) {
            return notice.get();
        } else {
            throw new DataNotFindException("문제를 찾을 수 없습니다.");
        }
    }

    // index 페이지에서 사용(HomeController)
    public List<Notice> getLatestNotices(int count) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0, count, Sort.by(sorts));

        return noticeRepository.findAll(pageable).getContent();
    }
}
