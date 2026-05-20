package com.wd225.online_movie_ticket_reservation_system.controller;

import com.wd225.online_movie_ticket_reservation_system.model.Seat;
import com.wd225.online_movie_ticket_reservation_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller for Seat Management
 * Handles all HTTP requests for seat operations
 */
@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "*") // Allow requests from frontend
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * GET /api/seats - Get all seats
     * Optional query param: screenId to filter by screen
     */
    @GetMapping
    public ResponseEntity<?> getAllSeats(
            @RequestParam(required = false) String screenId) {
        try {
            List<Seat> seats;
            if (screenId != null && !screenId.isEmpty()) {
                seats = seatService.getSeatsByScreen(screenId);
            } else {
                seats = seatService.getAllSeats();
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(seats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error retrieving seats: " + e.getMessage()));
        }
    }

    /**
     * GET /api/seats/{id} - Get a specific seat by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeatById(@PathVariable String id) {
        try {
            Seat seat = seatService.getSeatById(id);
            if (seat == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Seat not found: " + id));
            }
            return ResponseEntity.ok(seat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error retrieving seat: " + e.getMessage()));
        }
    }

    /**
     * POST /api/seats - Create a new seat
     */
    @PostMapping
    public ResponseEntity<?> createSeat(@RequestBody Seat seat) {
        try {
            // Validate required fields
            if (seat.getSeatId() == null || seat.getSeatId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Seat ID is required"));
            }
            if (seat.getScreenId() == null || seat.getScreenId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Screen ID is required"));
            }
            if (seat.getRowNumber() == null || seat.getRowNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Row number is required"));
            }
            if (seat.getSeatNumber() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Seat number must be greater than 0"));
            }
            if (seat.getSeatType() == null || seat.getSeatType().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Seat type is required"));
            }
            if (seat.getPrice() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Price must be greater than 0"));
            }
            if (seat.getStatus() == null || seat.getStatus().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Status is required"));
            }

            Seat createdSeat = seatService.addSeat(seat);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdSeat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating seat: " + e.getMessage()));
        }
    }

    /**
     * PUT /api/seats/{id} - Update an existing seat
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeat(
            @PathVariable String id,
            @RequestBody Seat updatedSeat) {
        try {
            Seat seat = seatService.updateSeat(id, updatedSeat);
            return ResponseEntity.ok(seat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error updating seat: " + e.getMessage()));
        }
    }

    /**
     * DELETE /api/seats/{id} - Delete a seat
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeat(@PathVariable String id) {
        try {
            seatService.deleteSeat(id);
            return ResponseEntity.ok(Map.of("message", "Seat deleted successfully: " + id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error deleting seat: " + e.getMessage()));
        }
    }

    /**
     * GET /api/seats/screen/{screenId} - Get all seats for a specific screen
     */
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<?> getSeatsByScreen(@PathVariable String screenId) {
        try {
            List<Seat> seats = seatService.getSeatsByScreen(screenId);
            return ResponseEntity.ok(seats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error retrieving seats for screen: " + e.getMessage()));
        }
    }
}
