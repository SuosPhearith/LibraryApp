package controllers;

import java.io.IOException;

import config.LoginAndSignUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Text loginbtn;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpbtn;

    @FXML
    private TextField username;

    @FXML
    void handleLogin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }
    @FXML
    void SignUp(ActionEvent event) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String isActive = "1";
        String confirmPassowrd = this.confirmPassword.getText();
        if(username == null || username == "" || password == null || password == "" || confirmPassowrd == null || confirmPassowrd == ""){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate fields!!");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
        }
        else{
            if(confirmPassowrd.equals(password)){
                LoginAndSignUp db = new LoginAndSignUp();
                if(db.insertUser(username, password, isActive)){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Create Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Create Success");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
                    Parent welcomeParent = loader.load();
                    Scene welcomeScene = new Scene(welcomeParent);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(welcomeScene);
                    window.show();

                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Fail insert");
                    alert.setHeaderText(null);
                    alert.setContentText("Data already exists. Cannot insert!!");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error confirm password");
                alert.setHeaderText(null);
                alert.setContentText("Please check confirm password again!!");
                alert.showAndWait();
            }
        }
     }

}

