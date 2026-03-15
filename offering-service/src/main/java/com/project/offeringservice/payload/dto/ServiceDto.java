package com.project.offeringservice.payload.dto;

import lombok.Data;

@Data
public class ServiceDto {

    private Long id;

    private String name;

    private String description;

    private int price;

    private int duration;

    private Long categoryId;

    private Long salonId;

    private String image;

}
