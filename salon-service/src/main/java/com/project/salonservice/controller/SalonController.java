package com.project.salonservice.controller;

import com.project.salonservice.exception.SalonNotFoundException;
import com.project.salonservice.exception.UnauthorizedException;
import com.project.salonservice.mapper.SalonMapper;
import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;
import com.project.salonservice.payload.dto.UserDto;
import com.project.salonservice.service.SalonService;
import com.project.salonservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<SalonDto> createSalon(
            @RequestBody SalonDto salonDto,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (jwt == null || jwt.isBlank()) {
            throw new UnauthorizedException("JWT token is missing");
        }

        UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

        if (userDto == null) {
            throw new UnauthorizedException("User not found");
        }

        Salon salon = salonService.createSalon(salonDto, userDto);
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDto> updateSalon(
            @PathVariable("id") Long salonId,
            @RequestBody SalonDto salonDto,
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (jwt == null || jwt.isBlank()) {
            throw new UnauthorizedException("JWT token is missing");
        }

        UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

        if (userDto == null) {
            throw new UnauthorizedException("User not found");
        }

        Salon salon = salonService.updateSalon(salonDto, userDto, salonId);
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @GetMapping
    public ResponseEntity<List<SalonDto>> getAllSalons() {
        List<Salon> salons = salonService.getAllSalons();

        if (salons == null || salons.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(salons.stream().map(SalonMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDto> getSalonById(
            @PathVariable("id") Long salonId) {

        if (salonId == null || salonId <= 0) {
            throw new IllegalArgumentException("Invalid salon id");
        }

        Salon salon = salonService.getSalonById(salonId);

        if (salon == null) {
            throw new SalonNotFoundException("Salon not found with id: " + salonId);
        }

        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDto>> searchSalon(
            @RequestParam("city") String city) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City parameter is required");
        }

        List<Salon> salons = salonService.searchSalonByCity(city);

        if (salons == null || salons.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(salons.stream().map(SalonMapper::toDto).toList());
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDto> getSalonByOwnerId(
            @RequestHeader("Authorization") String jwt) throws Exception {

        if (jwt == null || jwt.isBlank()) {
            throw new UnauthorizedException("JWT token is missing");
        }

        UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

        if (userDto == null) {
            throw new UnauthorizedException("User not found");
        }

        Salon salon = salonService.getSalonByOwnerId(userDto.getId());

        if (salon == null) {
            throw new SalonNotFoundException("Salon not found for owner: " + userDto.getId());
        }

        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

}
