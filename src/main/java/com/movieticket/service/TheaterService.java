package com.movieticket.service;

import com.movieticket.model.Theater;
import java.util.ArrayList;
import java.util.List;

/**
 * TheaterService handles theater-related operations
 */
public class TheaterService {
    private List<Theater> theaters;

    public TheaterService() {
        this.theaters = new ArrayList<>();
        initializeTheaters();
    }

    private void initializeTheaters() {
        // Initialize with sample theaters
        theaters.add(new Theater(1, "PVR Cinemas", 10, 15));
        theaters.add(new Theater(2, "INOX Multiplex", 8, 12));
        theaters.add(new Theater(3, "Cinepolis", 10, 14));
    }

    public List<Theater> getAllTheaters() {
        return new ArrayList<>(theaters);
    }

    public Theater getTheaterById(int theaterId) {
        return theaters.stream()
                .filter(theater -> theater.getTheaterId() == theaterId)
                .findFirst()
                .orElse(null);
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }
}

