package com.project.salonservice.mapper;

import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;

public class SalonMapper {

    private SalonMapper() {}

    public static SalonDto toDto(Salon salon) {
        if (salon == null) {
            return null;
        }

        SalonDto dto = new SalonDto();
        dto.setId(salon.getId());
        dto.setName(salon.getName());
        dto.setImages(salon.getImages());
        dto.setAddress(salon.getAddress());
        dto.setPhoneNumber(salon.getPhoneNumber());
        dto.setEmail(salon.getEmail());
        dto.setCity(salon.getCity());
        dto.setOwnerId(salon.getOwnerId());
        dto.setOpenTime(salon.getOpenTime());
        dto.setCloseTime(salon.getCloseTime());

        return dto;
    }

    public static Salon toEntity(SalonDto dto) {
        if (dto == null) {
            return null;
        }

        Salon salon = new Salon();
        salon.setName(dto.getName());
        salon.setImages(dto.getImages());
        salon.setAddress(dto.getAddress());
        salon.setPhoneNumber(dto.getPhoneNumber());
        salon.setEmail(dto.getEmail());
        salon.setCity(dto.getCity());
        salon.setOwnerId(dto.getOwnerId());
        salon.setOpenTime(dto.getOpenTime());
        salon.setCloseTime(dto.getCloseTime());

        return salon;
    }
}
