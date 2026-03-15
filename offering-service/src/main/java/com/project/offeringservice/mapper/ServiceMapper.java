package com.project.offeringservice.mapper;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.ServiceDto;

public class ServiceMapper {

    private ServiceMapper() {}

    public static ServiceDto toDto(ServiceOffering service) {

        if (service == null) {
            return null;
        }

        ServiceDto dto = new ServiceDto();

        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setDuration(service.getDuration());
        dto.setCategoryId(service.getCategoryId());
        dto.setSalonId(service.getSalonId());
        dto.setImage(service.getImage());

        return dto;
    }

    public static ServiceOffering toEntity(ServiceDto dto) {

        if (dto == null) {
            return null;
        }

        ServiceOffering service = new ServiceOffering();

        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setDuration(dto.getDuration());
        service.setCategoryId(dto.getCategoryId());
        service.setSalonId(dto.getSalonId());
        service.setImage(dto.getImage());

        return service;
    }
}
