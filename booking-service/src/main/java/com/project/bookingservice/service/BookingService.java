package com.project.bookingservice.service;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.PaymentOrder;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.ServiceDto;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.payload.dto.SalonDto;
import com.project.bookingservice.payload.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon, Set<ServiceDto> serviceDtoSet) throws Exception;
    List<Booking> getBookingsByCustomerId(Long customerId);
    List<Booking> getBookingBySalonId(Long salonId);
    Booking getBookingById(Long id);
    Booking updateBooking(Long bookingId, BookingStatus status);
    List<Booking> getBookingByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
    void bookingSuccess(PaymentOrder paymentOrder);
}
