package com.example.dogether.service;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Question;
import com.example.dogether.exception.DataNotFindException;
import com.example.dogether.repository.BoardRepository;
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
public class BoardService {
    private final BoardRepository boardRepository;

    // 등록하기 (create)
    public void create(Board board) {
        this.boardRepository.save(board);
    }

    // 전체 list 조회하기 (read)
    /*public List<Board> getList() {
        return boardRepository.findAll();
    }*/
    public Page<Board> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }

    // 단건 조회하기 (read)
    /*public Optional<Board> getBoard(Long boardid) {
        return boardRepository.findById(boardid);
    }*/

    public Board getBoard(Long boardid) {
        Optional<Board> findBoardId = boardRepository.findById(boardid);
        if(findBoardId.isPresent()) {
            Board board = findBoardId.get();
            board.setViews(board.getViews() + 1);
            this.boardRepository.save(board);
            return board;
        }
        else {
            throw new DataNotFindException("게시글을 찾을 수 없습니다.");
        }
    }

    public Board getBoardBefore(Long boardid) {
        Optional<Board> findBoardId = boardRepository.findById(boardid - 1);
        if(findBoardId.isPresent()) {
            return findBoardId.get();
        }
        else {
            return null;
        }
    }
    public Board getBoardAfter(Long boardid) {
        Optional<Board> findBoardId = boardRepository.findById(boardid + 1);
        if(findBoardId.isPresent()) {
            return findBoardId.get();
        }
        else {
            return null;
        }
    }

    // 삭제하기 (delete)
    public void delete(Board board){
        this.boardRepository.delete(board);
    }

    // 쿼리문 이용을 위한 Specification 사용
    public Specification<Board> search(String kWord, String searchType) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                String likePattern = "%" + kWord + "%";
                switch (searchType) {
                    case "subject":
                        return cb.like(b.get("subject"), likePattern);
                    case "content":
                        return cb.like(b.get("content"), likePattern);
                    case "member":
                        return cb.like(b.get("member").get("name"), likePattern);
                    case "subjectAndContent":
                    default:
                        return cb.or(cb.like(b.get("subject"), likePattern),
                                cb.like(b.get("content"), likePattern));
                }
            }
        };
    }

    public Page<Board> searchBoards(String kWord, String searchType, Pageable pageable) {
        return boardRepository.findAll(search(kWord, searchType), pageable);
    }

    public Page<Board> getList(int page, String kWord, String searchType) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        if (kWord==null || kWord.isEmpty()) {
            return boardRepository.findAll(pageable);
        } else {
        Specification<Board> specB = search(kWord, searchType);
        return boardRepository.findAll(specB, pageable);
        }
    }

    // index 페이지에서 사용(HomeController)
    public List<Board> getLatestBoard(int count) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0, count, Sort.by(sorts));

        return boardRepository.findAll(pageable).getContent();
    }

    public List<Board> getLatestBoard(int count, Member member) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(0, count, Sort.by(sorts));

        return boardRepository.findByMember(member, pageable).getContent();
    }

}
