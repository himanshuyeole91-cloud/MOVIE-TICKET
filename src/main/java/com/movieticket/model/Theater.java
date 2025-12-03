package com.movieticket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Theater class represents a theater with seats
 */
public class Theater {
    private int theaterId;
    private String theaterName;
    private int totalRows;
    private int seatsPerRow;
    private List<Seat> seats;

    public Theater(int theaterId, String theaterName, int totalRows, int seatsPerRow) {
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new ArrayList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        char[] rowLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        
        for (int i = 0; i < totalRows; i++) {
            Seat.SeatType seatType;
            // First 2 rows are VIP, next 3 are Premium, rest are Regular
            if (i < 2) {
                seatType = Seat.SeatType.VIP;
            } else if (i < 5) {
                seatType = Seat.SeatType.PREMIUM;
            } else {
                seatType = Seat.SeatType.REGULAR;
            }

            for (int j = 1; j <= seatsPerRow; j++) {
                String seatNumber = rowLabels[i] + String.format("%02d", j);
                seats.add(new Seat(seatNumber, seatType));
            }
        }
    }

    // Getters and Setters
    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Seat getSeatByNumber(String seatNumber) {
        return seats.stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst()
                .orElse(null);
    }

    public int getTotalSeats() {
        return seats.size();
    }

    public int getAvailableSeats() {
        return (int) seats.stream().filter(seat -> !seat.isBooked()).count();
    }
}

