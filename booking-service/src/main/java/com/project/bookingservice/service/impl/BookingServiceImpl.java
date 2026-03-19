package com.project.bookingservice.service.impl;

import com.project.bookingservice.domain.BookingStatus;
import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.modal.SalonReport;
import com.project.bookingservice.payload.dto.SalonDto;
import com.project.bookingservice.payload.dto.ServiceDto;
import com.project.bookingservice.payload.dto.UserDto;
import com.project.bookingservice.payload.request.BookingRequest;
import com.project.bookingservice.repository.BookingRepository;
import com.project.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon, Set<ServiceDto> serviceDtoSet) throws Exception {
        int totalDuration = serviceDtoSet.stream().mapToInt(ServiceDto::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = booking.getEndTime();

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);

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

        return newBooking;
    }

    public Boolean isTimeSlotAvailable(SalonDto salonDto,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime
    ) throws Exception {
        List<Booking> existingBookings = getBookingBySalonId(salonDto.getId());

        LocalDateTime salonOpenTime = salonDto.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDto.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within the working hours");
        }

        for(Booking existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if(bookingStartTime.isBefore(existingBookingEndTime) || bookingEndTime.isAfter(existingBookingStartTime)) {
                throw new Exception("Slot not available, choose different time");
            }

            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception("Slot not available, choose different time");
            }
        }
        return true;
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

