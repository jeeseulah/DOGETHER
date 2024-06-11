package com.example.dogether.repository;

import com.example.dogether.domain.PetDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetDBRepository extends JpaRepository<PetDB, Long> {
    List<PetDB> findBySidoAndSigunguAndDongeubAndCategory2AndCategory3 (String sido, String sigungu, String dongeub, String category2, String category3);

    @Query("SELECT DISTINCT p.sigungu FROM PetDB p WHERE p.sido = :siDo")
    List<String> findSigunguBySido(@Param("siDo") String siDo);

    @Query("SELECT DISTINCT p.dongeub FROM PetDB p WHERE p.sido = :siDo AND p.sigungu = :siGunGu")
    List<String> findDongeubBySidoAndSigungu(@Param("siDo") String siDo, @Param("siGunGu") String siGunGu);

    //목록조회
    Page<PetDB> findAll(Pageable pageable);
    Page<PetDB> findAll(Specification<PetDB> spec, Pageable pageable);

}
