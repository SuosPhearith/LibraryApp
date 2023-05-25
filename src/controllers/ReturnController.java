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

public class ReturnController implements Initializable {

    @FXML
    private TableColumn<Borrow, String> bookCol;

    @FXML
    private Button bookstbn;

    @FXML
    private TableColumn<Borrow, String> borrowDateCol;

    @FXML
    private Button borrowingBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private TableColumn<Borrow, String> idCol;

    @FXML
    private TextField idField;

    @FXML
    private Button insertBtn2;

    @FXML
    private Button insertBtn21;

    @FXML
    private TableColumn<Borrow, String> isReturnCol;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<Borrow, String> nameCol;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<Borrow, String> returnDateCol;

    @FXML
    private TableColumn<Borrow, String> schoolIdCol;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox selectCombo;

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, String> telCol;

    @FXML
    void autoGenerate(MouseEvent event) {

    }

    @FXML
    void doReturn(ActionEvent event) {
        String bookId = idField.getText();
        int id;
        String isReturn = selectCombo.getSelectionModel().getSelectedItem().toString();
        boolean con = true;

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(bookId) || obj.isNullAndEmpty(isReturn)) {
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
                id = Integer.parseInt(bookId);
                while (rs.next()) {
                    if (rs.getInt("borrowId") == id) {
                        String sqlInsert = "UPDATE borrow SET isReturn= ? WHERE borrowId= ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, isReturn);
                        statement2.setInt(2, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();
                        con = false;

                        idField.setText(null);
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

    @FXML
    void getItem(MouseEvent event) {
        Integer index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
        selectCombo.setPromptText(isReturnCol.getCellData(index).toString());
        String compare = isReturnCol.getCellData(index).toString();
        selectCombo.setPromptText(compare);
        for (int i = 0; i < 2; i++) {
            if (selectCombo.getItems().get(i).toString().equals(compare)) {
                selectCombo.getItems().get(i);
                selectCombo.setValue(selectCombo.getItems().get(i));
            }
        }
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
    void handleBorrowing(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BorrowingPage.fxml"));
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
    void handledashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/DashboardPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void insertBook(ActionEvent event) {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Return", "NoReturn");
        selectCombo.setItems(list);
        try {
            showBorrows();
            getBooksList();
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
                        resultSet.getString("returnDate"), resultSet.getString("book"),
                        resultSet.getString("isReturn"));
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

    }

}
