package com.project.bookingservice.controller;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.mapper.BookingMapper;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.*;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
    ) throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);

        SalonDto salon = new SalonDto();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));

        Set<ServiceDto> serviceDtoSet = new HashSet<>();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(1L);
        serviceDto.setSalonId(salonId);
        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setName("Hair cut for men");

        serviceDtoSet.add(serviceDto);

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceDtoSet);

        return ResponseEntity.ok(booking);
    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingsByCustomerId(
            @RequestParam Long customerId
    ){
        List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);

        return ResponseEntity.ok(getBookingDtos(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingsBySalonId(
            @RequestParam Long salonId
    ){
        List<Booking> bookings = bookingService.getBookingBySalonId(salonId);

        return ResponseEntity.ok(getBookingDtos(bookings));
    }

    private Set<BookingDto> getBookingDtos(List<Booking> bookings){
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            @PathVariable Long bookingId
    ){
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus bookingStatus
    ){
        Booking booking = bookingService.updateBooking(bookingId, bookingStatus);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDto>> getBookedSlot(
            @PathVariable Long salonId,
            @PathVariable LocalDate date
    ){
        List<Booking> bookings = bookingService.getBookingByDate(date, salonId);

        List<BookingSlotDto> bookingSlotDto = bookings.stream()
                .map(booking -> {
                    BookingSlotDto slotDto = new BookingSlotDto();
                    slotDto.setStartTime(booking.getStartTime());
                    slotDto.setEndTime(booking.getEndTime());

                    return slotDto;
                }).toList();

        return ResponseEntity.ok(bookingSlotDto);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(){
        SalonReport salonReport = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(salonReport);
    }
}
