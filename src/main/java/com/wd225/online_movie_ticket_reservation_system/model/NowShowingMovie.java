package com.wd225.online_movie_ticket_reservation_system.model;


public class NowShowingMovie extends Movie {

    public NowShowingMovie() { super(); }

    public NowShowingMovie(String id, String title, String genre,
                           double price, String showtime, double rating) {
        super(id, title, genre, price, showtime, "NOW_SHOWING", rating);
    }

   
    @Override
    public String displayDetails() {
        return "[NOW SHOWING] " + getTitle()
                + " | Showtime: " + getShowtime()
                + " | Genre: " + getGenre()
                + " | Price: $" + getPrice()
                + " | Rating: " + getRating() + "/5";
    }
}
