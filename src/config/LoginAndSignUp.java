package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginAndSignUp {

    public boolean insertUser(String name, String pass, String isActive){
        try {
            Connection conn = DatabaseConnector.getConnection();
            String selectSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, name); 
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Data already exists. Cannot insert.");
                return false;
            } else {
                String insertSql = "INSERT INTO users (username, password, isActive) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, name);
                insertStmt.setString(2, pass);
                insertStmt.setString(3, isActive);
        
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Data inserted successfully.");
                    return true;
                }
        
                insertStmt.close();
            }
        
            rs.close();
            selectStmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public boolean loginUser(String username, String password){
        try {
            Connection connection = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }


}
