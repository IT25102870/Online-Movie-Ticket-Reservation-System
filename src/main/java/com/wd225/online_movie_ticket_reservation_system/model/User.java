package com.wd225.online_movie_ticket_reservation_system.model;

// Encapsulation: all fields are private with getters/setters
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role; // "USER" or "ADMIN"

    public User() {}

    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Backward-compatible constructor (defaults role to "USER")
    public User(String userId, String name, String email, String password) {
        this(userId, name, email, password, "USER");
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

    // Polymorphism: overridable by subclasses (Admin)
    public String displayDetails() {
        return "User [" + userId + "] " + name + " | " + email + " | Role: " + role;
    }
}
