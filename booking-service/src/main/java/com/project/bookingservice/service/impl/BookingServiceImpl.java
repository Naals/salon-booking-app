package com.project.bookingservice.service.impl;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.SalonDto;
import com.project.bookingservice.payload.dto.UserDto;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.repository.BookingRepository;
import com.project.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return List.of();
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return List.of();
    }

    @Override
    public Booking getBookingById(Long id) {
        return null;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) {
        return null;
    }

    @Override
    public void deleteBooking(Long bookingId) {

    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {
        return List.of();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        return null;
    }
}

