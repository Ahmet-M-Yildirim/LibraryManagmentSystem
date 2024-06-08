package com.example.lms;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.Datas.Member;
import com.example.lms.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private TextField UsernameField;
    @FXML
    private TextField MailField;
    @FXML
    private TextField PhoneField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmField;
    @FXML
    private PasswordField CurrentField;
    @FXML
    private Button SaveButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDatas();
    }

    @FXML
    private void SaveAction(ActionEvent event) {
        String username = UsernameField.getText();
        String mail = MailField.getText();
        String phone = PhoneField.getText();
        String password = newPasswordField.getText();
        String confirmPassword = confirmField.getText();
        String currentPassword = CurrentField.getText();

        if(!username.equals("") || !mail.equals("") || !phone.equals("")){
            UpdateDatas(username, mail, phone, currentPassword, password);
        }
        else if(!password.equals("") && !confirmPassword.equals("")){
            if(password.equals(confirmPassword)){
                updatePassword(currentPassword, password);
            }
            else{
                System.out.println("Password not matches");
            }
        }
        else{
            System.out.println("Not Update");
        }
    }

    private void loadDatas(){
        list.clear();
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, SessionManager.getInstance().getLoggedInUserId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                String username = rs.getString("Username");
                String mail = rs.getString("email");
                String phone = rs.getString("phone");

                UsernameField.setText(username);
                MailField.setText(mail);
                PhoneField.setText(phone);

                list.add(new Member(username, "", mail, phone , 0));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void updatePassword(String currentPassword,String newPassword){
        String sql = "UPDATE user SET password = ? WHERE user_id = ? AND password = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setInt(2, SessionManager.getInstance().getLoggedInUserId());
            statement.setString(3, currentPassword);

            int affectedRows = statement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Password update succesfully");
            }
            else{
                System.out.println("password could not be updated");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void UpdateDatas(String newUsername , String newMail , String newPhone , String CurrentPassword , String newPassword){
        String sql = "UPDATE user SET Username = ?, email = ?,phone = ? WHERE user_id = ? AND password = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, newUsername);
            statement.setString(2, newMail);
            statement.setString(3, newPhone);
            statement.setInt(4, SessionManager.getInstance().getLoggedInUserId());
            statement.setString(5, CurrentPassword);

            int affectedRows = statement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Datas update succesfully");
            }
            else{
                System.out.println("Datas could not be updated");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



}