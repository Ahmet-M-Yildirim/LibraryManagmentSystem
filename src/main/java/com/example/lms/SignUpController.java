package com.example.lms;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.DataBase.DataHelper;
import com.example.lms.Datas.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

    @FXML
    private Button Sign_InButton;
    @FXML
    private Button CancelButton;
    DataBaseHandler dataBaseHandler;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField mailField;
    @FXML
    private PasswordField passWordField;
    @FXML
    private PasswordField ConfirmField;
    @FXML
    private TextField PhoneField;

    @FXML
    private CheckBox checkButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.getConnection();

        PhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                PhoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void SignInAction(ActionEvent event) throws IOException {
        String Username = usernameField.getText();
        String PassWord = passWordField.getText();
        String mail = mailField.getText();
        String phone = PhoneField.getText();

        if (!PassWord.equals(ConfirmField.getText())) {
            showAlert("Password Mismatch");
            return;
        }

        if (!isValidEmail(mail)) {
            showAlert("Enter a valid email address");
            return;
        }

        if (!isValidPassword(PassWord)) {
            showAlert("Password must be at least 8 characters long, contain at least one uppercase letter and one special character");
            return;
        }

        if (!isValidPhoneNumber(phone)) {
            showAlert("Phone number must be exactly 11 digits");
            return;
        }

        if (isUsernameTaken(Username)) {
            showAlert("Username is already taken");
            return;
        }

        if (isEmailTaken(mail)) {
            showAlert("Email is already taken");
            return;
        }

        if (isPhoneTaken(phone)) {
            showAlert("Phone number is already taken");
            return;
        }

        Member member = new Member(Username, mail, PassWord, phone , 0);
        boolean isInserted = DataHelper.insertNewMember(member);

        if (isInserted) {
            showAlert("User successfully registered");

            FXMLLoader loader = new FXMLLoader(loginController.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } else {
            showAlert("Failed to register user");
        }
    }


    @FXML
    private void checkAction(ActionEvent event) {
        if(checkButton.isSelected()){
            passWordField.setPromptText(passWordField.getText());
            passWordField.setText("");

            ConfirmField.setPromptText(ConfirmField.getText());
            ConfirmField.setText("");
        }
        else{
            passWordField.setText(passWordField.getPromptText());
            passWordField.setPromptText("PassWord");

            ConfirmField.setText(ConfirmField.getPromptText());
            ConfirmField.setPromptText("Confirm PassWord");
        }
    }

    @FXML
    private void CancelAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(loginController.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");

        Matcher hasUpperCase = upperCasePattern.matcher(password);
        Matcher hasSpecialChar = specialCharPattern.matcher(password);

        return hasUpperCase.find() && hasSpecialChar.find();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.length() == 11;
    }

    private boolean isValidEmail(String mail) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@(gmail|hotmail)\\.com$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(mail);
        return emailMatcher.find();
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM user WHERE Username = ?";
        try {
            PreparedStatement statement = dataBaseHandler.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean isEmailTaken(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement statement = dataBaseHandler.getConnection().prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean isPhoneTaken(String phone) {
        String sql = "SELECT * FROM user WHERE phone = ?";
        try {
            PreparedStatement statement = dataBaseHandler.getConnection().prepareStatement(sql);
            statement.setString(1, phone);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
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
