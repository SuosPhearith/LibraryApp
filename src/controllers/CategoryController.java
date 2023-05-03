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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CategoryController implements Initializable {

    @FXML
    private TableColumn<Category, String> authorCol;

    @FXML
    private Button bookBtn;

    @FXML
    private Button bookIssuesBtn;

    @FXML
    private TableView<Category> bookTable;

    @FXML
    private Button bookstbn;

    @FXML
    private Button categoryBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button dashboardbtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Category, String> idCol;

    @FXML
    private TextField idField;

    @FXML
    private Button insertBtn;

    @FXML
    private Text logoutbtn;

    @FXML
    private TableColumn<Category, String> titleCol;

    @FXML
    private TextField titleField;

    @FXML
    private Button updateBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea infoField;

    @FXML
    void autoGenerate(MouseEvent event) throws SQLException {
        int id = 0;
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM category";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("categoryId") + 1;
        }
        String convert = Integer.toString(id);
        idField.setText(convert);
    }

    @FXML
    void clearData(ActionEvent event) {
        idField.setText(null);
        titleField.setText(null);
        infoField.setText(null);
    }

    @FXML
    void deleteBook(ActionEvent event) {
        String categoryId = idField.getText();
        int id = Integer.parseInt(categoryId);
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "DELETE FROM category WHERE categoryId=?";
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
                infoField.setText(null);
                showCategory();
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
    void getItem(MouseEvent event) {
        Integer index = bookTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        idField.setText(idCol.getCellData(index).toString());
        titleField.setText(titleCol.getCellData(index).toString());
        infoField.setText(authorCol.getCellData(index).toString());
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
    void handleBookIssues(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BookIssuePage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }

    @FXML
    void handleCategory(ActionEvent event) {

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
    void insertBook(ActionEvent event) {
        String catName = titleField.getText();
        String catInfo = infoField.getText();

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(catInfo) || obj.isNullAndEmpty(catName)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlInsert = "INSERT INTO `category`(`catName`, `catInfo`) VALUES (?,?)";
                PreparedStatement statement = conn.prepareStatement(sqlInsert);
                statement.setString(1, catName);
                statement.setString(2, catInfo);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Insert success.");
                    alert.showAndWait();
                    idField.setText(null);
                    titleField.setText(null);
                    infoField.setText(null);

                    showCategory();
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
        String categoryId = idField.getText();
        int id;
        // System.out.println("kdkeicdlfjkwwwwwwwwwwwwww");
        String catName = titleField.getText();
        String catInfo = infoField.getText();
        boolean con = true;

        IsNullAndEmpty obj = new IsNullAndEmpty();
        if (obj.isNullAndEmpty(categoryId) || obj.isNullAndEmpty(catName) || obj.isNullAndEmpty(catInfo)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
            return;
        } else {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sqlSelect = "SELECT * FROM category";
                PreparedStatement statement = conn.prepareStatement(sqlSelect);
                ResultSet rs = statement.executeQuery();
                id = Integer.parseInt(categoryId);
                while (rs.next()) {
                    if (rs.getInt("categoryId") == id) {
                        String sqlInsert = "UPDATE category SET catName= ? ,catInfo= ? WHERE categoryId= ? ";
                        PreparedStatement statement2 = conn.prepareStatement(sqlInsert);
                        statement2.setString(1, catName);
                        statement2.setString(2, catInfo);
                        statement2.setInt(3, id);

                        statement2.executeUpdate();

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Update success.");
                        alert.showAndWait();
                        con = false;

                        idField.setText(null);
                        titleField.setText(null);
                        infoField.setText(null);
                        showCategory();
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
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Category> getCategoryList() throws SQLException {
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM category";
            //Search Function
            String search = searchField.getText();
            if(search!=""){
                sql += " WHERE catName LIKE '%" +search+ "%'";
            }
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Category category;
            while (resultSet.next()) {
                category = new Category(resultSet.getString("categoryId"), resultSet.getString("catName"),resultSet.getString("catInfo"));
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;

    }

    public void showCategory() throws SQLException {
        ObservableList<Category> list = getCategoryList();
        idCol.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Category, String>("catName"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Category, String>("catInfo"));
        bookTable.setItems(list);
    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException {
        showCategory();
    }


}
