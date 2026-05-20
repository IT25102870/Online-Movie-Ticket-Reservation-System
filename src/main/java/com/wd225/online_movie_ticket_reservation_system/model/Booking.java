package com.wd225.online_movie_ticket_reservation_system.model;

// Encapsulation: booking details secured with private fields
public class Booking {
    private String bookingId;
    private String userId;
    private String movieId;
    private String seatId;
    private String showtime;
    private String ticketType; // "BALCONY" or "ODC"

    public Booking() {}

    public Booking(String bookingId, String userId, String movieId,
                   String seatId, String showtime, String ticketType) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.seatId = seatId;
        this.showtime = showtime;
        this.ticketType = ticketType;
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getMovieId() { return movieId; }
    public String getSeatId() { return seatId; }
    public String getShowtime() { return showtime; }
    public String getTicketType() { return ticketType; }

    // Setters
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }
    public void setShowtime(String showtime) { this.showtime = showtime; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    // Polymorphism: overridable by BalconyTicket / ODCTicket
    public String displayDetails() {
        return "Booking [" + bookingId + "] User: " + userId
                + " | Movie: " + movieId + " | Seat: " + seatId
                + " | " + showtime + " | Type: " + ticketType;
    }
}
