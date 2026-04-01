package com.project.notificationservice.service.impl;

import com.project.notificationservice.mapper.NotificationMapper;
import com.project.notificationservice.modal.Notification;
import com.project.notificationservice.payload.dto.BookingDto;
import com.project.notificationservice.payload.dto.NotificationDto;
import com.project.notificationservice.repository.NotificationRepository;
import com.project.notificationservice.service.NotificationService;
import com.project.notificationservice.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDto createNotification(Notification notification) {

        Notification savedNotification = notificationRepository.save(notification);

        BookingDto bookingDto = bookingFeignClient.getBookingById(savedNotification.getBookingId()).getBody();

        return NotificationMapper.toDto(savedNotification, bookingDto);
    }

    @Override
    public List<NotificationDto> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).
                stream().
                map( notification1 -> {
                    BookingDto bookingDto = bookingFeignClient.getBookingById(notification1.getBookingId()).getBody();
                    return NotificationMapper.toDto(notification1, bookingDto);
                }).toList();
    }

    @Override
    public List<NotificationDto> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId).
                stream().
                map( notification1 -> {
                    BookingDto bookingDto = bookingFeignClient.getBookingById(notification1.getBookingId()).getBody();
                    return NotificationMapper.toDto(notification1, bookingDto);
                }).toList();
    }

    @Override
    public NotificationDto markNotificationAsRead(Long notificationId) {

        return NotificationMapper.toDto(notificationRepository.findById(notificationId).map(
                notification1 -> {
                    notification1.setIsRead(true);
                    return notificationRepository.save(notification1);
                }
        ).orElseThrow(() -> new RuntimeException("Notification not found")), bookingFeignClient.getBookingById(notificationId).getBody());
    }
}
