package com.wd225.online_movie_ticket_reservation_system.model;

// Inheritance: ODCTicket extends Booking
public class ODCTicket extends Booking {

    public ODCTicket() { super(); }

    public ODCTicket(String bookingId, String userId, String movieId,
                     String seatId, String showtime) {
        super(bookingId, userId, movieId, seatId, showtime, "ODC");
    }

    // Polymorphism: overrides displayDetails() with ODC-specific info
    @Override
    public String displayDetails() {
        return "[ODC TICKET] " + super.displayDetails() + " | Open deck cinema experience";
    }
}
