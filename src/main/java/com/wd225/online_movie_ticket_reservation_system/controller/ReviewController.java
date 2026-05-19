package com.wd225.online_movie_ticket_reservation_system.controller;

import com.wd225.online_movie_ticket_reservation_system.model.Review;
import com.wd225.online_movie_ticket_reservation_system.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // CREATE - Submit a review
    @PostMapping("/add")
    public String addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    // READ - Get all reviews
    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // READ - Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id) {
        Review review = reviewService.getReviewById(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    // READ - Get all reviews for a specific movie
    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable String movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    // UPDATE - Edit a review
    @PutMapping("/update/{id}")
    public String updateReview(@PathVariable String id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    // DELETE - Delete a review
    @DeleteMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id) {
        return reviewService.deleteReview(id);
    }
}
