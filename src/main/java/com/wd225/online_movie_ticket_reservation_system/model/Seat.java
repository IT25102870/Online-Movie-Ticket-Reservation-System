package com.wd225.online_movie_ticket_reservation_system.model;

/**
 * Seat Entity Class
 * Represents a cinema seat with all necessary attributes
 */
public class Seat {
    private String seatId;           // Unique identifier (e.g., SCREEN1_A1)
    private String screenId;         // Screen/Theater identifier
    private String rowNumber;        // Row letter (A, B, C, etc.)
    private int seatNumber;          // Seat number in the row (1, 2, 3, etc.)
    private String seatType;         // Regular or VIP
    private double price;            // Seat price
    private String status;           // Available or Booked

    // Constructors
    public Seat() {
    }

    public Seat(String seatId, String screenId, String rowNumber, int seatNumber,
                String seatType, double price, String status) {
        this.seatId = seatId;
        this.screenId = screenId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Converts Seat object to CSV format for file storage
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%s,%.2f,%s",
                seatId, screenId, rowNumber, seatNumber, seatType, price, status);
    }

    /**
     * Creates Seat object from CSV line
     */
    public static Seat fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        return new Seat(
                parts[0].trim(),                      // seatId
                parts[1].trim(),                      // screenId
                parts[2].trim(),                      // rowNumber
                Integer.parseInt(parts[3].trim()),    // seatNumber
                parts[4].trim(),                      // seatType
                Double.parseDouble(parts[5].trim()),  // price
                parts[6].trim()                       // status
        );
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId='" + seatId + '\'' +
                ", screenId='" + screenId + '\'' +
                ", rowNumber='" + rowNumber + '\'' +
                ", seatNumber=" + seatNumber +
                ", seatType='" + seatType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
