package com.example.dogether.controller;

import com.example.dogether.domain.PetDB;
import com.example.dogether.repository.PetDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final PetDBRepository petDBRepository;

    @GetMapping("/api/locations")
    public ResponseEntity<List<String>> getLocations(
            @RequestParam(name="siDo", required = false) String siDo
            , @RequestParam(name="siGunGu", required = false) String siGunGu) {
        if (siDo != null && siGunGu == null) {
            List<String> siGunGuList = petDBRepository.findSigunguBySido(siDo);
            return ResponseEntity.ok(siGunGuList);
        } else if (siDo != null && siGunGu != null) {
            List<String> dongEubList = petDBRepository.findDongeubBySidoAndSigungu(siDo, siGunGu);
            return ResponseEntity.ok(dongEubList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/hospital")
    public ResponseEntity<List<PetDB>> getHospitals(
            @RequestParam(name="siDo", required = false) String siDo
            , @RequestParam(name="siGunGu", required = false) String siGunGu
            , @RequestParam(name="dongEub", required = false) String dongEub) {
        List<PetDB> hospitals = petDBRepository.findBySidoAndSigunguAndDongeubAndCategory2AndCategory3(siDo, siGunGu, dongEub, "반려의료", "동물병원");
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/api/drugstore")
    public ResponseEntity<List<PetDB>> getDrugstore(
            @RequestParam(name="siDo", required = false) String siDo
            , @RequestParam(name="siGunGu", required = false) String siGunGu
            , @RequestParam(name="dongEub", required = false) String dongEub) {
        List<PetDB> drugstores = petDBRepository.findBySidoAndSigunguAndDongeubAndCategory2AndCategory3(siDo, siGunGu, dongEub, "반려의료", "동물약국");
        return ResponseEntity.ok(drugstores);
    }

    @GetMapping("/api/puppyshop")
    public ResponseEntity<List<PetDB>> getPuppyShop(
            @RequestParam(name="siDo", required = false) String siDo
            , @RequestParam(name="siGunGu", required = false) String siGunGu
            , @RequestParam(name="dongEub", required = false) String dongEub) {
        List<PetDB> puppyshops = petDBRepository.findBySidoAndSigunguAndDongeubAndCategory2AndCategory3(siDo, siGunGu, dongEub, "반려동물 서비스", "반려동물용품");
        return ResponseEntity.ok(puppyshops);
    }

    @GetMapping("/api/cafe")
    public ResponseEntity<List<PetDB>> getCafe(
            @RequestParam(name="siDo", required = false) String siDo
            , @RequestParam(name="siGunGu", required = false) String siGunGu
            , @RequestParam(name="dongEub", required = false) String dongEub) {
        List<PetDB> cafes = petDBRepository.findBySidoAndSigunguAndDongeubAndCategory2AndCategory3(siDo, siGunGu, dongEub, "반려동물식당카페", "카페");
        return ResponseEntity.ok(cafes);
    }
}
