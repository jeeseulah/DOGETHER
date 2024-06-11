package com.example.dogether.service;

import com.example.dogether.domain.member.Board;
import com.example.dogether.domain.member.Diary;
import com.example.dogether.domain.member.Member;
import com.example.dogether.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //저장
    public void saveDiary(Diary diary) {

        diaryRepository.save(diary);
    }

    //반환
    public List<Diary> diaryList() {
        return diaryRepository.findAll(); // DB에 저장된 다이어리를 검색해서 List<Diary> 형태로 반환
    }

    //로그인 멤버
    public List<Diary> diaryMemberId(Long memberId) {
        return diaryRepository.findByMemberId(memberId);
    }

    //삭제
    public void deleteDiary(Diary diary) {
        this.diaryRepository.delete(diary);
    }

    //삭제
    public Diary findDiaryById(Long id) {
        return diaryRepository.findById(id).orElse(null);
    }
}
