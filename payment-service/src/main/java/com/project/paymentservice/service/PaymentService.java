package com.project.paymentservice.service;

import com.project.paymentservice.domain.PaymentMethod;
import com.project.paymentservice.modal.PaymentOrder;
import com.project.paymentservice.payload.dto.BookingDto;
import com.project.paymentservice.payload.dto.UserDto;
import com.project.paymentservice.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDto user,
                                    BookingDto booking,
                                    PaymentMethod paymentMethod) throws StripeException;

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);


    String createStripePaymentLink(UserDto user,
                                   Long amount,
                                   Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId,
                           String paymentLinkId);
}
