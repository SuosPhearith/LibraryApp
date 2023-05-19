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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookController implements Initializable {

    @FXML
    private TableColumn<Books, String> authorCol;

    @FXML
    private Button bookBtn;

    @FXML
    private Button categoryBtn;

    @FXML
    private ComboBox<Category> categoryCambo;

    @FXML
    private Button bookIssuesBtn;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private TableView<Books> bookTable;

    @FXML
    private Button bookstbn;

    @FXML
    private Button dashboardbtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Books, String> idCol;

    @FXML
    private TableColumn<Books, String> categoryCol;

    @FXML
    private TextField idField;

    @FXML
    private Button insertBtn;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<Books, String> pageCol;

    @FXML
    private TextField pageField;

    @FXML
    private TableColumn<Books, String> titleCol;

    @FXML
    private TextField titleField;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Books, String> yearCol;

    @FXML
    private Button clearBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    void handleBookIssues(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BookIssuePage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void handleCategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/CategoryPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void clearData(ActionEvent event) {
        idField.setText(null);
        titleField.setText(null);
        authorField.setText(null);
        yearField.setText(null);
        pageField.setText(null);
        categoryCambo.setPromptText("select");
    }

    @FXML
    void handleLogout(MouseEvent event) throws IOException {
        // handle logout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void linkDashboard(ActionEvent event) throws IOException {
        // link dashboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/DashboardPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
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
    void autoGenerate(MouseEvent event) throws SQLException {
        int id = 0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM books";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("bookId") + 1;
        }
        String convert = Integer.toString(id);
        idField.setText(convert);
    }

    @FXML
    void insertBook(ActionEvent event) {
        // String bookId = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String page = pageField.getText();
        String category = categoryCambo.getSelectionModel().getSelectedItem().toString();

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(title) || obj.isNullAndEmpty(author) || obj.isNullAndEmpty(page)
                || obj.isNullAndEmpty(category) || obj.isNullAndEmpty(year)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `books`(`title`, `author`, `year`, `pages`, `category`) VALUES (?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, title);
                statement.setString(2, author);
                statement.setString(3, year);
                statement.setString(4, page);
                statement.setString(5, category);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    // idField.setText(null);
                    // titleField.setText(null);
                    // authorField.setText(null);
                    // yearField.setText(null);
                    // pageField.setText(null);
                    clearData(event);

                    showBooks();
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
    void getItem(MouseEvent event) throws SQLException {
        Integer index = bookTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
        titleField.setText(titleCol.getCellData(index).toString());
        authorField.setText(authorCol.getCellData(index).toString());
        yearField.setText(yearCol.getCellData(index).toString());
        pageField.setText(pageCol.getCellData(index).toString());
        int catSize = getCategoryList().size();
        String compare = categoryCol.getCellData(index).toString();
        categoryCambo.setPromptText(compare);
        for (int i = 0; i < catSize; i++) {
            if (categoryCambo.getItems().get(i).toString().equals(compare)) {
                categoryCambo.getItems().get(i);
                categoryCambo.setValue(categoryCambo.getItems().get(i));
                // System.out.println(categoryCambo.getItems().get(i));
            }
        }

    }

    @FXML
    void updateBook(ActionEvent event) {
        String bookId = idField.getText();
        int id;
        // System.out.println("kdkeicdlfjkwwwwwwwwwwwwww");
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String page = pageField.getText();
        String category = categoryCambo.getSelectionModel().getSelectedItem().toString();
        boolean con = true;

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(bookId) || obj.isNullAndEmpty(title) || obj.isNullAndEmpty(author)
                || obj.isNullAndEmpty(page) || obj.isNullAndEmpty(year) || obj.isNullAndEmpty(category)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM books";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(bookId);
                while (rs.next()) {
                    if (rs.getInt("bookid") == id) {
                        String sqlInsert = "UPDATE books SET title= ? ,author= ? ,year= ?,pages= ?, category = ? WHERE bookId= ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, title);
                        statement2.setString(2, author);
                        statement2.setString(3, year);
                        statement2.setString(4, page);
                        statement2.setString(5, category);
                        statement2.setInt(6, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();
                        con = false;

                        idField.setText(null);
                        titleField.setText(null);
                        authorField.setText(null);
                        yearField.setText(null);
                        pageField.setText(null);
                        showBooks();
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

    @FXML
    void deleteBook(ActionEvent event) {
        String bookId = idField.getText();
        int id = Integer.parseInt(bookId);
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM books WHERE bookId=?";
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

                idField.setText(null);
                titleField.setText(null);
                authorField.setText(null);
                yearField.setText(null);
                pageField.setText(null);
                showBooks();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showBooks();
            getCategoryList();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Books> getBooksList() throws SQLException {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM books";
            // Search Function
            String search = searchField.getText();
            if(search!=""){
                sql += " WHERE title LIKE '%" +search+ "%'";
            }
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Books books;
            while (resultSet.next()) {
                books = new Books(resultSet.getString("bookId"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("year"), resultSet.getString("pages"),
                        resultSet.getString("category"));
                bookList.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;

    }

    public void showBooks() throws SQLException {
        ObservableList<Books> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<Books, String>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Books, String>("year"));
        pageCol.setCellValueFactory(new PropertyValueFactory<Books, String>("pages"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        bookTable.setItems(list);
    }

    public ObservableList<Category> getCategoryList() throws SQLException {
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM category";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Category category;
            while (resultSet.next()) {
                category = new Category(resultSet.getString("catName"));
                categoryList.add(category);
            }
            categoryCambo.setItems(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(categoryList);
        return categoryList;
    }
    
    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showBooks();
    }
    @FXML
    private Button borrowBtn;
    @FXML
    void handleBorrow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BorrowPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

}
