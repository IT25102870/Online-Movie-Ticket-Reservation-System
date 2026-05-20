package com.wd225.online_movie_ticket_reservation_system.model;

// Inheritance: BalconyTicket extends Booking
public class BalconyTicket extends Booking {

    public BalconyTicket() { super(); }

    public BalconyTicket(String bookingId, String userId, String movieId,
                         String seatId, String showtime) {
        super(bookingId, userId, movieId, seatId, showtime, "BALCONY");
    }

    // Polymorphism: overrides displayDetails() with balcony-specific info
    @Override
    public String displayDetails() {
        return "[BALCONY TICKET] " + super.displayDetails() + " | Premium elevated seating";
    }
}
