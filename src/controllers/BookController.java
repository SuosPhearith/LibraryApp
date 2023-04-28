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
    void clearData(ActionEvent event) {
        idField.setText(null);
        titleField.setText(null);
        authorField.setText(null);
        yearField.setText(null);
        pageField.setText(null);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ScreenPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void linkToBookPage(ActionEvent event) {

    }

    @FXML
    void autoGenerate(MouseEvent event) throws SQLException {
        int id=0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM books";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            id = rs.getInt("bookId") + 1;
        }
        String convert = Integer.toString(id);
        idField.setText(convert);
    }

    @FXML
    void insertBook(ActionEvent event) {
        //String bookId = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String page = pageField.getText();
        
        IsNullAndEmpty obj = new IsNullAndEmpty();
        if(obj.isNullAndEmpty(title) || obj.isNullAndEmpty(author) || obj.isNullAndEmpty(page)){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return ;
        }else{
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `books`(`title`, `author`, `year`, `pages`) VALUES (?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, title);
                statement.setString(2, author);
                statement.setString(3, year);
                statement.setString(4, page);

                int rowsInserted = statement.executeUpdate();
                if(rowsInserted>0){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    idField.setText(null);
                    titleField.setText(null);
                    authorField.setText(null);
                    yearField.setText(null);
                    pageField.setText(null);

                    showBooks();
                }else{
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
    void getItem(MouseEvent event) {
        Integer index = bookTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
        titleField.setText(titleCol.getCellData(index).toString());
        authorField.setText(authorCol.getCellData(index).toString());
        yearField.setText(yearCol.getCellData(index).toString());
        pageField.setText(pageCol.getCellData(index).toString());
        
    }

    @FXML
    void updateBook(ActionEvent event) {
        String bookId = idField.getText();
        int id;
        //System.out.println("kdkeicdlfjkwwwwwwwwwwwwww");
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String page = pageField.getText();
        boolean con = true; 
        
        IsNullAndEmpty obj = new IsNullAndEmpty();
        if(obj.isNullAndEmpty(bookId) || obj.isNullAndEmpty(title) || obj.isNullAndEmpty(author) || obj.isNullAndEmpty(page) || obj.isNullAndEmpty(year)){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return ;
        }else{
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM books";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(bookId);
                while(rs.next()){
                    if(rs.getInt("bookid") == id){
                        String sqlInsert = "UPDATE books SET title= ? ,author= ? ,year= ?,pages= ? WHERE bookId= ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, title);
                        statement2.setString(2, author);
                        statement2.setString(3, year);
                        statement2.setString(4, page);
                        statement2.setInt(5, id);

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
                if(con){
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
                //System.out.println("Record deleted successfully.");
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
                //System.out.println("Record not found.");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Books> getBooksList() throws SQLException{
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Books books;
            while(resultSet.next()){
                books = new Books(resultSet.getString("bookId"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("year"), resultSet.getString("pages"));
                bookList.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;

    }
    public void showBooks() throws SQLException{
        ObservableList<Books> list = getBooksList();
        idCol.setCellValueFactory(new PropertyValueFactory<Books, String>("bookId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Books, String>("year"));
        pageCol.setCellValueFactory(new PropertyValueFactory<Books,String>("pages"));
        bookTable.setItems(list);
    }


}
