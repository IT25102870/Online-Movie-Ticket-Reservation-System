package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.User;
import com.wd225.online_movie_ticket_reservation_system.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final String FILE_NAME = "data/txt/users.txt";

    // CSV format: userId,name,email,password,role
    private User parseLine(String line) {
        String[] p = line.split(",");
        if (p.length >= 5) {
            return new User(p[0], p[1], p[2], p[3], p[4]);
        } else if (p.length == 4) {
            // backward-compatible with old 4-field records
            return new User(p[0], p[1], p[2], p[3], "USER");
        }
        return null;
    }

    // CREATE - Register new user
    public String addUser(User user) {
        try {
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }
            String data = user.getUserId() + "," + user.getName() + ","
                    + user.getEmail() + "," + user.getPassword() + "," + user.getRole();
            FileUtil.saveToFile(FILE_NAME, data);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // READ ALL - List all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            for (String line : lines) {
                User u = parseLine(line);
                if (u != null) users.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    // READ ONE - Get user by ID
    public User getUserById(String userId) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(userId)) return parseLine(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // READ - Search by username (name)
    public List<User> searchByName(String keyword) {
        List<User> result = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 2 && p[1].toLowerCase().contains(keyword.toLowerCase())) {
                    User u = parseLine(line);
                    if (u != null) result.add(u);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Polymorphism: regular user login (checks role = USER)
    public String login(String email, String password) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 4 && p[2].equals(email) && p[3].equals(password)) {
                    String role = p.length >= 5 ? p[4] : "USER";
                    return "Login successful! Welcome " + p[1] + " [" + role + "]";
                }
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Invalid email or password.";
    }

    // UPDATE - Modify user details
    public String updateUser(String userId, User updatedUser) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(userId)) {
                    String role = (updatedUser.getRole() != null) ? updatedUser.getRole() : "USER";
                    updatedLines.add(userId + "," + updatedUser.getName() + ","
                            + updatedUser.getEmail() + "," + updatedUser.getPassword() + "," + role);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            if (found) { FileUtil.overwriteFile(FILE_NAME, updatedLines); return "User updated!"; }
            return "User not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Remove user account
    public String deleteUser(String userId) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean removed = lines.removeIf(line -> line.split(",")[0].equals(userId));
            FileUtil.overwriteFile(FILE_NAME, lines);
            return removed ? "User deleted!" : "User not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
