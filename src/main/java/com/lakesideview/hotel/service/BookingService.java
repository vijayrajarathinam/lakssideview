package com.lakesideview.hotel.service;


import com.lakesideview.hotel.model.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> getAllBookingsByRoomId(Long roomId);

    List<Booking> getAllBookings();

    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, Booking bookingRequest);

    Booking findByBookingConfirmationCode(String confirmationCode);
}
