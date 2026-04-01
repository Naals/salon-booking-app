package com.project.notificationservice.controller;

import com.project.notificationservice.modal.Notification;
import com.project.notificationservice.payload.dto.NotificationDto;
import com.project.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<NotificationDto> createNotification(
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(notificationService.getAllNotificationByUserId(userId));
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDto>> getNotificationBySalonId(
            @PathVariable Long salonId
    ) {
        return ResponseEntity.ok(notificationService.getAllNotificationBySalonId(salonId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDto> markNotificationAsRead(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(notificationService.markNotificationAsRead(id));
    }
}
