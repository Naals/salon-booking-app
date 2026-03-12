package com.project.salonservice.service.impl;

import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;
import com.project.salonservice.payload.dto.UserDto;
import com.project.salonservice.service.SalonService;

import java.util.List;

public class SalonServiceImpl implements SalonService {


    @Override
    public Salon createSalon(SalonDto salon, UserDto user) {
        return null;
    }

    @Override
    public Salon updateSalon(SalonDto salon, UserDto user, Long salonId) {
        return null;
    }

    @Override
    public List<Salon> getAllSalons() {
        return List.of();
    }

    @Override
    public Salon getSalonById(Long salonId) {
        return null;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return List.of();
    }
}
