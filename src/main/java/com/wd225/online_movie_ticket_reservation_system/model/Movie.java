package com.wd225.online_movie_ticket_reservation_system.model;


public class Movie {
    private String id;
    private String title;
    private String genre;
    private double price;
    private String showtime;
    private String status; 
    private double rating;
    private String imageUrl; // path to uploaded poster image

    public Movie() {}

    public Movie(String id, String title, String genre, double price,
                 String showtime, String status, double rating, String imageUrl) {
        this.id       = id;
        this.title    = title;
        this.genre    = genre;
        this.price    = price;
        this.showtime = showtime;
        this.status   = status;
        this.rating   = rating;
        this.imageUrl = imageUrl;
    }

    
    public Movie(String id, String title, String genre, double price,
                 String showtime, String status, double rating) {
        this(id, title, genre, price, showtime, status, rating, "");
    }

    
    public Movie(String id, String title, String genre, double price) {
        this(id, title, genre, price, "TBD", "NOW_SHOWING", 0.0, "");
    }

    // Getters
    public String getId()       { return id; }
    public String getTitle()    { return title; }
    public String getGenre()    { return genre; }
    public double getPrice()    { return price; }
    public String getShowtime() { return showtime; }
    public String getStatus()   { return status; }
    public double getRating()   { return rating; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setId(String id)           { this.id = id; }
    public void setTitle(String title)     { this.title = title; }
    public void setGenre(String genre)     { this.genre = genre; }
    public void setPrice(double price)     { this.price = price; }
    public void setShowtime(String showtime){ this.showtime = showtime; }
    public void setStatus(String status)   { this.status = status; }
    public void setRating(double rating)   { this.rating = rating; }
    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }

    
    public String displayDetails() {
        return "Movie [" + id + "] " + title + " | " + genre
                + " | " + showtime + " | $" + price + " | Rating: " + rating + " | " + status;
    }
}
