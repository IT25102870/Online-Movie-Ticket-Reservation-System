package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.Movie;
import com.wd225.online_movie_ticket_reservation_system.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private static final String FILE_NAME = "data/txt/movies.txt";

    //format: id,title,genre,price,showtime,status,rating,imageUrl
    private Movie parseLine(String line) {
        String[] p = line.split(",", 8);
        if (p.length >= 8) {
            return new Movie(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4], p[5], Double.parseDouble(p[6]), p[7]);
        } else if (p.length >= 7) {
            return new Movie(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4], p[5], Double.parseDouble(p[6]));
        } else if (p.length == 4) {
            return new Movie(p[0], p[1], p[2], Double.parseDouble(p[3]));
        }
        return null;
    }

    // CREATE - Add a new movie
    public String addMovie(Movie movie) {
        try {
            if (movie.getShowtime() == null) movie.setShowtime("TBD");
            if (movie.getStatus()   == null) movie.setStatus("NOW_SHOWING");
            if (movie.getImageUrl() == null) movie.setImageUrl("");
            String data = movie.getId() + "," + movie.getTitle() + "," + movie.getGenre() + ","
                    + movie.getPrice() + "," + movie.getShowtime() + ","
                    + movie.getStatus() + "," + movie.getRating() + "," + movie.getImageUrl();
            FileUtil.saveToFile(FILE_NAME, data);
            return "Movie added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // READ ALL - Get all movies
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                Movie m = parseLine(line);
                if (m != null) movies.add(m);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return movies;
    }

    // READ ONE - Get movie by ID
    public Movie getMovieById(String id) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(id)) return parseLine(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // READ - Search movies by title
    public List<Movie> searchByTitle(String keyword) {
        List<Movie> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 2 && p[1].toLowerCase().contains(keyword.toLowerCase())) {
                    Movie m = parseLine(line);
                    if (m != null) result.add(m);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // READ - Search movies by rating
    public List<Movie> searchByRating(double minRating) {
        List<Movie> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 7) {
                    double rating = Double.parseDouble(p[6]);
                    if (rating >= minRating) {
                        Movie m = parseLine(line);
                        if (m != null) result.add(m);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // UPDATE - Modify movie details
    public String updateMovie(String id, Movie updatedMovie) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] p = line.split(",", 8);
                if (p.length >= 1 && p[0].equals(id)) {
                    String showtime  = (updatedMovie.getShowtime()  != null) ? updatedMovie.getShowtime()  : "TBD";
                    String status    = (updatedMovie.getStatus()    != null) ? updatedMovie.getStatus()    : "NOW_SHOWING";
                    String imageUrl  = (updatedMovie.getImageUrl()  != null) ? updatedMovie.getImageUrl()  : (p.length >= 8 ? p[7] : "");
                    updatedLines.add(id + "," + updatedMovie.getTitle() + "," + updatedMovie.getGenre() + ","
                            + updatedMovie.getPrice() + "," + showtime + "," + status + ","
                            + updatedMovie.getRating() + "," + imageUrl);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            if (found) { FileUtil.overwriteFile(FILE_NAME, updatedLines); return "Movie updated!"; }
            return "Movie not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Remove a movie from the system
    public String deleteMovie(String id) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean removed = lines.removeIf(line -> line.split(",")[0].equals(id));
            FileUtil.overwriteFile(FILE_NAME, lines);
            return removed ? "Movie deleted!" : "Movie not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
