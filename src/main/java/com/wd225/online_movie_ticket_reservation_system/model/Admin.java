package com.wd225.online_movie_ticket_reservation_system.model;

// Inheritance: Admin extends User
public class Admin extends User {
    private String permissions;

    public Admin() { super(); }

    public Admin(String userId, String name, String email, String password, String permissions) {
        super(userId, name, email, password, "ADMIN");
        this.permissions = permissions;
    }

    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }

    // Polymorphism: overrides displayDetails() from User
    @Override
    public String displayDetails() {
        return "Admin [" + getUserId() + "] " + getName() + " | " + getEmail() + " | Permissions: " + permissions;
    }
}
