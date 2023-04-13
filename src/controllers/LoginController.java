package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController {

    @FXML
    private TextField loginPassword;

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

}
