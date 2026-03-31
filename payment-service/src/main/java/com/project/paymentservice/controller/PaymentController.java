package com.project.paymentservice.controller;

import com.project.paymentservice.domain.PaymentMethod;
import com.project.paymentservice.modal.PaymentOrder;
import com.project.paymentservice.payload.dto.BookingDto;
import com.project.paymentservice.payload.dto.UserDto;
import com.project.paymentservice.payload.response.PaymentLinkResponse;
import com.project.paymentservice.service.PaymentService;
import com.project.paymentservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDto booking,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserDto user = userFeignClient.getUserProfile(jwt).getBody();

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(paymentOrder,
                paymentId,
                paymentLinkId);

        return ResponseEntity.ok(res);
    }


}
