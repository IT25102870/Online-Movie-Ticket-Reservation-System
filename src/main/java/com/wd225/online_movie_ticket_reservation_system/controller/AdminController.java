package com.wd225.online_movie_ticket_reservation_system.controller;

import com.wd225.online_movie_ticket_reservation_system.model.Admin;
import com.wd225.online_movie_ticket_reservation_system.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // CREATE - Register a new admin account
    @PostMapping("/add")
    public String addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    // Polymorphism: admin-specific login (different from user login)
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return adminService.login(email, password);
    }

    // READ - Get all admins
    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // READ - Get admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable String id) {
        Admin admin = adminService.getAdminById(id);
        return admin != null ? ResponseEntity.ok(admin) : ResponseEntity.notFound().build();
    }

    // UPDATE - Modify admin details and permissions
    @PutMapping("/update/{id}")
    public String updateAdmin(@PathVariable String id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    // DELETE - Remove an admin account
    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable String id) {
        return adminService.deleteAdmin(id);
    }
}
