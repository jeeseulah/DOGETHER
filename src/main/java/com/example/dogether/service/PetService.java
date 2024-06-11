package com.example.dogether.service;

import com.example.dogether.domain.member.Diary;
import com.example.dogether.domain.member.Member;
import com.example.dogether.domain.member.Pet;
import com.example.dogether.dto.PetForm;
import com.example.dogether.repository.MemberRepository;
import com.example.dogether.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PetService(PetRepository petRepository, MemberRepository memberRepository) {
        this.petRepository = petRepository;
        this.memberRepository = memberRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    //로그인 멤버
    public List<Pet> petMemberId(Long memberId) {
        return petRepository.findByMemberId(memberId);
    }

    //펫추가
    public void addPet(PetForm petForm) {
        Pet pet = new Pet();
        pet.setName(petForm.getName());
        pet.setKinds(petForm.getKinds());
        pet.setGender(petForm.getGender());
        pet.setBirth(petForm.getBirth());
        pet.setContent(petForm.getContent());
        pet.setImageUrl(petForm.getImageUrl());

        Member member = memberRepository.findById(petForm.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        pet.setMember(member);

        petRepository.save(pet);
    }

    //삭제
    public void deletePet(Pet pet) {
        petRepository.delete(pet);
    }

    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
}