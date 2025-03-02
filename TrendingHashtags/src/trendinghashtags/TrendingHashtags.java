/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trendinghashtags;




import java.sql.*;

public class TrendingHashtags {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/twitterDB"; // Replace with your database name
        String user = "root";  // Replace with your MySQL username
        String password = "radhekrishna@123";  // Replace with your MySQL password

        // SQL Query
        String sql = """
            WITH HashtagExtraction AS (
                SELECT 
                    id,
                    SUBSTRING_INDEX(SUBSTRING_INDEX(content, '#', n.digit + 1), ' ', -1) AS hashtag
                FROM Tweets
                JOIN (
                    SELECT 1 AS digit UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 
                    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 
                    UNION ALL SELECT 9 UNION ALL SELECT 10
                ) AS n 
                ON CHAR_LENGTH(content) - CHAR_LENGTH(REPLACE(content, '#', '')) >= n.digit
                WHERE created_at BETWEEN '2024-02-01' AND '2024-02-29'
            )
            SELECT 
                hashtag, 
                COUNT(*) AS hashtag_count
            FROM HashtagExtraction
            WHERE hashtag IS NOT NULL AND hashtag != ''
            GROUP BY hashtag
            ORDER BY hashtag_count DESC, hashtag DESC
            LIMIT 3;
        """;

        // Establish connection and execute query
        try {
            // Load MySQL Driver (Fix for "No suitable driver found" issue)
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("Top 3 Trending Hashtags in February 2024:");
                System.out.println("------------------------------------------");

                while (rs.next()) {
                    String hashtag = rs.getString("hashtag");
                    int count = rs.getInt("hashtag_count");
                    System.out.println(hashtag + ": " + count + " mentions");
                }

            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found! Make sure you have added mysql-connector-java.jar to your project.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
