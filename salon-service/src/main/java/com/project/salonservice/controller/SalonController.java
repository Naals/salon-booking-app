package com.project.salonservice.controller;

import com.project.salonservice.mapper.SalonMapper;
import com.project.salonservice.modal.Salon;
import com.project.salonservice.payload.dto.SalonDto;
import com.project.salonservice.payload.dto.UserDto;
import com.project.salonservice.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto salonDto) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Salon salon = salonService.createSalon(salonDto, userDto);
        SalonDto salonDtoResponse = SalonMapper.toDto(salon);

        return ResponseEntity.ok(salonDtoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDto> updateSalon(
            @PathVariable("id") Long salonId,
            @RequestBody SalonDto salonDto
    ) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Salon salon = salonService.updateSalon(salonDto, userDto, salonId);
        SalonDto salonDtoResponse = SalonMapper.toDto(salon);

        return ResponseEntity.ok(salonDtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<SalonDto>> getAllSalons() {
        List<Salon> salons = salonService.getAllSalons();
        return ResponseEntity.ok(salons.stream().map(SalonMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable("id") Long salonId) {
        Salon salon = salonService.getSalonById(salonId);
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDto>> searchSalon(
            @RequestParam("city") String city
    ) {
        List<Salon> salons = salonService.searchSalonByCity(city);
        return ResponseEntity.ok(salons.stream().map(SalonMapper::toDto).toList());
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDto> getSalonByOwnerId() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Salon salon = salonService.getSalonByOwnerId(userDto.getId());
        return ResponseEntity.ok(SalonMapper.toDto(salon));
    }

}
