package com.lakesideview.hotel.repository;

import com.lakesideview.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByRoomId(Long roomId);

    Booking findByBookingConfirmationCode(String confirmationCode);
}
