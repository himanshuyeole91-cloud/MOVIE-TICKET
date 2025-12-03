package com.movieticket.service;

import com.movieticket.model.Movie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MovieService handles movie-related operations
 */
public class MovieService {
    private List<Movie> movies;

    public MovieService() {
        this.movies = new ArrayList<>();
        initializeMovies();
    }

    private void initializeMovies() {
        // Initialize with sample movies
        movies.add(new Movie(1, "Avengers: Endgame", "Action", 181, 
                "English", LocalDateTime.of(2024, 12, 20, 10, 0), 250.0));
        movies.add(new Movie(2, "The Dark Knight", "Action", 152, 
                "English", LocalDateTime.of(2024, 12, 20, 14, 30), 300.0));
        movies.add(new Movie(3, "Inception", "Sci-Fi", 148, 
                "English", LocalDateTime.of(2024, 12, 20, 18, 0), 280.0));
        movies.add(new Movie(4, "Interstellar", "Sci-Fi", 169, 
                "English", LocalDateTime.of(2024, 12, 21, 11, 0), 320.0));
        movies.add(new Movie(5, "The Matrix", "Sci-Fi", 136, 
                "English", LocalDateTime.of(2024, 12, 21, 15, 30), 270.0));
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public Movie getMovieById(int movieId) {
        return movies.stream()
                .filter(movie -> movie.getMovieId() == movieId)
                .findFirst()
                .orElse(null);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
}

