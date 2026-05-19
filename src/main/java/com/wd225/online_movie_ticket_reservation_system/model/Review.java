package com.wd225.online_movie_ticket_reservation_system.model;

// Encapsulation: all review details secured with private fields
public class Review {
    private String reviewId;
    private String userId;
    private String movieId;
    private int rating;   // 1 to 5
    private String comment;

    public Review() {}

    public Review(String reviewId, String userId, String movieId, int rating, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters
    public String getReviewId() { return reviewId; }
    public String getUserId() { return userId; }
    public String getMovieId() { return movieId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    // Setters
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    public String displayDetails() {
        return "Review [" + reviewId + "] By: " + userId
                + " | Movie: " + movieId
                + " | Rating: " + rating + "/5 | " + comment;
    }
}
