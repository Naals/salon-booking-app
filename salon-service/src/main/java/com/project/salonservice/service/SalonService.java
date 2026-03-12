package com.project.salonservice.service;

import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;
import com.project.salonservice.payload.dto.UserDto;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDto salon, UserDto user);

    Salon updateSalon(SalonDto salon, UserDto user, Long salonId);

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId);

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}