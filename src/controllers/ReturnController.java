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

}
