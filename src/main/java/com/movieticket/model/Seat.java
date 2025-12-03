package com.movieticket.model;

/**
 * Seat class represents a seat in the theater
 */
public class Seat {
    private String seatNumber;
    private SeatType seatType;
    private boolean isBooked;

    public enum SeatType {
        REGULAR, PREMIUM, VIP
    }

    public Seat(String seatNumber, SeatType seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isBooked = false;
    }

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public String toString() {
        return String.format("Seat: %s | Type: %s | Status: %s", 
                seatNumber, seatType, isBooked ? "Booked" : "Available");
    }
}

