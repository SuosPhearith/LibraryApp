package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import config.DatabaseConnector;
import docs.IsNullAndEmpty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BorrowController implements Initializable {

    @FXML
    private TableColumn<Borrow, String> bookCol;

    @FXML
    private Button bookstbn;

    @FXML
    private ComboBox<Books> borrowCombo;

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
    private Button borrowingBtn;

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
    void handleBorrowing(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BorrowingPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void autoGenerate(MouseEvent event) throws SQLException {
        int id = 0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM borrow";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("borrowId") + 1;
        }
        String convert = Integer.toString(id);
        borrowIdField.setText(convert);
    }

    @FXML
    void clearData(ActionEvent event) {
        borrowIdField.setText(null);
        nameField.setText(null);
        schoolIdField.setText(null);
        yearField.setText(null);
        borrowDateField.setValue(null);
        returnDateField.setValue(null);
        borrowCombo.setValue(null);

    }

    @FXML
    void deleteBorrow(ActionEvent event) {
        String borrowId = borrowIdField.getText();
        int id = Integer.parseInt(borrowId);
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM borrow WHERE borrowId=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                // System.out.println("Record deleted successfully.");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Record deleted successfully.");
                alert.showAndWait();

                borrowIdField.setText(null);
                nameField.setText(null);
                schoolIdField.setText(null);
                yearField.setText(null);
                borrowDateField.setValue(null);
                returnDateField.setValue(null);
                borrowCombo.setValue(null);

                showBorrows();
            } else {
                // System.out.println("Record not found.");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail delete!!.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showBorrows();
    }

    @FXML
    void insertBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ReturnPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void insertBorrow(ActionEvent event) {
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String tel = yearField.getText();
        String borrowDate = borrowDateField.getValue().toString();
        String returnDate = returnDateField.getValue().toString();
        String book = borrowCombo.getSelectionModel().getSelectedItem().toString();

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(name) || obj.isNullAndEmpty(schoolId) || obj.isNullAndEmpty(borrowDate)
                || obj.isNullAndEmpty(returnDate) || obj.isNullAndEmpty(tel) || obj.isNullAndEmpty(book)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `borrow`(`name`, `schoolId`, `tel`, `borrowDate`, `returnDate`, `book`) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, name);
                statement.setString(2, schoolId);
                statement.setString(3, tel);
                statement.setString(4, borrowDate);
                statement.setString(5, returnDate);
                statement.setString(6, book);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    borrowIdField.setText(null);
                    nameField.setText(null);
                    schoolIdField.setText(null);
                    yearField.setText(null);
                    borrowDateField.setValue(null);
                    returnDateField.setValue(null);
                    borrowCombo.setValue(null);

                    showBorrows();

                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Fail");
                    alert.setHeaderText(null);
                    alert.setContentText("Fail insert!!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail insert!!");
                alert.showAndWait();
            }
        }
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

    @FXML
    void updateBorrow(ActionEvent event) {
        String borrowId = borrowIdField.getText();
        int id;
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String tel = yearField.getText();
        String borrowDate = borrowDateField.getValue().toString();
        String returnDate = returnDateField.getValue().toString();
        String book = borrowCombo.getSelectionModel().getSelectedItem().toString();
        boolean con = true;

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(name) || obj.isNullAndEmpty(schoolId) || obj.isNullAndEmpty(borrowDate)
                || obj.isNullAndEmpty(returnDate) || obj.isNullAndEmpty(tel) || obj.isNullAndEmpty(book)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM borrow";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(borrowId);
                System.out.println(id);
                while (rs.next()) {
                    if (rs.getInt("borrowId") == id) {
                        String sqlInsert = "UPDATE borrow SET name= ? ,schoolId= ? ,tel= ?,borrowDate= ?, returnDate = ?, book = ? WHERE borrowId = ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, name);
                        statement2.setString(2, schoolId);
                        statement2.setString(3, tel);
                        statement2.setString(4, borrowDate);
                        statement2.setString(5, returnDate);
                        statement2.setString(6, book);
                        statement2.setInt(7, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();
                        con = false;

                        borrowIdField.setText(null);
                        nameField.setText(null);
                        schoolIdField.setText(null);
                        yearField.setText(null);
                        borrowDateField.setValue(null);
                        returnDateField.setValue(null);
                        borrowCombo.setValue(null);
                        showBorrows();
                    }
                }
                if (con) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("ID not Found!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Fail insert!");
                alert.showAndWait();
            }

        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showBorrows();
            getCategoryList();
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
            if (search != "") {
                sql += " WHERE name LIKE '%" + search + "%'";
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

    public ObservableList<Books> getCategoryList() throws SQLException {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Books books;
            while (resultSet.next()) {
                books = new Books(resultSet.getString("bookId"), resultSet.getString("title"));
                bookList.add(books);
            }
            borrowCombo.setItems(bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(categoryList);
        return bookList;
    }

    @FXML
    void getItem(MouseEvent event) throws SQLException {
        Integer index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        borrowIdField.setText(idCol.getCellData(index).toString());
        nameField.setText(nameCol.getCellData(index).toString());
        schoolIdField.setText(schoolIdCol.getCellData(index).toString());
        yearField.setText(telCol.getCellData(index).toString());
        // borrowDateField.setValue(borrowDateCol.getCellData(index));
        // returnDateField.setVaule(returnDateCol.getCellData(index));
        // bookCol.setText(bookCol.getCellData(index).toString());
        // int catSize = getBooksList().size()-2;
        // String compare = bookCol.getCellData(index).toString();
        // borrowCombo.setPromptText(compare);
        // for (int i = 0; i < catSize; i++) {
        // if (borrowCombo.getItems().get(i).toString().equals(compare)) {
        // borrowCombo.getItems().get(i);
        // borrowCombo.setValue(borrowCombo.getItems().get(i));
        // // System.out.println(categoryCambo.getItems().get(i));
        // }
    }
    @FXML
    private Button dashboardBtn;
    @FXML
    void handleDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/DashboardPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }
}
