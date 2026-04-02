package com.project.notificationservice.messaging;

import com.project.notificationservice.modal.Notification;
import com.project.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void sendNotification(Notification notification) {
        notificationService.createNotification(notification);
    }


}
