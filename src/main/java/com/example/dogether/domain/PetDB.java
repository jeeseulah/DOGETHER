package com.example.dogether.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PetDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String facility_name;
    private String category2;
    private String category3;
    private String sido;
    private String sigungu;
    private String dongeub;
    private double lat;
    private double lng;
    //private int postal_code; // 우편 번호 null 존재

    private String road_address; // null 존재
    private String street_address;

    private String tel;
    private String homepage;
    private String day_off;

    private String operate_time; // 운영시간
    private String parking;
    private String price_info;
    private String pet_size;
    private String place_description_info;
    private String pet_add_fee;

}
