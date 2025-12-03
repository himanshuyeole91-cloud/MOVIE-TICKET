package com.movieticket.service;

import com.movieticket.model.Booking;
import com.movieticket.model.Movie;
import com.movieticket.model.Seat;
import com.movieticket.model.Theater;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingService handles booking-related operations
 */
public class BookingService {
    private List<Booking> bookings;
    private int nextBookingId;

    public BookingService() {
        this.bookings = new ArrayList<>();
        this.nextBookingId = 1;
    }

    public Booking createBooking(String customerName, String customerEmail, 
                                 String customerPhone, Movie movie, 
                                 Theater theater, List<String> seatNumbers) {
        // Validate seats
        if (!validateSeats(theater, seatNumbers)) {
            return null;
        }

        // Book the seats
        for (String seatNum : seatNumbers) {
            Seat seat = theater.getSeatByNumber(seatNum);
            if (seat != null && !seat.isBooked()) {
                seat.setBooked(true);
            }
        }

        // Create booking
        Booking booking = new Booking(nextBookingId++, customerName, customerEmail, 
                                     customerPhone, movie, theater, seatNumbers);
        bookings.add(booking);
        return booking;
    }

    private boolean validateSeats(Theater theater, List<String> seatNumbers) {
        for (String seatNum : seatNumbers) {
            Seat seat = theater.getSeatByNumber(seatNum);
            if (seat == null) {
                return false; // Seat doesn't exist
            }
            if (seat.isBooked()) {
                return false; // Seat already booked
            }
        }
        return true;
    }

    public Booking getBookingById(int bookingId) {
        return bookings.stream()
                .filter(booking -> booking.getBookingId() == bookingId)
                .findFirst()
                .orElse(null);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public boolean cancelBooking(int bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null && booking.getStatus() == Booking.BookingStatus.CONFIRMED) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            
            // Release the seats
            for (String seatNum : booking.getSeatNumbers()) {
                Seat seat = booking.getTheater().getSeatByNumber(seatNum);
                if (seat != null) {
                    seat.setBooked(false);
                }
            }
            return true;
        }
        return false;
    }

    public void displaySeatLayout(Theater theater) {
        System.out.println("\n=== Seat Layout for " + theater.getTheaterName() + " ===");
        System.out.println("Screen");
        System.out.println("---------------------------------------------------");
        
        List<Seat> seats = theater.getSeats();
        int seatsPerRow = theater.getSeatsPerRow();
        int totalRows = theater.getTotalRows();
        
        // Display seat numbers header
        System.out.print("   ");
        for (int j = 1; j <= seatsPerRow; j++) {
            System.out.printf("%4d", j);
        }
        System.out.println();
        
        // Display seats row by row
        char[] rowLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 0; i < totalRows; i++) {
            System.out.print(rowLabels[i] + "  ");
            for (int j = 0; j < seatsPerRow; j++) {
                int seatIndex = i * seatsPerRow + j;
                if (seatIndex < seats.size()) {
                    Seat seat = seats.get(seatIndex);
                    if (seat.isBooked()) {
                        System.out.print("[X] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                }
            }
            System.out.println();
        }
        
        System.out.println("\nLegend: [ ] = Available, [X] = Booked");
        System.out.println("Seat Types: Rows A-B = VIP, Rows C-E = Premium, Rows F-J = Regular");
    }
}

