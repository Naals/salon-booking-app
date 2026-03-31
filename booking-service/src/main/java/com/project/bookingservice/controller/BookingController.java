package com.project.bookingservice.controller;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.domain.PaymentMethod;
import com.project.bookingservice.mapper.BookingMapper;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.*;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.payload.response.PaymentLinkResponse;
import com.project.bookingservice.service.BookingService;
import com.project.bookingservice.service.client.OfferingServiceFeignClient;
import com.project.bookingservice.service.client.PaymentFeignClient;
import com.project.bookingservice.service.client.SalonFeignClient;
import com.project.bookingservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;
    private final OfferingServiceFeignClient offeringServiceFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDto user = userFeignClient.getUserProfile(jwt).getBody();

        SalonDto salon = salonFeignClient.getSalonById(salonId).getBody();

        Set<ServiceDto> serviceDtoSet = offeringServiceFeignClient.
                getServicesByIds(bookingRequest.getServicesId()).getBody();

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceDtoSet);

        PaymentLinkResponse paymentLinkResponse = paymentFeignClient.
                createPaymentLink(BookingMapper.toDto(booking), paymentMethod, jwt).getBody();

        return ResponseEntity.ok(paymentLinkResponse);
    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingsByCustomerId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDto customer = userFeignClient.getUserProfile(jwt).getBody();

        assert customer != null;
        List<Booking> bookings = bookingService.getBookingsByCustomerId(customer.getId());

        return ResponseEntity.ok(getBookingDtos(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingsBySalonId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        assert salonDto != null;
        List<Booking> bookings = bookingService.getBookingBySalonId(salonDto.getId());

        return ResponseEntity.ok(getBookingDtos(bookings));
    }

    private Set<BookingDto> getBookingDtos(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            @PathVariable Long bookingId
    ) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus bookingStatus
    ) {
        Booking booking = bookingService.updateBooking(bookingId, bookingStatus);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDto>> getBookedSlot(
            @PathVariable Long salonId,
            @PathVariable LocalDate date
    ) {
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
    public ResponseEntity<SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();


        assert salonDto != null;
        SalonReport salonReport = bookingService.getSalonReport(salonDto.getId());
        return ResponseEntity.ok(salonReport);
    }
}
