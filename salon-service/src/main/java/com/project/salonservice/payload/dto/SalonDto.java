package com.project.salonservice.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalonDto {

    private Long id;

    private String name;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;

    private UserDto owner;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}
