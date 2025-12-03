package com.movieticket.model;

import java.time.LocalDateTime;

/**
 * Movie class represents a movie with its details
 */
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int duration; // in minutes
    private String language;
    private LocalDateTime showTime;
    private double ticketPrice;

    public Movie(int movieId, String title, String genre, int duration, 
                 String language, LocalDateTime showTime, double ticketPrice) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return String.format("Movie ID: %d | Title: %s | Genre: %s | Duration: %d min | Language: %s | Show Time: %s | Price: ₹%.2f",
                movieId, title, genre, duration, language, showTime.toString(), ticketPrice);
    }
}

