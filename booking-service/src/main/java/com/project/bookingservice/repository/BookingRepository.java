package com.project.bookingservice.repository;

import com.project.bookingservice.modal.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findBySalonId(Long salonId);
    List<Booking> findBySalonIdAndStartTimeBetween(
            Long salonId,
            LocalDateTime start,
            LocalDateTime end
    );
}
