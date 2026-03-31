package com.project.bookingservice.service.client;


import com.project.bookingservice.domain.PaymentMethod;
import com.project.bookingservice.payload.dto.BookingDto;
import com.project.bookingservice.payload.response.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentFeignClient {

    @PostMapping("/create")
    ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDto booking,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
    );

}

