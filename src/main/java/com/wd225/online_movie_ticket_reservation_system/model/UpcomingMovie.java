package com.wd225.online_movie_ticket_reservation_system.model;


public class UpcomingMovie extends Movie {
    private String releaseDate;

    public UpcomingMovie() { super(); }

    public UpcomingMovie(String id, String title, String genre,
                         double price, String releaseDate, double rating) {
        super(id, title, genre, price, releaseDate, "UPCOMING", rating);
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    
    @Override
    public String displayDetails() {
        return "[UPCOMING] " + getTitle()
                + " | Release: " + releaseDate
                + " | Genre: " + getGenre()
                + " | Price: $" + getPrice()
                + " | Rating: " + getRating() + "/5";
    }
}
