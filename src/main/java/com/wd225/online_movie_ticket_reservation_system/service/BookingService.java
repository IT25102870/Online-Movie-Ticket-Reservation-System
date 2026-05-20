package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.Booking;
import com.wd225.online_movie_ticket_reservation_system.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private static final String FILE_NAME = "data/txt/bookings.txt";

    // CSV format: bookingId,userId,movieId,seatId,showtime,ticketType
    private Booking parseLine(String line) {
        String[] p = line.split(",");
        if (p.length >= 6) return new Booking(p[0], p[1], p[2], p[3], p[4], p[5]);
        return null;
    }

    // CREATE - Book a ticket
    public String addBooking(Booking booking) {
        try {
            if (booking.getTicketType() == null || booking.getTicketType().isEmpty()) {
                booking.setTicketType("ODC");
            }
            String data = booking.getBookingId() + "," + booking.getUserId() + ","
                    + booking.getMovieId() + "," + booking.getSeatId() + ","
                    + booking.getShowtime() + "," + booking.getTicketType();
            FileUtil.saveToFile(FILE_NAME, data);
            return "Ticket booked successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // READ ALL - View all bookings (Admin)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                Booking b = parseLine(line);
                if (b != null) bookings.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bookings;
    }

    // READ ONE - Get booking by ID
    public Booking getBookingById(String bookingId) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(bookingId)) return parseLine(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // READ - Search bookings by username/userId
    public List<Booking> getBookingsByUserId(String userId) {
        List<Booking> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 2 && p[1].equals(userId)) {
                    Booking b = parseLine(line);
                    if (b != null) result.add(b);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // READ - Search bookings by movie name/movieId
    public List<Booking> getBookingsByMovieId(String movieId) {
        List<Booking> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 3 && p[2].equals(movieId)) {
                    Booking b = parseLine(line);
                    if (b != null) result.add(b);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // UPDATE - Modify booking details like seat and showtime
    public String updateBooking(String bookingId, Booking updatedBooking) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(bookingId)) {
                    String type = (updatedBooking.getTicketType() != null) ? updatedBooking.getTicketType() : "ODC";
                    updatedLines.add(bookingId + "," + updatedBooking.getUserId() + ","
                            + updatedBooking.getMovieId() + "," + updatedBooking.getSeatId() + ","
                            + updatedBooking.getShowtime() + "," + type);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            if (found) { FileUtil.overwriteFile(FILE_NAME, updatedLines); return "Booking updated!"; }
            return "Booking not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Cancel a booking
    public String deleteBooking(String bookingId) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean removed = lines.removeIf(line -> line.split(",")[0].equals(bookingId));
            FileUtil.overwriteFile(FILE_NAME, lines);
            return removed ? "Booking cancelled!" : "Booking not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
