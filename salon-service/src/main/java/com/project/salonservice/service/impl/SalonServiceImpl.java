package com.project.salonservice.service.impl;

import com.project.salonservice.mapper.SalonMapper;
import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;
import com.project.salonservice.payload.dto.UserDto;
import com.project.salonservice.repository.SalonRepository;
import com.project.salonservice.service.SalonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDto salonDto, UserDto user) {
        Salon salon = SalonMapper.toEntity(salonDto);
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDto salon, UserDto user, Long salonId) {
        Salon existingSalon = salonRepository.findById(salonId).orElseThrow(EntityNotFoundException::new);

        if(existingSalon.getOwnerId().equals(salon.getOwnerId())) {
            existingSalon = SalonMapper.toEntity(salon);
            return salonRepository.save(existingSalon);
        }
        throw new EntityNotFoundException("You do not own this salon");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) {
        return salonRepository.findById(salonId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalon(city);
    }
}
