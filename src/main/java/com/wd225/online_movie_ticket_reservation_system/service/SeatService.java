package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.Seat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for Seat Management
 * Handles all CRUD operations and file I/O
 */
@Service
public class SeatService {

    @Value("${seat.file.path:seats.txt}")
    private String filePath;

    /**
     * Get the file path, with fallback to default
     */
    private String getFilePath() {
        if (filePath == null || filePath.isEmpty()) {
            return "data/txt/seats.txt";
        }
        return filePath;
    }

    /**
     * Read all seats from file
     */
    public List<Seat> getAllSeats() {
        try {
            Path path = Paths.get(getFilePath());
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }

            return Files.lines(path)
                    .skip(1) // Skip header line
                    .filter(line -> !line.trim().isEmpty())
                    .map(Seat::fromCSV)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading seats file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get seat by ID
     */
    public Seat getSeatById(String seatId) {
        return getAllSeats().stream()
                .filter(seat -> seat.getSeatId().equals(seatId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all seats for a specific screen
     */
    public List<Seat> getSeatsByScreen(String screenId) {
        return getAllSeats().stream()
                .filter(seat -> seat.getScreenId().equals(screenId))
                .collect(Collectors.toList());
    }

    /**
     * Add a new seat
     * Validates:
     * - No duplicate seat (same screenId, row, seatNumber)
     * - Valid seat type (Regular or VIP)
     * - Valid price (> 0)
     */
    public Seat addSeat(Seat seat) throws Exception {
        // Validate seat type
        if (!seat.getSeatType().equalsIgnoreCase("Regular") &&
                !seat.getSeatType().equalsIgnoreCase("VIP")) {
            throw new IllegalArgumentException("Seat type must be 'Regular' or 'VIP'");
        }

        // Validate price
        if (seat.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        // Validate status
        if (!seat.getStatus().equalsIgnoreCase("Available") &&
                !seat.getStatus().equalsIgnoreCase("Booked")) {
            throw new IllegalArgumentException("Status must be 'Available' or 'Booked'");
        }

        // Check for duplicate
        boolean duplicate = getAllSeats().stream()
                .anyMatch(s -> s.getScreenId().equals(seat.getScreenId()) &&
                        s.getRowNumber().equals(seat.getRowNumber()) &&
                        s.getSeatNumber() == seat.getSeatNumber());

        if (duplicate) {
            throw new IllegalArgumentException(
                    "Seat already exists: Screen " + seat.getScreenId() +
                            ", Row " + seat.getRowNumber() +
                            ", Seat " + seat.getSeatNumber());
        }

        // Add seat to file
        List<Seat> seats = getAllSeats();
        seats.add(seat);
        writeSeatsToFile(seats);

        return seat;
    }

    /**
     * Update an existing seat
     * Validates status changes
     */
    public Seat updateSeat(String seatId, Seat updatedSeat) throws Exception {
        List<Seat> seats = getAllSeats();

        Seat existingSeat = seats.stream()
                .filter(s -> s.getSeatId().equals(seatId))
                .findFirst()
                .orElse(null);

        if (existingSeat == null) {
            throw new IllegalArgumentException("Seat not found: " + seatId);
        }

        // Validate status if being changed
        if (updatedSeat.getStatus() != null) {
            if (!updatedSeat.getStatus().equalsIgnoreCase("Available") &&
                    !updatedSeat.getStatus().equalsIgnoreCase("Booked")) {
                throw new IllegalArgumentException("Status must be 'Available' or 'Booked'");
            }
            existingSeat.setStatus(updatedSeat.getStatus());
        }

        // Validate seat type if being changed
        if (updatedSeat.getSeatType() != null) {
            if (!updatedSeat.getSeatType().equalsIgnoreCase("Regular") &&
                    !updatedSeat.getSeatType().equalsIgnoreCase("VIP")) {
                throw new IllegalArgumentException("Seat type must be 'Regular' or 'VIP'");
            }
            existingSeat.setSeatType(updatedSeat.getSeatType());
        }

        // Validate price if being changed
        if (updatedSeat.getPrice() > 0) {
            existingSeat.setPrice(updatedSeat.getPrice());
        }

        // Update other fields if provided
        if (updatedSeat.getRowNumber() != null) {
            existingSeat.setRowNumber(updatedSeat.getRowNumber());
        }
        if (updatedSeat.getSeatNumber() > 0) {
            existingSeat.setSeatNumber(updatedSeat.getSeatNumber());
        }

        writeSeatsToFile(seats);
        return existingSeat;
    }

    /**
     * Delete a seat by ID
     */
    public boolean deleteSeat(String seatId) throws Exception {
        List<Seat> seats = getAllSeats();

        boolean removed = seats.removeIf(s -> s.getSeatId().equals(seatId));

        if (!removed) {
            throw new IllegalArgumentException("Seat not found: " + seatId);
        }

        writeSeatsToFile(seats);
        return true;
    }

    /**
     * Write all seats to file
     * Overwrites the entire file to ensure data consistency
     */
    private void writeSeatsToFile(List<Seat> seats) throws IOException {
        Path path = Paths.get(getFilePath());

        // Create parent directories if they don't exist (only if parent is not null)
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        // Write header and all seats
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write("seatId,screenId,row,seatNumber,type,price,status");
            writer.newLine();

            for (Seat seat : seats) {
                writer.write(seat.toCSV());
                writer.newLine();
            }
        }
    }

    /**
     * Initialize seats file with header if it doesn't exist
     */
    public void initializeSeatsFile() throws IOException {
        Path path = Paths.get(getFilePath());
        if (!Files.exists(path)) {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            writeSeatsToFile(new ArrayList<>());
        }
    }
}
