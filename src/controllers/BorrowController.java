package controllers;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BorrowController implements Initializable {

    @FXML
    private TableColumn<Borrow, String> bookCol;

    @FXML
    private Button bookstbn;

    @FXML
    private ComboBox<?> borrowCombo;

    @FXML
    private TableColumn<Borrow, String> borrowDateCol;

    @FXML
    private DatePicker borrowDateField;

    @FXML
    private TextField borrowIdField;

    @FXML
    private Button clearBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Borrow, String> idCol;

    @FXML
    private Button insertBtn;

    @FXML
    private Button insertBtn2;

    @FXML
    private Button insertBtn21;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<Borrow, String> nameCol;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<Borrow, String> returnDateCol;

    @FXML
    private DatePicker returnDateField;

    @FXML
    private TableColumn<Borrow, String> schoolIdCol;

    @FXML
    private TextField schoolIdField;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField yearField;

    @FXML
    private TableColumn<Borrow, String> telCol;

    @FXML
    void autoGenerate(MouseEvent event) {

    }

    @FXML
    void clearData(ActionEvent event) {

    }

    @FXML
    void deleteBorrow(ActionEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showBorrows();
    }

    @FXML
    void insertBook(ActionEvent event) {

    }

    @FXML
    void insertBorrow(ActionEvent event) {

    }

    @FXML
    void linkToBookPage(ActionEvent event) {

    }

    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void updateBorrow(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showBorrows();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Borrow> getBooksList() throws SQLException {
        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM borrow";
            // Search Function
            String search = searchField.getText();
            if(search!=""){
                sql += " WHERE name LIKE '%" +search+ "%'";
            }
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Borrow borrows;
            while (resultSet.next()) {
                borrows = new Borrow(resultSet.getString("borrowId"), resultSet.getString("name"),
                        resultSet.getString("schoolId"), resultSet.getString("tel"), resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"), resultSet.getString("book"));
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
        tableView.setItems(list);
    }

}

