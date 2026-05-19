package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.Movie;
import com.wd225.online_movie_ticket_reservation_system.model.Review;
import com.wd225.online_movie_ticket_reservation_system.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private static final String FILE_NAME = "data/txt/reviews.txt";

    @Autowired
    private MovieService movieService;

    // CSV format: reviewId,userId,movieId,rating,comment
    // Split with limit 5 so comments containing commas are preserved
    private Review parseLine(String line) {
        String[] p = line.split(",", 5);
        if (p.length >= 5) {
            return new Review(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4]);
        }
        return null;
    }

    // CREATE - Submit a review
    public String addReview(Review review) {
        try {
            String data = review.getReviewId() + "," + review.getUserId() + ","
                    + review.getMovieId() + "," + review.getRating() + "," + review.getComment();
            FileUtil.saveToFile(FILE_NAME, data);
            
            updateMovieRating(review.getMovieId());
            return "Review submitted successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // READ ALL - View all reviews
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                Review r = parseLine(line);
                if (r != null) reviews.add(r);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reviews;
    }

    // READ ONE - Get review by ID
    public Review getReviewById(String reviewId) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(reviewId)) return parseLine(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // READ - Get all reviews for a specific movie
    public List<Review> getReviewsByMovieId(String movieId) {
        List<Review> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 3 && p[2].equals(movieId)) {
                    Review r = parseLine(line);
                    if (r != null) result.add(r);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // UPDATE - Edit a review
    public String updateReview(String reviewId, Review updatedReview) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(reviewId)) {
                    updatedLines.add(reviewId + "," + updatedReview.getUserId() + ","
                            + updatedReview.getMovieId() + "," + updatedReview.getRating()
                            + "," + updatedReview.getComment());
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            if (found) { 
                FileUtil.overwriteFile(FILE_NAME, updatedLines); 
                updateMovieRating(updatedReview.getMovieId());
                return "Review updated!"; 
            }
            return "Review not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Delete a review
    public String deleteReview(String reviewId) {
        try {
            Review review = getReviewById(reviewId);
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean removed = lines.removeIf(line -> line.split(",")[0].equals(reviewId));
            FileUtil.overwriteFile(FILE_NAME, lines);
            if (removed && review != null) {
                updateMovieRating(review.getMovieId());
            }
            return removed ? "Review deleted!" : "Review not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private void updateMovieRating(String movieId) {
        try {
            List<Review> reviews = getReviewsByMovieId(movieId);
            double total = 0;
            for (Review r : reviews) total += r.getRating();
            double avg = reviews.isEmpty() ? 0 : total / reviews.size();
            
            // round to 1 decimal place
            avg = Math.round(avg * 10.0) / 10.0;
            
            Movie movie = movieService.getMovieById(movieId);
            if (movie != null) {
                movie.setRating(avg);
                movieService.updateMovie(movieId, movie);
            }
        } catch (Exception e) {
            System.out.println("Failed to update movie rating: " + e.getMessage());
        }
    }
}
