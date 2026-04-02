package com.project.notificationservice.mapper;

import com.project.notificationservice.modal.Notification;
import com.project.notificationservice.payload.dto.BookingDto;
import com.project.notificationservice.payload.dto.NotificationDto;

public class NotificationMapper {


    public static NotificationDto toDto(Notification notification, BookingDto bookingDto) {

        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }

        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setType(notification.getType());
        dto.setIsRead(notification.getIsRead());
        dto.setDescription(notification.getDescription());
        dto.setUserId(notification.getUserId());
        dto.setBookingId(notification.getBookingId());
        dto.setSalonId(notification.getSalonId());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setBookingDto(bookingDto);

        return dto;
    }

    private NotificationMapper() {
    }
}
