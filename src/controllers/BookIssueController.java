package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookIssueController {

    @FXML
    private TableColumn<?, ?> authorCol;

    @FXML
    private Button bookBtn;

    @FXML
    private Button bookIssuesBtn;

    @FXML
    private TableView<?> bookTable;

    @FXML
    private Button bookstbn;

    @FXML
    private Button categoryBtn;

    @FXML
    private Button dashboardbtn;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TextField idField;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<?, ?> pageCol;

    @FXML
    private TableColumn<?, ?> pageCol1;

    @FXML
    private TableColumn<?, ?> pageCol11;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<?, ?> yearCol;

    @FXML
    void autoGenerate(MouseEvent event) {

    }

    @FXML
    void getItem(MouseEvent event) {

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
    void updateBook(ActionEvent event) {

    }

}
