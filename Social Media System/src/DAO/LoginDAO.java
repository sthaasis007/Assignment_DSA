/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;


import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import Database.DatabaseConnection;
import Model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {

    private Connection conn;

    // Constructor to initialize the database connection
    public LoginDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT password FROM Users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            // Ensure the ResultSet is closed properly
            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                String hashedPassword = hashPassword(password);
                
                // Compare the hashed password with the stored one
                return storedPasswordHash.equals(hashedPassword);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Method to hash the password
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Hashing Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
