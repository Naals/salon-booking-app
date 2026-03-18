package com.project.bookingservice.service;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.payload.dto.SalonDto;
import com.project.bookingservice.payload.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon);
    List<Booking> getBookingsByCustomerId(Long customerId);
    List<Booking> getBookingBySalonId(Long salonId);
    Booking getBookingById(Long id);
    Booking updateBooking(Long bookingId, BookingStatus status);
    void deleteBooking(Long bookingId);
    List<Booking> getBookingByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}
