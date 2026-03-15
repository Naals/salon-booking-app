package com.project.offeringservice.services.impl;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.CategoryDto;
import com.project.offeringservice.payload.dto.SalonDto;
import com.project.offeringservice.payload.dto.ServiceDto;
import com.project.offeringservice.repository.ServiceOfferingRepository;
import com.project.offeringservice.services.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createServiceOffering(SalonDto salonDto, ServiceDto serviceDto, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) {
        return null;
    }

    @Override
    public ServiceOffering getAllServicesBySalonId(Long salonId) {
        return null;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        return Set.of();
    }
}
