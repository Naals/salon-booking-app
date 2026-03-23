package com.project.paymentservice.controller;

import com.project.paymentservice.domain.PaymentMethod;
import com.project.paymentservice.modal.PaymentOrder;
import com.project.paymentservice.payload.dto.BookingDto;
import com.project.paymentservice.payload.dto.UserDto;
import com.project.paymentservice.payload.response.PaymentLinkResponse;
import com.project.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDto booking,
            @RequestParam PaymentMethod paymentMethod
    ) throws StripeException {

        UserDto user = new UserDto();
        user.setFullName("Asik");
        user.setEmail("asik@gmail.com");
        user.setId(1L);

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
