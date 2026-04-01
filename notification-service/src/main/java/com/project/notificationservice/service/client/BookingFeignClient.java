package com.project.notificationservice.service.client;

import com.project.notificationservice.payload.dto.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOKING_SERVICE")
public interface BookingFeignClient {

    @GetMapping("/api/bookings/{bookingId}")
    ResponseEntity<BookingDto> getBookingById(
            @PathVariable Long bookingId
    );
}
