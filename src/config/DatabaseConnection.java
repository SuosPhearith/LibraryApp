package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public void insertUser(String name, String pass, String isActive ) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/library_app";
        String username = "root";
        String password = "";


        // Create a connection to the database and insert the data
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO users (username, password, isActive) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, pass);
            statement.setString(3, isActive);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully");
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert data: " + e.getMessage());
        }
        
    }


}
