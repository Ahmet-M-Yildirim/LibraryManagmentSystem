package com.example.lms;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.lms.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.example.lms.DataBase.DataBaseHandler;

public class loginController implements Initializable {

    @FXML
    private TextField UserNameFiled;
    @FXML
    private TextField PassWordField;
    @FXML
    private Button LoginButton;
    @FXML
    private Button SignUpButton;
    @FXML
    private CheckBox checkBoxButton;

    private DataBaseHandler dataBaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = DataBaseHandler.getInstance();

    }

    @FXML
    private void LoginAction(ActionEvent event) throws IOException, SQLException {
        String Username_field = UserNameFiled.getText();
        String Password_field = PassWordField.getText();
        String sql = "SELECT user_id, Username, Password FROM user WHERE Username = ? AND Password = ?";

        PreparedStatement statement = dataBaseHandler.getConnection().prepareStatement(sql);
        statement.setString(1, Username_field);
        statement.setString(2, Password_field);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            String username = resultSet.getString("Username");
            String password = resultSet.getString("Password");

            if (Username_field.equals(username) && Password_field.equals(password)) {
                SessionManager.getInstance().setLoggedInUserId(userId);

                showAlert("Successful login");

                FXMLLoader loader = new FXMLLoader(MainController.class.getResource("Main.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } else {
                showAlert("Username or password is incorrect, please try again.");
            }
        } else {
            showAlert("Username or password is incorrect, please try again.");
        }

        resultSet.close();
        statement.close();
    }

    @FXML
    private void LoginKeyAction(ActionEvent event){
        try{
            if(event.getSource() == KeyCode.ENTER){
                LoginAction(event);
            }
        }catch (SQLException | IOException exception){
            exception.printStackTrace();
        }
    }

    @FXML
    private void SignUpAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("SignUp.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void checkAction(ActionEvent event) {
        if(checkBoxButton.isSelected()){
            PassWordField.setPromptText(PassWordField.getText());
            PassWordField.setText("");
        }
        else{
            PassWordField.setText(PassWordField.getPromptText());
            PassWordField.setPromptText("");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Information Message");
        alert.setContentText(message);

        ButtonType okbutton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okbutton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/dark_Theme.css").toExternalForm());

        alert.showAndWait();
    }

}
