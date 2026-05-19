package com.wd225.online_movie_ticket_reservation_system.controller;

import com.wd225.online_movie_ticket_reservation_system.model.Movie;
import com.wd225.online_movie_ticket_reservation_system.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // CREATE - Add a new movie
    @PostMapping("/add")
    public String addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    // READ - Get all movies
    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // READ - Get a single movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    // READ - Search movies by title
    @GetMapping("/search/title")
    public List<Movie> searchByTitle(@RequestParam String keyword) {
        return movieService.searchByTitle(keyword);
    }

    // READ - Search movies by rating
    @GetMapping("/search/rating")
    public List<Movie> searchByRating(@RequestParam double min) {
        return movieService.searchByRating(min);
    }

    // UPDATE - Modify movie details 
    @PutMapping("/update/{id}")
    public String updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    // DELETE - Remove a movie from the system
    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable String id) {
        return movieService.deleteMovie(id);
    }
}
