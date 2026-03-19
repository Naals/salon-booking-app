package com.project.bookingservice.mapper;

import com.project.bookingservice.modal.Booking;
import com.project.bookingservice.payload.dto.BookingDto;

public class BookingMapper {

    public static BookingDto toDto(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setSalonId(booking.getSalonId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStartTime(booking.getStartTime());
        bookingDto.setEndTime(booking.getEndTime());
        bookingDto.setServiceIds(booking.getServiceIds());

        return bookingDto;
    }
}
