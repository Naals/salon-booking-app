package com.project.offeringservice.services;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.CategoryDto;
import com.project.offeringservice.payload.dto.SalonDto;
import com.project.offeringservice.payload.dto.ServiceDto;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createServiceOffering(SalonDto salonDto, ServiceDto serviceDto,
                                          CategoryDto categoryDto);
    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering);
    Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId);
    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

}
