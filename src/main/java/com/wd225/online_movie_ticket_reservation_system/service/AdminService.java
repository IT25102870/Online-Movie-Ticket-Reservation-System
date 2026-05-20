package com.wd225.online_movie_ticket_reservation_system.service;

import com.wd225.online_movie_ticket_reservation_system.model.Admin;
import com.wd225.online_movie_ticket_reservation_system.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private static final String FILE_NAME = "data/txt/admin.txt";

    // CSV format: adminId,name,email,password,permissions
    private Admin parseLine(String line) {
        String[] p = line.split(",");
        if (p.length >= 5) return new Admin(p[0], p[1], p[2], p[3], p[4]);
        if (p.length == 4) return new Admin(p[0], p[1], p[2], p[3], "BASIC");
        return null;
    }

    // CREATE - Add new admin account
    public String addAdmin(Admin admin) {
        try {
            if (admin.getPermissions() == null || admin.getPermissions().isEmpty()) {
                admin.setPermissions("BASIC");
            }
            String data = admin.getUserId() + "," + admin.getName() + ","
                    + admin.getEmail() + "," + admin.getPassword() + "," + admin.getPermissions();
            FileUtil.saveToFile(FILE_NAME, data);
            return "Admin added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // READ ALL - View admin list
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                Admin a = parseLine(line);
                if (a != null) admins.add(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return admins;
    }

    // READ ONE - Get admin by ID
    public Admin getAdminById(String adminId) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(adminId)) return parseLine(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Polymorphism: admin-specific login (different from user login)
    public String login(String email, String password) {
        try {
            for (String line : FileUtil.readAllLines(FILE_NAME)) {
                String[] p = line.split(",");
                if (p.length >= 4 && p[2].equals(email) && p[3].equals(password)) {
                    return "Admin login successful! Welcome " + p[1]
                            + " | Permissions: " + (p.length >= 5 ? p[4] : "BASIC");
                }
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Invalid admin credentials.";
    }

    // UPDATE - Modify admin details and permissions
    public String updateAdmin(String adminId, Admin updatedAdmin) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean found = false;
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length >= 1 && p[0].equals(adminId)) {
                    String perm = (updatedAdmin.getPermissions() != null) ? updatedAdmin.getPermissions() : "BASIC";
                    updatedLines.add(adminId + "," + updatedAdmin.getName() + ","
                            + updatedAdmin.getEmail() + "," + updatedAdmin.getPassword() + "," + perm);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
            if (found) { FileUtil.overwriteFile(FILE_NAME, updatedLines); return "Admin updated!"; }
            return "Admin not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // DELETE - Remove admin account
    public String deleteAdmin(String adminId) {
        try {
            List<String> lines = FileUtil.readAllLines(FILE_NAME);
            boolean removed = lines.removeIf(line -> line.split(",")[0].equals(adminId));
            FileUtil.overwriteFile(FILE_NAME, lines);
            return removed ? "Admin deleted!" : "Admin not found!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
