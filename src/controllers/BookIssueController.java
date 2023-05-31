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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookIssueController implements Initializable{

    @FXML
    private TableColumn<BookIssue, String> authorCol;

    @FXML
    private Button bookBtn;

    @FXML
    private Button bookIssuesBtn;

    @FXML
    private TableView<BookIssue> bookTable;

    @FXML
    private Button bookstbn;

    @FXML
    private Button categoryBtn;

    @FXML
    private Button dashboardbtn;

    @FXML
    private TableColumn<BookIssue, String> idCol;

    @FXML
    private TextField idField;

    @FXML
    private TableColumn<BookIssue, String> isActiveCol;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<BookIssue, String> pageCol;

    @FXML
    private TableColumn<BookIssue, String> categoryCol;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox selectCombo;

    @FXML
    private TableColumn<BookIssue, String> titleCol;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<BookIssue, String> yearCol;

    @FXML
    void autoGenerate(MouseEvent event) {

    }

    @FXML
    void getItem(MouseEvent event) {
        Integer index = bookTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
        selectCombo.setPromptText(isActiveCol.getCellData(index).toString());
        String compare = isActiveCol.getCellData(index).toString();
        selectCombo.setPromptText(compare);
        for (int i = 0; i < 2; i++) {
            if (selectCombo.getItems().get(i).toString().equals(compare)) {
                selectCombo.getItems().get(i);
                selectCombo.setValue(selectCombo.getItems().get(i));
            }
        }
    
    }

    @FXML
    void handleBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BookPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void handleBookIssues(ActionEvent event) {

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
    void handleLogout(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void linkDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/DashboardPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
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
    void linkToBookPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BookPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void updateBook(ActionEvent event) {
        String bookId = idField.getText();
        int id;
        String isActive = selectCombo.getSelectionModel().getSelectedItem().toString();
        boolean con = true;

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(bookId) || obj.isNullAndEmpty(isActive)) {
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
                        String sqlInsert = "UPDATE books SET isActive= ? WHERE bookId= ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, isActive);
                        statement2.setInt(2, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();
                        con = false;

                        idField.setText(null);
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
    void handleSearch(ActionEvent event) throws SQLException {
        showBooks();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            ObservableList<String> list = FXCollections.observableArrayList("Active","Disable");
            selectCombo.setItems(list);
        try {
            showBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<BookIssue> getBooksList() throws SQLException {
        ObservableList<BookIssue> bookList = FXCollections.observableArrayList();
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
            BookIssue books;
            while (resultSet.next()) {
                books = new BookIssue(resultSet.getString("bookId"), resultSet.getString("title"),
                        resultSet.getString("author"), resultSet.getString("year"), resultSet.getString("pages"),
                        resultSet.getString("category"),resultSet.getString("isActive"));
                bookList.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;

    }

    public void showBooks() throws SQLException {
        ObservableList<BookIssue> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("year"));
        pageCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("pages"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("category"));
        isActiveCol.setCellValueFactory(new PropertyValueFactory<BookIssue, String>("isActive"));
        bookTable.setItems(list);
    }

}
