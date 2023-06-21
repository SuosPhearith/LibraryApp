package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import java.sql.Statement;

public class BorrowController implements Initializable {

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
    private Button addBtn;

    @FXML
    private TableColumn<Cart, String> bookIdCol;

    @FXML
    private TableColumn<Cart, String> bookNameCol;

    @FXML
    private TableView<Cart> tableBook;

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
        carts.clear();

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

    public ArrayList<Cart> carts = new ArrayList<>();

    @FXML
    void handleAdd(ActionEvent event) throws SQLException {
        var selectedItem = borrowCombo.getSelectionModel().getSelectedItem();
        String bookId = "";
        String bookName = "";

        if (selectedItem != null && !selectedItem.toString().isEmpty()) {
            var selectedString = selectedItem.toString();
            bookId = selectedString.substring(0, selectedString.indexOf("-"));
            bookName = selectedString.substring(selectedString.indexOf("-") + 1);
        } else {
            return;
        }

        if (carts.size() > 4) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Can borrow only 5 books!");
            alert.showAndWait();
            return;
        }

        carts.add(new Cart(bookId, bookName));

        ObservableList<Cart> bookList = FXCollections.observableArrayList(carts);
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        tableBook.setItems(bookList);

    }

    @FXML
    void insertBorrow(ActionEvent event) {
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String tel = yearField.getText();
        String borrowDate = borrowDateField.getValue().toString();
        String returnDate = returnDateField.getValue().toString();

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(schoolId)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        }
        try (Connection con = DatabaseConnector.getConnection()) {
            String insertInsert = "SELECT `schoolId` FROM `borrow` WHERE schoolId = ? AND isReturn = 'NoReturn'";
            PreparedStatement stm = con.prepareStatement(insertInsert);
            stm.setString(1, schoolId);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("ID not allowed to borrow!!");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        if (obj.isNullAndEmpty(name) || obj.isNullAndEmpty(schoolId) || obj.isNullAndEmpty(borrowDate)
                || obj.isNullAndEmpty(returnDate) || obj.isNullAndEmpty(tel)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try {
                Connection conn = DatabaseConnector.getConnection();
                String sql = "INSERT INTO `borrow`(`name`, `schoolId`, `tel`, `borrowDate`, `returnDate`, `isReturn`) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, schoolId);
                statement.setString(3, tel);
                statement.setString(4, borrowDate);
                statement.setString(5, returnDate);
                statement.setString(6, "NoReturn");
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lastInsertId = generatedKeys.getInt(1);
                        StringBuilder sql2 = new StringBuilder(
                                "INSERT INTO `borrowbook`(`borrowId`, `bookId`) VALUES ");
                        for (Cart cart : carts) {
                            sql2.append("('").append(lastInsertId).append("','").append(cart.getBookId()).append("'),");
                        }
                        String sql3 = sql2.substring(0, sql2.length() - 1);
                        PreparedStatement statement2 = conn.prepareStatement(sql3);
                        int insert = statement2.executeUpdate();
                        if (insert > 0) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Insert Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Insert borrow Success!!");
                            alert.showAndWait();
                            showBorrows();
                            clearData(event);
                            tableBook.setItems(null);
                            return;
                        } else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Insert Fail");
                            alert.setHeaderText(null);
                            alert.setContentText("Insert borrow Fail!!");
                            alert.showAndWait();
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
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
        String name = nameField.getText();
        String schoolId = schoolIdField.getText();
        String tel = yearField.getText();
        String borrowDate = borrowDateField.getValue().toString();
        String returnDate = returnDateField.getValue().toString();

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(name) || obj.isNullAndEmpty(schoolId) || obj.isNullAndEmpty(borrowDate)
                || obj.isNullAndEmpty(returnDate) || obj.isNullAndEmpty(tel)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "UPDATE `borrow` SET `name`=?,`schoolId`=?,`tel`=?,`borrowDate`=?,`returnDate`=? WHERE `borrowId`=?";
                PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                statement2.setString(1, name);
                statement2.setString(2, schoolId);
                statement2.setString(3, tel);
                statement2.setString(4, borrowDate);
                statement2.setString(5, returnDate);
                statement2.setString(6, borrowId);

                statement2.executeUpdate();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Update success.");
                alert.showAndWait();
                showBorrows();
                clearData(event);

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
                        resultSet.getString("returnDate"));
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
        tableView.setItems(list);
    }

    public ObservableList<Books> getCategoryList() throws SQLException {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM books WHERE isActive = 'Active'";
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
        borrowId = idCol.getCellData(index).toString();
        showBooks();
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

    public String borrowId = "";

    public ObservableList<Cart> getBooks() throws SQLException {
        String borrow = borrowId;
        // System.out.println(borrow);
        ObservableList<Cart> borrowList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT borrowbook.bookId, books.title FROM borrowbook join books on borrowbook.bookId = books.bookId WHERE borrowbook.borrowId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, borrow);
            ResultSet resultSet = statement.executeQuery();
            Cart books;
            while (resultSet.next()) {
                books = new Cart(resultSet.getString("bookId"), resultSet.getString("books.title"));
                borrowList.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowList;

    }

    public void showBooks() throws SQLException {
        ObservableList<Cart> list = getBooks();
        bookIdCol.setCellValueFactory(new PropertyValueFactory<Cart, String>("bookId"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<Cart, String>("bookName"));
        tableBook.setItems(list);
    }
}
