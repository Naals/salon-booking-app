package com.project.notificationservice.service;

import com.project.notificationservice.modal.Notification;
import com.project.notificationservice.payload.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    NotificationDto createNotification(Notification notification);
    List<NotificationDto> getAllNotificationByUserId(Long userId);
    List<NotificationDto> getAllNotificationBySalonId(Long salonId);
    NotificationDto markNotificationAsRead(Long notificationId);

}
