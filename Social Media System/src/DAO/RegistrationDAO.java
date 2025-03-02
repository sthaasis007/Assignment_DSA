/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.User;
import Database.DatabaseConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationDAO {

    // Method to register a new user
    public boolean registerUser(User user) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";

        try {
            // Hash the password before storing it
            String hashedPassword = hashPassword(user.getPassword());

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashedPassword);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            // Check if the user was successfully inserted
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to hash the password using SHA-256
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
            e.printStackTrace();
            return null;
        }
    }
}
