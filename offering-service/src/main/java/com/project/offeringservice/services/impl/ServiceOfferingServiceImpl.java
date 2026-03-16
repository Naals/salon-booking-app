package com.project.offeringservice.services.impl;

import com.project.offeringservice.mapper.ServiceMapper;
import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.CategoryDto;
import com.project.offeringservice.payload.dto.SalonDto;
import com.project.offeringservice.payload.dto.ServiceDto;
import com.project.offeringservice.repository.ServiceOfferingRepository;
import com.project.offeringservice.services.ServiceOfferingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createServiceOffering(SalonDto salonDto, ServiceDto serviceDto, CategoryDto categoryDto) {
        ServiceOffering serviceOffering = ServiceMapper.toEntity(serviceDto, salonDto, categoryDto);
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) {
        ServiceOffering service = serviceOfferingRepository.findById(serviceId).orElseThrow(
                () -> new EntityNotFoundException("Service with id " + serviceId + " not found")
        );

        service.setImage(service.getImage());
        service.setName(service.getName());
        service.setDescription(service.getDescription());
        service.setPrice(service.getPrice());
        service.setDuration(service.getDuration());

        return serviceOfferingRepository.save(service);
    }

    @Override
    public Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> serviceOfferings = serviceOfferingRepository.findBySalonId(salonId);
        if(categoryId != null) {
            serviceOfferings.stream()
                    .filter(serviceOffering -> Objects.equals(serviceOffering.getCategoryId(), categoryId))
                    .toList();
        }
        return serviceOfferings;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        return null;
    }
}
