package controllers;

import java.io.IOException;

import config.LoginAndSignUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.Node;

public class LoginController {

    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField loginUsername;

    @FXML
    private Button loginbtn;

    @FXML
    private Text signUpbtn;

    @FXML
    void handleSignUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/SignUpPage.fxml"));
        Parent welcomeParent = loader.load();
        Scene welcomeScene = new Scene(welcomeParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(welcomeScene);
        window.show();
    }
    @FXML
    void doLogin(ActionEvent event) throws IOException {
        //System.out.println("wkwkwkwkwkw");
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        LoginAndSignUp db = new LoginAndSignUp();
        if(username == "" || username == null || password == "" || password == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate fields!!");
            alert.setHeaderText(null);
            alert.setContentText("Please input all fields!!");
            alert.showAndWait();
        }else{
            if(db.loginUser(username, password)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ScreenPage.fxml"));
                Parent welcomeParent = loader.load();
                Scene welcomeScene = new Scene(welcomeParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(welcomeScene);
                window.show();
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fail");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect username or password!!");
                alert.showAndWait();
            }
        }
    }

}
