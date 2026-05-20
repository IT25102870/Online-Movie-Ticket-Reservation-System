package com.wd225.online_movie_ticket_reservation_system.controller;

import com.wd225.online_movie_ticket_reservation_system.model.Booking;
import com.wd225.online_movie_ticket_reservation_system.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // CREATE - Book a ticket
    @PostMapping("/add")
    public String addBooking(@RequestBody Booking booking) {
        return bookingService.addBooking(booking);
    }

    // READ - Get all bookings (Admin)
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // READ - Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    // READ - Get all bookings for a user
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    // READ - Get all bookings for a movie
    @GetMapping("/movie/{movieId}")
    public List<Booking> getBookingsByMovie(@PathVariable String movieId) {
        return bookingService.getBookingsByMovieId(movieId);
    }

    // UPDATE - Modify booking (seat, showtime)
    @PutMapping("/update/{id}")
    public String updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    // DELETE - Cancel a booking
    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id) {
        return bookingService.deleteBooking(id);
    }
}
