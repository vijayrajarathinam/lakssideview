package com.lakesideview.hotel.controller;

import com.lakesideview.hotel.exception.InvalidBookingRequestException;
import com.lakesideview.hotel.exception.ResourceNotFoundException;
import com.lakesideview.hotel.model.Booking;
import com.lakesideview.hotel.model.Room;
import com.lakesideview.hotel.response.BookingResponse;
import com.lakesideview.hotel.response.RoomResponse;
import com.lakesideview.hotel.service.BookingService;
import com.lakesideview.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllBooking(){
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(Booking booking: bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    private BookingResponse getBookingResponse(Booking booking) {
        Room room  = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice()
        );

        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdult(),
                booking.getNumOfChildren(),
                booking.getTotalNumberOfGuest(),
                booking.getBookingConfCode(),
                roomResponse
        );

    }

    @PostMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            Booking booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<?> saveBooking(
            @PathVariable("roomId") Long roomId,
            @RequestBody Booking bookingRequest
    ){
        try {
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room booked successfully. Your booking confirmation code is "+ confirmationCode);
        }catch(InvalidBookingRequestException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
