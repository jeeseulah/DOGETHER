package com.example.dogether.controller;

import com.example.dogether.domain.member.Member;
import com.example.dogether.dto.PetForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.dogether.domain.member.Pet;
import com.example.dogether.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Controller
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    //펫프로필 페이지
    @GetMapping("/petProfile")
    public String showPetProfile(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "redirect:/login";
        }

        List<Pet> pets = petService.petMemberId(member.getId());
        model.addAttribute("pets", pets);
        //model.addAttribute("member", member);
        model.addAttribute("loginMember", member);  // 모델에 로그인 멤버 추가
        return "diary/petProfile";
    }

    //펫추가 페이지
    @GetMapping("/petAdd")
    public String showPetAddForm(HttpSession session ,Model model) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("petForm", new PetForm());
        model.addAttribute("kindsList", getKindsList());
        model.addAttribute("gendersList", getGenderList());
        model.addAttribute("loginMember", member);
        return "diary/petAdd";
    }

    private List<String> getKindsList() {
        return Arrays.asList("푸들", "비글", "말티즈", "치와와", "불도그", "보더콜리", "닥스훈트", "저먼 셰퍼드", "골든 리트리버", "시베리안 허스키");
    }

    private List<String> getGenderList() {
        return Arrays.asList("수컷", "암컷");
    }

    //펫 받아오기
    @PostMapping("/petAdd")
    public String petAdd(HttpSession session, @ModelAttribute ("petForm") PetForm petForm, @RequestParam("image") MultipartFile image) {
        //로그인 멤버
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "redirect:/login";
        }

        // 이미지 업로드
        if(!image.isEmpty()) {
             String fileName = image.getOriginalFilename();
             String uploadDir = "src/main/resources/static/upload/";

             try {
                 Path uploadPath = Paths.get(uploadDir);
                 if(!Files.exists(uploadPath)) {
                     Files.createDirectories(uploadPath);
                 }

                try(InputStream inputStream = image.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    throw new IOException("Could not save image file: " + fileName, ioe);
                }

                String imageUrl = "/upload/" + fileName;
                petForm.setImageUrl(imageUrl);

             } catch (IOException e) {
                 e.printStackTrace();
             }
        }

        petForm.setMemberId(member.getId());
        petService.addPet(petForm);

        return "redirect:/petProfile";


        //petForm.setMemberId(37l);
        //petForm.setMemberId(98l);
    }

    //삭제
    @GetMapping("/pet/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Pet deletePet = petService.findPetById(id);
        if(deletePet != null) {
            petService.deletePet(deletePet);
        }
        return "redirect:/petProfile";
    }
}
