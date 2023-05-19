package controllers;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import config.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private TableColumn<Borrow, String> bookCol;

    @FXML
    private Text bookValue;

    @FXML
    private Button bookstbn;

    @FXML
    private Button borrowBtn;

    @FXML
    private TableColumn<Borrow, String> borrowDateCol;

    @FXML
    private Text borrowValue;

    @FXML
    private Text borrowingValue;

    @FXML
    private Button dashboardBtn;

    @FXML
    private TableColumn<Borrow, String> idCol;

    @FXML
    private TableColumn<Borrow, String> isReturnCol;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<Borrow, String> nameCol;

    @FXML
    private TableColumn<Borrow, String> returnDateCol;

    @FXML
    private TableColumn<Borrow, String> schoolIdCol;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, String> telCol;

    @FXML
    void getItem(MouseEvent event) {

    }

    @FXML
    void handleBorrow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BorrowPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showBorrows();
    }

    @FXML
    void handledashboard(ActionEvent event) {

    }

    @FXML
    void linkToBookPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BookPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }


    public ObservableList<Borrow> getBooksList() throws SQLException {
        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM borrow";
            // Search Function
            String search = searchField.getText();
            if (search != "") {
                sql += " WHERE name LIKE '%" + search + "%'";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Borrow borrows;
            while (resultSet.next()) {
                borrows = new Borrow(resultSet.getString("borrowId"), resultSet.getString("name"),
                        resultSet.getString("schoolId"), resultSet.getString("tel"), resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"), resultSet.getString("book"),resultSet.getString("isReturn"));
                borrowList.add(borrows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowList;

    }
    public void showBorrows() throws SQLException {
        ObservableList<Borrow> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("name"));
        schoolIdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("schoolId"));
        telCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("tel"));
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnDate"));
        bookCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("book"));
        isReturnCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("isReturn"));
        tableView.setItems(list);

        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT COUNT(*) FROM borrow";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int rowCount = resultSet.getInt(1);
        String result = rowCount + "";
        //System.out.println(rowCount);
        borrowValue.setText(result);

        String sql2 = "SELECT COUNT(*) FROM books";
        PreparedStatement statement2 = conn.prepareStatement(sql2);
        ResultSet resultSet2 = statement2.executeQuery();
        resultSet2.next();
        int rowCount2 = resultSet2.getInt(1);
        String result2 = rowCount2 + "";
        //System.out.println(rowCount);
        bookValue.setText(result2);

        String sql3 = "SELECT COUNT(*) FROM borrow WHERE isReturn = 'NoReturn' ";
        PreparedStatement statement3 = conn.prepareStatement(sql3);
        ResultSet resultSet3 = statement3.executeQuery();
        resultSet3.next();
        int rowCount3 = resultSet3.getInt(1);
        String result3 = rowCount3 + "";
        //System.out.println(rowCount);
        borrowingValue.setText(result3);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showBorrows();
            getBooksList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

