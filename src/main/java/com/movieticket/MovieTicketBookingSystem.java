package com.movieticket;

import com.movieticket.model.Booking;
import com.movieticket.model.Movie;
import com.movieticket.model.Theater;
import com.movieticket.service.BookingService;
import com.movieticket.service.MovieService;
import com.movieticket.service.TheaterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for Movie Ticket Booking System
 */
public class MovieTicketBookingSystem {
    private MovieService movieService;
    private TheaterService theaterService;
    private BookingService bookingService;
    private Scanner scanner;

    public MovieTicketBookingSystem() {
        this.movieService = new MovieService();
        this.theaterService = new TheaterService();
        this.bookingService = new BookingService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        MovieTicketBookingSystem system = new MovieTicketBookingSystem();
        system.run();
    }

    public void run() {
        System.out.println("==========================================");
        System.out.println("   MOVIE TICKET BOOKING SYSTEM");
        System.out.println("==========================================");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    viewTheaters();
                    break;
                case 3:
                    bookTicket();
                    break;
                case 4:
                    viewBooking();
                    break;
                case 5:
                    cancelBooking();
                    break;
                case 6:
                    viewSeatLayout();
                    break;
                case 7:
                    running = false;
                    System.out.println("\nThank you for using Movie Ticket Booking System!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. View Available Movies");
        System.out.println("2. View Theaters");
        System.out.println("3. Book Ticket");
        System.out.println("4. View Booking Details");
        System.out.println("5. Cancel Booking");
        System.out.println("6. View Seat Layout");
        System.out.println("7. Exit");
        System.out.println("===============================");
    }

    private void viewMovies() {
        System.out.println("\n========== AVAILABLE MOVIES ==========");
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            for (Movie movie : movies) {
                System.out.println(movie);
                System.out.println("----------------------------------------");
            }
        }
        waitForEnter();
    }

    private void viewTheaters() {
        System.out.println("\n========== THEATERS ==========");
        List<Theater> theaters = theaterService.getAllTheaters();
        if (theaters.isEmpty()) {
            System.out.println("No theaters available.");
        } else {
            for (Theater theater : theaters) {
                System.out.println("Theater ID: " + theater.getTheaterId());
                System.out.println("Name: " + theater.getTheaterName());
                System.out.println("Total Seats: " + theater.getTotalSeats());
                System.out.println("Available Seats: " + theater.getAvailableSeats());
                System.out.println("----------------------------------------");
            }
        }
        waitForEnter();
    }

    private void bookTicket() {
        System.out.println("\n========== BOOK TICKET ==========");
        
        // Select Movie
        viewMovies();
        int movieId = getIntInput("\nEnter Movie ID: ");
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("Invalid Movie ID!");
            waitForEnter();
            return;
        }

        // Select Theater
        viewTheaters();
        int theaterId = getIntInput("\nEnter Theater ID: ");
        Theater theater = theaterService.getTheaterById(theaterId);
        if (theater == null) {
            System.out.println("Invalid Theater ID!");
            waitForEnter();
            return;
        }

        // Display seat layout
        bookingService.displaySeatLayout(theater);

        // Get customer details
        System.out.println("\nEnter Customer Details:");
        String customerName = getStringInput("Name: ");
        String customerEmail = getStringInput("Email: ");
        String customerPhone = getStringInput("Phone: ");

        // Select seats
        System.out.println("\nEnter seat numbers (comma-separated, e.g., A01,A02,B05): ");
        String seatsInput = scanner.nextLine().trim();
        String[] seatArray = seatsInput.split(",");
        List<String> seatNumbers = new ArrayList<>();
        for (String seat : seatArray) {
            seatNumbers.add(seat.trim().toUpperCase());
        }

        // Create booking
        Booking booking = bookingService.createBooking(customerName, customerEmail, 
                                                       customerPhone, movie, theater, seatNumbers);
        
        if (booking != null) {
            System.out.println("\n========== BOOKING CONFIRMED ==========");
            System.out.println(booking);
            System.out.println("\nBooking ID: " + booking.getBookingId() + 
                             " - Please save this for future reference!");
        } else {
            System.out.println("\nBooking failed! Some seats may be already booked or invalid.");
        }
        waitForEnter();
    }

    private void viewBooking() {
        System.out.println("\n========== VIEW BOOKING ==========");
        int bookingId = getIntInput("Enter Booking ID: ");
        Booking booking = bookingService.getBookingById(bookingId);
        
        if (booking != null) {
            System.out.println("\n" + booking);
        } else {
            System.out.println("\nBooking not found!");
        }
        waitForEnter();
    }

    private void cancelBooking() {
        System.out.println("\n========== CANCEL BOOKING ==========");
        int bookingId = getIntInput("Enter Booking ID to cancel: ");
        
        if (bookingService.cancelBooking(bookingId)) {
            System.out.println("\nBooking cancelled successfully!");
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking != null) {
                System.out.println("Refund Amount: ₹" + String.format("%.2f", booking.getTotalAmount()));
            }
        } else {
            System.out.println("\nCancellation failed! Booking not found or already cancelled.");
        }
        waitForEnter();
    }

    private void viewSeatLayout() {
        System.out.println("\n========== VIEW SEAT LAYOUT ==========");
        viewTheaters();
        int theaterId = getIntInput("\nEnter Theater ID: ");
        Theater theater = theaterService.getTheaterById(theaterId);
        
        if (theater != null) {
            bookingService.displaySeatLayout(theater);
        } else {
            System.out.println("Invalid Theater ID!");
        }
        waitForEnter();
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}

