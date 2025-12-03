package com.movieticket.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Booking class represents a ticket booking
 */
public class Booking {
    private int bookingId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Movie movie;
    private Theater theater;
    private List<String> seatNumbers;
    private LocalDateTime bookingTime;
    private double totalAmount;
    private BookingStatus status;

    public enum BookingStatus {
        CONFIRMED, CANCELLED
    }

    public Booking(int bookingId, String customerName, String customerEmail, 
                   String customerPhone, Movie movie, Theater theater, 
                   List<String> seatNumbers) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.movie = movie;
        this.theater = theater;
        this.seatNumbers = new ArrayList<>(seatNumbers);
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        double basePrice = movie.getTicketPrice();
        totalAmount = 0;
        
        for (String seatNum : seatNumbers) {
            Seat seat = theater.getSeatByNumber(seatNum);
            if (seat != null) {
                double seatPrice = basePrice;
                switch (seat.getSeatType()) {
                    case VIP:
                        seatPrice = basePrice * 1.5;
                        break;
                    case PREMIUM:
                        seatPrice = basePrice * 1.25;
                        break;
                    case REGULAR:
                        seatPrice = basePrice;
                        break;
                }
                totalAmount += seatPrice;
            }
        }
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
            "Booking ID: %d\n" +
            "Customer: %s (%s, %s)\n" +
            "Movie: %s\n" +
            "Theater: %s\n" +
            "Seats: %s\n" +
            "Total Amount: ₹%.2f\n" +
            "Status: %s\n" +
            "Booking Time: %s",
            bookingId, customerName, customerEmail, customerPhone,
            movie.getTitle(), theater.getTheaterName(),
            String.join(", ", seatNumbers), totalAmount,
            status, bookingTime.toString()
        );
    }
}

