package com.project.bookingservice.service.impl;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.PaymentOrder;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.SalonDto;
import com.project.bookingservice.payload.dto.ServiceDto;
import com.project.bookingservice.payload.dto.UserDto;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.repository.BookingRepository;
import com.project.bookingservice.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Transactional
    @Override
    public Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon, Set<ServiceDto> serviceDtoSet) throws Exception {
        int totalDuration = serviceDtoSet.stream().mapToInt(ServiceDto::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        validateTimeSlot(salon, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDtoSet.stream().mapToInt(ServiceDto::getPrice).sum();

        Set<Long> idList = serviceDtoSet.stream().map(ServiceDto::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public void validateTimeSlot(SalonDto salonDto,
                                 LocalDateTime bookingStartTime,
                                 LocalDateTime bookingEndTime) throws IllegalAccessException {

        LocalDateTime openTime = salonDto.getOpenTime()
                .atDate(bookingStartTime.toLocalDate());

        LocalDateTime closeTime = salonDto.getCloseTime()
                .atDate(bookingStartTime.toLocalDate());

        if (bookingStartTime.isBefore(openTime) || bookingEndTime.isAfter(closeTime)) {
            throw new IllegalAccessException("Outside working hours");
        }

        LocalDateTime startOfDay = bookingStartTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = bookingStartTime.toLocalDate().atTime(23, 59);

        List<Booking> bookings =
                bookingRepository.findBySalonIdAndStartTimeBetween(
                        salonDto.getId(), startOfDay, endOfDay
                );

        for (Booking b : bookings) {

            if (b.getStatus() == BookingStatus.CANCELLED) continue;

            if (bookingStartTime.isBefore(b.getEndTime()) &&
                    bookingEndTime.isAfter(b.getStartTime())) {
                throw new IllegalAccessException("Slot not available");
            }
        }
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Booking not found")
        );
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingBySalonId(salonId);

        if(date == null) {
            return allBookings;
        }

        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date) ||
                        isSameDate(booking.getEndTime(), date))
                .toList();
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> allBookings = getBookingBySalonId(salonId);

        int totalEarnings = allBookings.stream().mapToInt(Booking::getTotalPrice).sum();

        int totalBookings = allBookings.size();

        List<Booking> cancelledBookings = allBookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .toList();

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalBookings(totalBookings);
        salonReport.setTotalRefund(totalRefund);
        salonReport.setCancelledBookings(cancelledBookings.size());
        salonReport.setSalonId(salonId);
        return salonReport;

    }

    @Override
    public void bookingSuccess(PaymentOrder paymentOrder) {
        Booking booking = getBookingById(paymentOrder.getBookingId());
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }
}

