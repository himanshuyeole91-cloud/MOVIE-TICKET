# Movie Ticket Booking System

## Student Information

- **Name:** Himanshu Yeole
- **Roll Number:** 52
- **Batch:** A3
- **Registration Number:** 22070029

## Project Overview

A complete Movie Ticket Booking System with both backend (Java) and frontend (HTML/CSS/JavaScript) implementations.

## Frontend Features

The frontend provides a modern, responsive web interface with the following features:

### 🎬 Features

1. **View Available Movies**
   - Browse all available movies with details (genre, duration, rating)
   - Beautiful card-based layout

2. **View Theaters**
   - See all theaters with location and seat availability
   - Real-time seat count updates

3. **Book Tickets**
   - Select movie and theater
   - Interactive seat selection with visual seat layout
   - Customer information form
   - Real-time seat availability tracking

4. **My Bookings**
   - Search bookings by Booking ID
   - View complete booking details
   - Cancel bookings (frees up seats)

### 🎨 UI/UX Features

- Modern gradient design with smooth animations
- Responsive layout (works on desktop, tablet, and mobile)
- Interactive seat selection with color-coded status
- Toast notifications for user feedback
- Modal dialogs for detailed views
- Smooth transitions and hover effects

## How to Run

### Frontend

1. Simply open `index.html` in any modern web browser
2. No server required - works as a standalone application
3. All data is stored in browser memory (refreshes on page reload)

### Backend (Java)

1. Compile the Java files:
   ```bash
   javac -d target/classes src/main/java/com/movieticket/**/*.java
   ```

2. Run the application:
   ```bash
   java -cp target/classes com.movieticket.MovieTicketBookingSystem
   ```

## File Structure

```
AOLE/
├── index.html          # Main HTML file
├── styles.css          # Styling and layout
├── script.js           # JavaScript functionality
├── README.md           # This file
└── src/                # Java source files
    └── main/java/com/movieticket/
        ├── MovieTicketBookingSystem.java
        ├── model/
        │   ├── Movie.java
        │   ├── Theater.java
        │   ├── Booking.java
        │   └── Seat.java
        └── service/
            ├── MovieService.java
            ├── TheaterService.java
            └── BookingService.java
```

## Technologies Used

### Frontend
- HTML5
- CSS3 (with modern features like Grid, Flexbox, Animations)
- Vanilla JavaScript (ES6+)

### Backend
- Java
- Object-Oriented Programming

## Usage Instructions

1. **Viewing Movies**: Click on the "Movies" tab to see all available movies
2. **Viewing Theaters**: Click on the "Theaters" tab to see theater information
3. **Booking a Ticket**:
   - Go to "Book Ticket" tab
   - Select a movie and theater
   - Fill in customer details
   - Click on available seats (green) to select them
   - Click "Confirm Booking"
4. **Viewing Bookings**: 
   - Go to "My Bookings" tab
   - Enter your Booking ID and click "Search"
   - View details or cancel if needed

## Notes

- The frontend uses sample data that resets on page refresh
- For production use, connect the frontend to a backend API
- Seat availability is tracked in real-time during the session
