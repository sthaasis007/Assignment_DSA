/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/SocialMedia";  // Replace with your database name
    private static final String USER = "root";  // Replace with your username
    private static final String PASSWORD = "1aashishA";  // Replace with your password

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver (optional with modern JDBC versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

