// Sample Data
let movies = [
    { id: 1, title: "The Dark Knight", genre: "Action", duration: "152 min", rating: "9.0" },
    { id: 2, title: "Inception", genre: "Sci-Fi", duration: "148 min", rating: "8.8" },
    { id: 3, title: "Interstellar", genre: "Sci-Fi", duration: "169 min", rating: "8.6" },
    { id: 4, title: "The Matrix", genre: "Action", duration: "136 min", rating: "8.7" },
    { id: 5, title: "Pulp Fiction", genre: "Crime", duration: "154 min", rating: "8.9" }
];

let theaters = [
    { id: 1, name: "CineMax Theater", totalSeats: 100, availableSeats: 85, location: "Downtown" },
    { id: 2, name: "Star Cinema", totalSeats: 150, availableSeats: 120, location: "Mall Area" },
    { id: 3, name: "Royal Theater", totalSeats: 80, availableSeats: 65, location: "City Center" }
];

let bookings = [];
let nextBookingId = 1;
let selectedSeats = [];
let currentTheater = null;
let occupiedSeats = {}; // Store occupied seats per theater

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeNavigation();
    loadMovies();
    loadTheaters();
    populateSelects();
    setupBookingForm();
    setupBookingSearch();
    setupModal();
});

// Navigation
function initializeNavigation() {
    const navButtons = document.querySelectorAll('.nav-btn');
    const sections = document.querySelectorAll('.content-section');

    navButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetSection = btn.getAttribute('data-section');
            
            // Update active states
            navButtons.forEach(b => b.classList.remove('active'));
            sections.forEach(s => s.classList.remove('active'));
            
            btn.classList.add('active');
            document.getElementById(targetSection).classList.add('active');

            // Load seat layout if booking section
            if (targetSection === 'book' && currentTheater) {
                renderSeatLayout(currentTheater);
            }
        });
    });
}

// Load and display movies
function loadMovies() {
    const moviesList = document.getElementById('movies-list');
    moviesList.innerHTML = '';

    if (movies.length === 0) {
        moviesList.innerHTML = '<p style="text-align: center; color: #666; padding: 40px;">No movies available.</p>';
        return;
    }

    movies.forEach(movie => {
        const movieCard = document.createElement('div');
        movieCard.className = 'movie-card';
        movieCard.innerHTML = `
            <h3>${movie.title}</h3>
            <p><strong>Genre:</strong> ${movie.genre}</p>
            <p><strong>Duration:</strong> ${movie.duration}</p>
            <p><strong>Rating:</strong> ⭐ ${movie.rating}</p>
            <span class="genre">${movie.genre}</span>
        `;
        moviesList.appendChild(movieCard);
    });
}

// Load and display theaters
function loadTheaters() {
    const theatersList = document.getElementById('theaters-list');
    theatersList.innerHTML = '';

    if (theaters.length === 0) {
        theatersList.innerHTML = '<p style="text-align: center; color: #666; padding: 40px;">No theaters available.</p>';
        return;
    }

    theaters.forEach(theater => {
        const theaterCard = document.createElement('div');
        theaterCard.className = 'theater-card';
        theaterCard.innerHTML = `
            <h3>${theater.name}</h3>
            <div class="info">
                <strong>Location:</strong> ${theater.location}
            </div>
            <div class="info">
                <strong>Total Seats:</strong> ${theater.totalSeats}
            </div>
            <div class="info">
                <strong>Available Seats:</strong> ${theater.availableSeats}
            </div>
        `;
        theatersList.appendChild(theaterCard);
    });
}

// Populate select dropdowns
function populateSelects() {
    const movieSelect = document.getElementById('movie-select');
    const theaterSelect = document.getElementById('theater-select');

    // Populate movies
    movies.forEach(movie => {
        const option = document.createElement('option');
        option.value = movie.id;
        option.textContent = movie.title;
        movieSelect.appendChild(option);
    });

    // Populate theaters
    theaters.forEach(theater => {
        const option = document.createElement('option');
        option.value = theater.id;
        option.textContent = `${theater.name} - ${theater.location}`;
        theaterSelect.appendChild(option);
    });

    // Update seat layout when theater changes
    theaterSelect.addEventListener('change', function() {
        const theaterId = parseInt(this.value);
        currentTheater = theaters.find(t => t.id === theaterId);
        if (currentTheater) {
            selectedSeats = [];
            renderSeatLayout(currentTheater);
            updateSelectedSeatsDisplay();
        }
    });
}

// Render seat layout
function renderSeatLayout(theater) {
    const container = document.getElementById('seat-layout-container');
    if (!theater) {
        container.innerHTML = '<p style="text-align: center; color: #666;">Please select a theater first.</p>';
        return;
    }

    const rows = Math.ceil(Math.sqrt(theater.totalSeats));
    const seatsPerRow = Math.ceil(theater.totalSeats / rows);
    const theaterKey = `theater-${theater.id}`;
    
    if (!occupiedSeats[theaterKey]) {
        occupiedSeats[theaterKey] = [];
    }

    let html = '<div class="screen">🎬 SCREEN 🎬</div>';
    html += '<div class="seats-grid">';

    let seatNumber = 1;
    for (let row = 0; row < rows; row++) {
        const rowLabel = String.fromCharCode(65 + row); // A, B, C, etc.
        html += '<div class="seat-row">';
        html += `<div class="row-label">${rowLabel}</div>`;
        
        for (let col = 0; col < seatsPerRow && seatNumber <= theater.totalSeats; col++) {
            const seatId = `${rowLabel}${String(col + 1).padStart(2, '0')}`;
            const isOccupied = occupiedSeats[theaterKey].includes(seatId);
            const isSelected = selectedSeats.includes(seatId);
            
            let seatClass = 'seat';
            if (isOccupied) {
                seatClass += ' occupied';
            } else if (isSelected) {
                seatClass += ' selected';
            } else {
                seatClass += ' available';
            }

            html += `<div class="${seatClass}" data-seat="${seatId}">${col + 1}</div>`;
            seatNumber++;
        }
        
        html += '</div>';
    }

    html += '</div>';
    html += `
        <div class="seat-legend">
            <div class="legend-item">
                <div class="legend-seat" style="background: #d4edda; border-color: #28a745;"></div>
                <span>Available</span>
            </div>
            <div class="legend-item">
                <div class="legend-seat" style="background: #667eea; border-color: #667eea;"></div>
                <span>Selected</span>
            </div>
            <div class="legend-item">
                <div class="legend-seat" style="background: #dc3545; border-color: #dc3545; opacity: 0.6;"></div>
                <span>Occupied</span>
            </div>
        </div>
    `;

    container.innerHTML = html;

    // Add click handlers to seats
    container.querySelectorAll('.seat.available, .seat.selected').forEach(seat => {
        seat.addEventListener('click', function() {
            const seatId = this.getAttribute('data-seat');
            toggleSeatSelection(seatId);
        });
    });
}

// Toggle seat selection
function toggleSeatSelection(seatId) {
    const index = selectedSeats.indexOf(seatId);
    if (index > -1) {
        selectedSeats.splice(index, 1);
    } else {
        selectedSeats.push(seatId);
    }
    renderSeatLayout(currentTheater);
    updateSelectedSeatsDisplay();
}

// Update selected seats display
function updateSelectedSeatsDisplay() {
    const display = document.getElementById('selected-seats');
    if (selectedSeats.length === 0) {
        display.innerHTML = '<p style="color: #666;">No seats selected</p>';
        return;
    }

    let html = '<h4>Selected Seats:</h4><div class="selected-seats-list">';
    selectedSeats.forEach(seat => {
        html += `<span class="seat-badge">${seat}</span>`;
    });
    html += '</div>';
    display.innerHTML = html;
}

// Setup booking form
function setupBookingForm() {
    const form = document.getElementById('booking-form');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        createBooking();
    });
}

// Create booking
function createBooking() {
    const movieId = parseInt(document.getElementById('movie-select').value);
    const theaterId = parseInt(document.getElementById('theater-select').value);
    const customerName = document.getElementById('customer-name').value.trim();
    const customerEmail = document.getElementById('customer-email').value.trim();
    const customerPhone = document.getElementById('customer-phone').value.trim();

    // Validation
    if (!movieId || !theaterId || !customerName || !customerEmail || !customerPhone) {
        showToast('Please fill in all fields', 'error');
        return;
    }

    if (selectedSeats.length === 0) {
        showToast('Please select at least one seat', 'error');
        return;
    }

    const movie = movies.find(m => m.id === movieId);
    const theater = theaters.find(t => t.id === theaterId);

    if (!movie || !theater) {
        showToast('Invalid movie or theater selection', 'error');
        return;
    }

    // Check if seats are available
    const theaterKey = `theater-${theaterId}`;
    const unavailableSeats = selectedSeats.filter(seat => 
        occupiedSeats[theaterKey] && occupiedSeats[theaterKey].includes(seat)
    );

    if (unavailableSeats.length > 0) {
        showToast(`Seats ${unavailableSeats.join(', ')} are already booked`, 'error');
        return;
    }

    // Create booking
    const booking = {
        id: nextBookingId++,
        movie: movie,
        theater: theater,
        customerName: customerName,
        customerEmail: customerEmail,
        customerPhone: customerPhone,
        seats: [...selectedSeats],
        status: 'confirmed',
        bookingDate: new Date().toLocaleString()
    };

    bookings.push(booking);

    // Mark seats as occupied
    if (!occupiedSeats[theaterKey]) {
        occupiedSeats[theaterKey] = [];
    }
    selectedSeats.forEach(seat => {
        occupiedSeats[theaterKey].push(seat);
    });

    // Update theater available seats
    theater.availableSeats -= selectedSeats.length;

    // Reset form
    document.getElementById('booking-form').reset();
    selectedSeats = [];
    renderSeatLayout(currentTheater);
    updateSelectedSeatsDisplay();

    showToast(`Booking confirmed! Your Booking ID is: ${booking.id}`, 'success');
    
    // Switch to bookings section
    setTimeout(() => {
        document.querySelector('[data-section="bookings"]').click();
        displayBooking(booking);
    }, 1500);
}

// Setup booking search
function setupBookingSearch() {
    const searchBtn = document.getElementById('search-booking-btn');
    const bookingIdInput = document.getElementById('booking-id-input');

    searchBtn.addEventListener('click', function() {
        const bookingId = parseInt(bookingIdInput.value);
        if (!bookingId) {
            showToast('Please enter a valid Booking ID', 'error');
            return;
        }

        const booking = bookings.find(b => b.id === bookingId);
        if (booking) {
            displayBooking(booking);
        } else {
            showToast('Booking not found', 'error');
            document.getElementById('bookings-list').innerHTML = '';
        }
    });

    // Allow Enter key
    bookingIdInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchBtn.click();
        }
    });
}

// Display booking
function displayBooking(booking) {
    const bookingsList = document.getElementById('bookings-list');
    
    const bookingCard = document.createElement('div');
    bookingCard.className = 'booking-card';
    bookingCard.innerHTML = `
        <h3>Booking #${booking.id}</h3>
        <div class="info-row">
            <strong>Movie:</strong>
            <span>${booking.movie.title}</span>
        </div>
        <div class="info-row">
            <strong>Theater:</strong>
            <span>${booking.theater.name}</span>
        </div>
        <div class="info-row">
            <strong>Customer Name:</strong>
            <span>${booking.customerName}</span>
        </div>
        <div class="info-row">
            <strong>Email:</strong>
            <span>${booking.customerEmail}</span>
        </div>
        <div class="info-row">
            <strong>Phone:</strong>
            <span>${booking.customerPhone}</span>
        </div>
        <div class="info-row">
            <strong>Seats:</strong>
            <span>${booking.seats.join(', ')}</span>
        </div>
        <div class="info-row">
            <strong>Booking Date:</strong>
            <span>${booking.bookingDate}</span>
        </div>
        <div class="info-row">
            <strong>Status:</strong>
            <span class="status ${booking.status}">${booking.status.toUpperCase()}</span>
        </div>
        ${booking.status === 'confirmed' ? `
            <button class="btn-danger" onclick="cancelBooking(${booking.id})" style="margin-top: 15px; width: 100%;">
                Cancel Booking
            </button>
        ` : ''}
    `;
    
    bookingsList.innerHTML = '';
    bookingsList.appendChild(bookingCard);
}

// Cancel booking
function cancelBooking(bookingId) {
    const booking = bookings.find(b => b.id === bookingId);
    if (!booking) {
        showToast('Booking not found', 'error');
        return;
    }

    if (booking.status === 'cancelled') {
        showToast('Booking is already cancelled', 'error');
        return;
    }

    if (confirm('Are you sure you want to cancel this booking?')) {
        booking.status = 'cancelled';
        
        // Free up seats
        const theaterKey = `theater-${booking.theater.id}`;
        if (occupiedSeats[theaterKey]) {
            booking.seats.forEach(seat => {
                const index = occupiedSeats[theaterKey].indexOf(seat);
                if (index > -1) {
                    occupiedSeats[theaterKey].splice(index, 1);
                }
            });
        }

        // Update theater available seats
        booking.theater.availableSeats += booking.seats.length;

        showToast('Booking cancelled successfully', 'success');
        displayBooking(booking);
    }
}

// Setup modal
function setupModal() {
    const modal = document.getElementById('booking-modal');
    const closeBtn = document.querySelector('.close');

    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// Show toast notification
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Make cancelBooking available globally
window.cancelBooking = cancelBooking;
