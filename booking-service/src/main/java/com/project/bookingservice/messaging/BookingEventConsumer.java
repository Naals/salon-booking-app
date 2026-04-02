package com.project.bookingservice.messaging;

import com.project.bookingservice.modal.PaymentOrder;
import com.project.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener(PaymentOrder paymentOrder) {
        bookingService.bookingSuccess(paymentOrder);
    }


}
